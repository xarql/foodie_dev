package com.imooc.service;

import com.imooc.pojo.ItemsDO;
import com.imooc.pojo.ItemsImgDO;
import com.imooc.pojo.ItemsParamDO;
import com.imooc.pojo.ItemsSpecDO;
import com.imooc.pojo.vo.CommentLevelCountsVO;
import com.imooc.pojo.vo.ItemCommentVO;
import com.imooc.utils.PagedGridResult;

import java.util.List;

public interface ItemService  {
    /**
     * 根据商品Id查询详情
     * @param itemId
     * @return
     */
    public ItemsDO queryItemById(String itemId);
    /**
     * 根据商品Id查询图片列表
     * @param itemId
     * @return
     */
    public List<ItemsImgDO> queryItemImgList(String itemId);
    /**
     * 根据商品Id查询商品规格
     * @param itemId
     * @return
     */
    public List<ItemsSpecDO> queryItemSpecList(String itemId);
    /**
     * 根据商品Id查询商品参数
     * @param itemId
     * @return
     */
    public ItemsParamDO queryItemParamList(String itemId);

    /**
     * 根据商品Id查询商品的评价等级数量
     * @param itemId
     */
    public CommentLevelCountsVO queryCommentCounts(String itemId);

    /**
     * 根据商品id 查询商品的评价(分页)
     * @param itemId
     * @param level
     * @return
     */
    public PagedGridResult querypagedComments(String itemId, Integer level
                                                  , Integer page, Integer pageSize);

    /**
     * 搜索商品列表
     * @param keywords
     * @param sort
     * @param page
     * @param pageSize
     * @return
     */
    public PagedGridResult searchItems(String keywords, Integer sort
            , Integer page, Integer pageSize);

    /**
     * 根据分类id搜索商品列表
     * @param catId
     * @param sort
     * @param page
     * @param pageSize
     * @return
     */
    public PagedGridResult searchItems(Integer catId, Integer sort
            , Integer page, Integer pageSize);

    /**
     * 根据商品规格id获取规格对象
     * @param specId
     * @return
     */
    public ItemsSpecDO queryItemsById(String specId);

    /**
     * 根据商品id获得商品图片主图url
     * @param itemId
     * @return
     */
    public String querryItemMainImgById(String itemId);

    /**
     * 减少库存
     * @param specId
     * @param buyCounts
     */
    public void decreapItemSpecStock(String specId,int buyCounts);
}
