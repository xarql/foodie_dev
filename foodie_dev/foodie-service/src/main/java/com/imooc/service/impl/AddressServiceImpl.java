package com.imooc.service.impl;

import com.imooc.enums.YesOrNo;
import com.imooc.mapper.UserAddressMapper;
import com.imooc.pojo.UserAddressDO;
import com.imooc.pojo.bo.AddressBO;
import com.imooc.service.AddressService;
import com.imooc.utils.IdGenerate;
import org.apache.catalina.SessionIdGenerator;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class AddressServiceImpl implements AddressService {

    @Autowired
    private UserAddressMapper userAddressMapper;

    @Transactional(propagation = Propagation.SUPPORTS)
    @Override
    public List<UserAddressDO> queryAll(String userId) {
        return  userAddressMapper.queryUserAddress(userId);
    }

    @Transactional(propagation = Propagation.REQUIRED)
    @Override
    public void addNewUserAddress(AddressBO addressBO) {
//        1.判断当前用户存在地址，如果没有新增地址
        List<UserAddressDO> addressDOList=this.queryAll(addressBO.getUserId());
        Integer isDefault=0;
        if(addressDOList==null||addressDOList.isEmpty()||addressDOList.size()==0){
            isDefault=1;
        }
//        2.保存地址到数据库
        UserAddressDO newAddress=new UserAddressDO();
        BeanUtils.copyProperties(addressBO,newAddress);
        newAddress.setId(IdGenerate.uuid());
        newAddress.setIsDefault(isDefault);
        newAddress.setCreatedTime(new Date());
        newAddress.setUpdatedTime(new Date());
        userAddressMapper.insertAddress(newAddress);
    }

    @Transactional(propagation = Propagation.REQUIRED)
    @Override
    public void updateUserAddress(AddressBO addressBO) {
        String addressId=addressBO.getAddressId();

        UserAddressDO pendingAddress=new UserAddressDO();
        BeanUtils.copyProperties(addressBO,pendingAddress);

        pendingAddress.setId(addressId);
        pendingAddress.setUpdatedTime(new Date());
        userAddressMapper.updateAddress(pendingAddress);
    }
    @Transactional(propagation = Propagation.REQUIRED)
    @Override
    public void deleteUserAddress(String userId, String addressId) {
        UserAddressDO address=new UserAddressDO();
        address.setId(addressId);
        address.setUserId(userId);

        userAddressMapper.deleteAddress(address);
    }
    @Transactional(propagation = Propagation.REQUIRED)
    @Override
    public void updateUserAddressToBeDefault(String userId, String addressId) {
//        1.查找默认地址，设置为不默认

        UserAddressDO queryAddress=new UserAddressDO();
        queryAddress.setUserId(userId);
        queryAddress.setIsDefault(YesOrNo.YES.type);
        List<UserAddressDO> lists=userAddressMapper.selectIsDefault(queryAddress);
        for(UserAddressDO ua:lists ){
            Map<String,Object> map=new HashMap<>();
            UserAddressDO user=new UserAddressDO();
            user.setUserId(ua.getUserId());
            user.setIsDefault(YesOrNo.NO.type);
            user.setId(ua.getId());
            userAddressMapper.updateIsdefault(user);
        }
//        2.根据地址id修改为默认的地址
        Map<String,Object> map=new HashMap<>();
        UserAddressDO user=new UserAddressDO();
        user.setIsDefault(YesOrNo.YES.type);
        user.setId(addressId);
        user.setUserId(userId);
        userAddressMapper.updateIsdefault(user);
    }
    @Transactional(propagation = Propagation.SUPPORTS)
    @Override
    public UserAddressDO queryUserAddress(String userId, String addressId) {
        UserAddressDO user=new UserAddressDO();
        user.setId(addressId);
        user.setUserId(userId);
        return userAddressMapper.queryUserAddressByid(user);
    }
}
