package com.imooc.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.imooc.enums.Sex;
import com.imooc.mapper.UserMapper;
import com.imooc.pojo.UserDO;
import com.imooc.pojo.bo.UserBO;
import com.imooc.service.UserService;
import com.imooc.utils.DateUtil;
import com.imooc.utils.MD5Utils;
import com.imooc.utils.UUIDS;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.beans.Transient;
import java.text.ParseException;
import java.util.Date;

@Service
public class UserServiceImpl extends ServiceImpl<UserMapper,UserDO> implements UserService  {
    @Autowired
    private UserMapper userMapper;

    //新建用户默认头像
    private static final String USER_FACE="http://bpic.588ku.com/element_origin_min_pic/18/06/10/d94f9766a82e40b2c96ddd359c6a59d7.jpg";

    @Transactional(propagation = Propagation.SUPPORTS)
    public boolean queryUsernameIsExist(String username) {
        String id=userMapper.equlsName(username);
        if(id!=null){
            return true;
        }else{
            return false;
        }
    }
    @Transactional(propagation=Propagation.REQUIRED)
    public UserDO createuser(UserBO userBO) throws ParseException {
        UserDO user=new UserDO();
        //插入id
        user.setId(UUIDS.randomUUID32());
        user.setUsername(userBO.getUsername());
        try {
            user.setPassword(MD5Utils.getMD5Str(userBO.getPassword()));
        } catch (Exception e) {
            e.printStackTrace();
        }
        //默认用户昵称同用户名
        user.setNickname(userBO.getUsername());
        //默认头像
        user.setFace(USER_FACE);
        //设置默认生日
        user.setBirthday(DateUtil.stringToDate("1977-1-1"));
        //默认性别为保密
        user.setSex(Sex.secret.type);
        //创建时间  更新时间
        user.setCreatedTime(new Date());
        user.setUpdatedTime(new Date());
        //保存user类
        userMapper.insert(user);
        return user;
    }

    @Transactional(propagation = Propagation.SUPPORTS)
    public UserDO queryUserForLogin(String username, String password) {

       UserDO result=userMapper.selectNameandPassword(username,password);
       return result;
    }
}
