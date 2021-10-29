package com.imooc.mapper;

import com.imooc.pojo.ItemsDO;
import com.imooc.pojo.ItemsSpecDO;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

@Mapper
public interface ItemsSpecMapper {
    public List<ItemsSpecDO> selectItemsSpec(String itemId);
    public ItemsSpecDO selctIttemsSpecById(String id);
}
