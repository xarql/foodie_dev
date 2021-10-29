package com.imooc.mapper;
import com.imooc.pojo.UserAddressDO;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import java.util.List;
import java.util.Map;

@Mapper
public interface UserAddressMapper {
    public List<UserAddressDO> queryUserAddress(String userId);
    public UserAddressDO queryUserAddressByid(UserAddressDO userAddressDO);
    public void insertAddress(UserAddressDO userAddressDO);
    public void updateAddress(UserAddressDO userAddressDO);
    public void deleteAddress(UserAddressDO userAddressDO);
    public List<UserAddressDO> selectIsDefault(UserAddressDO userAddressDO);
    public void updateIsdefault(UserAddressDO userAddressDO);
}
