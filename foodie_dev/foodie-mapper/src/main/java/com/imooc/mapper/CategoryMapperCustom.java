package com.imooc.mapper;

import com.imooc.pojo.vo.CategoryVO;
import com.imooc.pojo.vo.NewItemsVO;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

@Mapper
public interface CategoryMapperCustom {
    public List<CategoryVO> getsubCatList(Integer rootCatId);
    public List<NewItemsVO> getSixNewItemslazy(@Param("paramMap") Map<String,Object> map);
}
