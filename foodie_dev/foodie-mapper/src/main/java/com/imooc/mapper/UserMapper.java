package com.imooc.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.imooc.pojo.UserDO;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;


@Repository
public interface UserMapper extends BaseMapper<UserDO> {
     String equlsName(String name);
     void insertUser(UserDO userDO);
     UserDO  selectNameandPassword(@Param("name")String name,@Param("password") String password);
     void updateUserMsg(UserDO userDO);
     void updateUserFace(UserDO userDO);
}
