package com.imooc.mapper;

import com.imooc.pojo.ItemsDO;
import com.imooc.pojo.ItemsParamDO;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface ItemsParamMapper {
    public ItemsParamDO selectItemsParam(String itemId);
}
