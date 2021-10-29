package com.imooc.service;
import com.imooc.pojo.CategoryDO;
import com.imooc.pojo.vo.CategoryVO;
import com.imooc.pojo.vo.NewItemsVO;

import java.util.List;
import java.util.Locale;
import java.util.Map;

public interface CategoryService {
    /**
     * 查询所有一级分类
     * @return
     */
    public List<CategoryDO> queryAllRootLevelCat(int i);

    /**
     * 根据一级目录分类id 查询子类分类信息
     * @param rootCatId
     * @return
     */
    public List<CategoryVO> getSubCatList(Integer rootCatId);

    /**
     * 查询首页每个一级分类下的6条最新商品数据
     * @param rootCatId
     * @return
     */
    public List<NewItemsVO> getSixNewItemslazy(Integer rootCatId);
}
