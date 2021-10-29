package com.imooc.mapper;

import com.imooc.pojo.ItemsDO;
import com.imooc.pojo.ItemsImgDO;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface ItemsImgMapper {
    public List<ItemsImgDO> selectItemsImg(String itemId);
    public ItemsImgDO selectMainImg(ItemsImgDO itemsImgDO);
}
