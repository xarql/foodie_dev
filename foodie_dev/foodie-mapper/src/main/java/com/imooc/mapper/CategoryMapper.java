package com.imooc.mapper;

import com.imooc.pojo.CategoryDO;
import com.imooc.pojo.vo.NewItemsVO;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

@Repository
public interface CategoryMapper {
    public List<CategoryDO> qetCategoryRoot(int i);
}
