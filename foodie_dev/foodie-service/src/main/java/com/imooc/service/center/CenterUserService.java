package com.imooc.service.center;
import com.imooc.pojo.bo.center.CenterUserBO;
import com.imooc.pojo.UserDO;
import org.springframework.beans.BeanUtils;

public interface CenterUserService {
    /**
     * 根据用户id查询用户信息
     * @param userId
     * @return
     */
    public UserDO queryUserInfo(String userId);

    /**
     * 根据用户id,修改用户信息
     * @param userId
     * @param centerUserBO
     */
    public UserDO updateUserInfo(String userId,CenterUserBO centerUserBO);

    /**
     * 用户头像更新
     * @param userId
     * @param faceUrl
     * @return
     */
    public UserDO updateUserFace(String userId,String faceUrl);
}
