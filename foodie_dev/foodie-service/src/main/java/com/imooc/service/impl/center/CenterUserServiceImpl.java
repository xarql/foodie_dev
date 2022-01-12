package com.imooc.service.impl.center;

import com.imooc.mapper.UserMapper;
import com.imooc.pojo.UserDO;
import com.imooc.pojo.bo.center.CenterUserBO;
import com.imooc.service.center.CenterUserService;
import com.imooc.utils.CookieUtils;
import com.imooc.utils.JsonUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;

@Service
public class CenterUserServiceImpl implements CenterUserService {
    @Autowired
    private UserMapper userMapper;

    @Transactional(propagation = Propagation.SUPPORTS)
    @Override
    public UserDO queryUserInfo(String userId) {
        UserDO user= userMapper.selectById(userId);
        user.setPassword(null);
        return user;
    }

    @Transactional(propagation = Propagation.REQUIRED)
    @Override
    public UserDO updateUserInfo(String userId, CenterUserBO centerUserBO) {
        UserDO updateUser=new UserDO();
        BeanUtils.copyProperties(centerUserBO ,updateUser);
        updateUser.setId(userId);
        updateUser.setUpdatedTime(new Date());
        userMapper.updateUserMsg(updateUser);
        return userMapper.selectById(userId);
    }
    @Transactional(propagation = Propagation.REQUIRED)
    @Override
    public UserDO updateUserFace(String userId, String faceUrl) {
        UserDO updateUser=new UserDO();
        updateUser.setId(userId);
        updateUser.setFace(faceUrl);
        updateUser.setUpdatedTime(new Date());

        userMapper.updateUserFace(updateUser);
        return userMapper.selectById(userId);
    }
}
