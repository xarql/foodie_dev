package com.imooc.service;

import com.imooc.pojo.UserAddressDO;
import com.imooc.pojo.bo.AddressBO;

import java.util.List;

public interface AddressService {
    /**
     * 根据用户id 查询用户的收获地址列表
     * @param userId
     * @return
     */
    public List<UserAddressDO> queryAll(String userId);

    /**
     * 用户新增地址
     * @param addressBO
     */
    public void addNewUserAddress(AddressBO addressBO);

    /**
     * 用户修改地址
     * @param addressBO
     */
    public void updateUserAddress(AddressBO addressBO);

    /**
     * 根据用户id和地址id,删除对应的用户地址信息
     * @param userId
     * @param addressId
     */
    public void deleteUserAddress(String userId,String addressId);

    /**
     * 修改默认地址
     * @param userId
     * @param addressId
     */
    public void updateUserAddressToBeDefault(String userId,String addressId);

    /**
     * 根据用户id和地址id,查询出具体的用户地址对象信息
     * @param userId
     * @param addressId
     * @return
     */
    public UserAddressDO queryUserAddress(String userId,String addressId);
}
