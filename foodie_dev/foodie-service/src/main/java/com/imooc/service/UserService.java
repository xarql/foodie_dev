package com.imooc.service;

import com.imooc.pojo.UserDO;
import com.imooc.pojo.bo.UserBO;

import java.text.ParseException;

public interface UserService {
    /**
     * 判断用户名是否存在
     */
    public boolean queryUsernameIsExist(String username);

    /**
     * 创建用户表
     */
    public UserDO createuser(UserBO userBO) throws ParseException;

    /**
     * 检索用户名和密码是否匹配，用于登录
     */
    public UserDO queryUserForLogin(String username,String password);
}
