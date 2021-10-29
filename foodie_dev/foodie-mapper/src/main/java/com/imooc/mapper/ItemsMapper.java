package com.imooc.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.imooc.pojo.ItemsDO;
import com.imooc.pojo.UserDO;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface ItemsMapper {
    public ItemsDO selectItems(String itemId);
}
