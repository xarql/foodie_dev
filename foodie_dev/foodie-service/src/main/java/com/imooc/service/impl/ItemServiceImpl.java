package com.imooc.service.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.imooc.enums.CommentLevel;
import com.imooc.enums.YesOrNo;
import com.imooc.mapper.*;
import com.imooc.pojo.ItemsDO;
import com.imooc.pojo.ItemsImgDO;
import com.imooc.pojo.ItemsParamDO;
import com.imooc.pojo.ItemsSpecDO;
import com.imooc.pojo.vo.CommentLevelCountsVO;
import com.imooc.pojo.vo.ItemCommentVO;
import com.imooc.pojo.vo.SearchItemsVO;
import com.imooc.service.ItemService;
import com.imooc.utils.DesensitizationUtil;
import com.imooc.utils.PagedGridResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class ItemServiceImpl implements ItemService{
    @Autowired
    private ItemsMapper itemsMapper;
    @Autowired
    private ItemsImgMapper itemsImgMapper;
    @Autowired
    private ItemsSpecMapper itemsSpecMapper;
    @Autowired
    private ItemsParamMapper itemsParamMapper;
    @Autowired
    private CommentMapper commentMapper;
    @Autowired
    private ItemsMapperCustom itemsMapperCustom;

    @Transactional(propagation = Propagation.SUPPORTS)
    @Override
    public ItemsDO queryItemById(String itemId) {
        return itemsMapper.selectItems(itemId);
    }

    @Transactional(propagation = Propagation.SUPPORTS)
    @Override
    public List<ItemsImgDO> queryItemImgList(String itemId) {
        return itemsImgMapper.selectItemsImg(itemId);
    }

    @Transactional(propagation = Propagation.SUPPORTS)
    @Override
    public List<ItemsSpecDO> queryItemSpecList(String itemId) {
        return itemsSpecMapper.selectItemsSpec(itemId);
    }

    @Transactional(propagation = Propagation.SUPPORTS)
    @Override
    public ItemsParamDO queryItemParamList(String itemId) {
        return itemsParamMapper.selectItemsParam(itemId);
    }

    @Transactional(propagation = Propagation.SUPPORTS)
    @Override
    public CommentLevelCountsVO queryCommentCounts(String itemId) {

        Integer sum=getCommentCounts(itemId,CommentLevel.BAD.type)
                +getCommentCounts(itemId,CommentLevel.GOOD.type)
                +getCommentCounts(itemId,CommentLevel.NORMAL.type);

        CommentLevelCountsVO commentLevelCountsVO=new CommentLevelCountsVO();
        commentLevelCountsVO.setBadCounts(getCommentCounts(itemId,CommentLevel.GOOD.type));
        commentLevelCountsVO.setNormalCounts(getCommentCounts(itemId,CommentLevel.NORMAL.type));
        commentLevelCountsVO.setGoodCounts(getCommentCounts(itemId,CommentLevel.BAD.type));
        commentLevelCountsVO.setTotalCounts(sum);
        return commentLevelCountsVO;
    }

    @Transactional(propagation = Propagation.SUPPORTS)
    public Integer getCommentCounts(String itemId,Integer level){
        if(level==null){
            return null;
        }
        return  commentMapper.getCommentCounts(itemId,level);
    }

    @Transactional(propagation = Propagation.SUPPORTS)
    @Override
    public PagedGridResult querypagedComments(String itemId, Integer level
                                                 ,Integer page,Integer pageSize) {
        Map<String,Object> map=new HashMap();
        map.put("itemId",itemId);
        map.put("level",level);
        PageHelper.startPage(page,pageSize);
        List<ItemCommentVO> list=itemsMapperCustom.queryItemComments(map);

        for (ItemCommentVO vo:list){
            vo.setNickName(DesensitizationUtil.commonDisplay(vo.getNickName()));
        }
        return  setterPagedGrid(list,page);
    }
    private PagedGridResult setterPagedGrid( List<?> list,Integer page){
        PageInfo<?> pagelist=new PageInfo<>(list);
        PagedGridResult grid=new PagedGridResult();
        grid.setPage(page);
        grid.setRows(list);
        grid.setTotal(pagelist.getPages());
        grid.setRecords(pagelist.getTotal());
        return grid;
    }
    @Transactional(propagation = Propagation.SUPPORTS)
    @Override
    public PagedGridResult searchItems(String keywords, Integer sort, Integer page, Integer pageSize) {
        Map<String,Object> map=new HashMap();
        map.put("keywords",keywords);
        map.put("sort",sort);

        PageHelper.startPage(page,pageSize);
        List<SearchItemsVO> list=itemsMapperCustom.searchItems(map);
        return setterPagedGrid(list,page);
    }
    @Transactional(propagation = Propagation.SUPPORTS)
    @Override
    public PagedGridResult searchItems(Integer catId, Integer sort, Integer page, Integer pageSize) {
        Map<String,Object> map=new HashMap();
        map.put("catId",catId);
        map.put("sort",sort);
        PageHelper.startPage(page,pageSize);
        List<SearchItemsVO> list=itemsMapperCustom.searchItemsByThirdCat(map);
        return setterPagedGrid(list,page);
    }
    @Transactional(propagation = Propagation.SUPPORTS)
    @Override
    public ItemsSpecDO queryItemsById(String specId) {

        return itemsSpecMapper.selctIttemsSpecById(specId);
    }

    @Transactional(propagation = Propagation.SUPPORTS)
    @Override
    public String querryItemMainImgById(String itemId) {
        ItemsImgDO itemsImgDO=new ItemsImgDO();
        itemsImgDO.setItemId(itemId);
        itemsImgDO.setIsMain(YesOrNo.YES.type);
        ItemsImgDO result=itemsImgMapper.selectMainImg(itemsImgDO);
        return result !=null ?result.getUrl():" ";
    }
    @Transactional(propagation = Propagation.REQUIRED)
    @Override
    public void decreapItemSpecStock(String specId, int buyCounts) {
        //分布式锁 zookeeper  redis
        //lockUtil.getLock();加锁
       int result=itemsMapperCustom.decreapItemSpecStock(specId,buyCounts);
       if(result!=1){
           throw new RuntimeException("订单创建失败，原因:库存不足");
       }
        //lockUtil.unLock();解锁
    }


}
