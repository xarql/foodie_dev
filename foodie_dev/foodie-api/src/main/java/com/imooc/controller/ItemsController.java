package com.imooc.controller;

import com.imooc.enums.YesOrNo;
import com.imooc.pojo.*;
import com.imooc.pojo.vo.CommentLevelCountsVO;
import com.imooc.pojo.vo.ItemInfoVO;
import com.imooc.service.ItemService;
import com.imooc.service.impl.ItemServiceImpl;
import com.imooc.utils.IMOOCJSONResult;
import com.imooc.utils.PagedGridResult;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.validation.constraints.NotBlank;
import java.util.List;

@Api(value="商品接口",tags={"商品信息展示的相关接口"})
@RestController
@RequestMapping("items")
public class ItemsController extends BaseController {
    @Autowired
    private ItemService itemService;

    @ApiOperation(value="查询商品详情",notes="查询商品详情",httpMethod = "GET")
    @GetMapping("/info/{itemId}")
    public IMOOCJSONResult info(
            @ApiParam(name="itemId",value="商品id",required = true)
            @PathVariable String itemId) {

        if(itemId==null){
            return IMOOCJSONResult.errorMsg(null);
        }
        ItemsDO  item=itemService.queryItemById(itemId);
        List<ItemsImgDO>  itemImgList=itemService.queryItemImgList(itemId);
        List<ItemsSpecDO> itemSpecList= itemService.queryItemSpecList(itemId);
        ItemsParamDO itemsParam=itemService.queryItemParamList(itemId);

        ItemInfoVO itemInfoVO=new ItemInfoVO();
        itemInfoVO.setItem(item);
        itemInfoVO.setItemImgList(itemImgList);
        itemInfoVO.setItemSpecList(itemSpecList);
        itemInfoVO.setItemsParam(itemsParam);
        return IMOOCJSONResult.ok(itemInfoVO);
    }

    @ApiOperation(value="查询商品评价等级",notes="查询商品评价等级",httpMethod = "GET")
    @GetMapping("/commentLevel")
    public IMOOCJSONResult commentLeval(
            @ApiParam(name="itemId",value="商品id",required = true)
            @RequestParam String itemId) {

        if(itemId==null){
            return IMOOCJSONResult.errorMsg(null);
        }
        CommentLevelCountsVO countsVO=itemService.queryCommentCounts(itemId);
        return IMOOCJSONResult.ok(countsVO);
    }

    @ApiOperation(value="查询商品评论",notes="查询商品评论",httpMethod = "GET")
    @GetMapping("/comments")
    public IMOOCJSONResult comments(
            @ApiParam(name="itemId",value="商品id",required = true)
            @RequestParam  String itemId,
            @ApiParam(name="level",value="评价等级",required = false)
            @RequestParam @NotBlank Integer level,
            @ApiParam(name="page",value="查询下一页的第几页",required = false)
            @RequestParam Integer page,
            @ApiParam(name="pageSize",value="分页的每一页显示的条数",required = false)
            @RequestParam Integer pageSize) {

        if(itemId==null){
            return IMOOCJSONResult.errorMsg(null);
        }
        if(page==null){
            page=1;
        }
        if(pageSize==null){
            pageSize=COMMENT_PAGE_SIZE;
        }
        PagedGridResult grid=itemService.querypagedComments(itemId,
                                                             level,
                                                     page,pageSize);
        return IMOOCJSONResult.ok(grid);
    }
    @ApiOperation(value="搜索商品列表",notes="搜索商品列表",httpMethod = "GET")
    @GetMapping("/search")
    public IMOOCJSONResult search(
            @ApiParam(name="keywords",value="关键字",required = true)
            @RequestParam  String keywords,
            @ApiParam(name="sort",value="排序",required = false)
            @RequestParam @NotBlank Integer sort,
            @ApiParam(name="page",value="查询下一页的第几页",required = false)
            @RequestParam Integer page,
            @ApiParam(name="pageSize",value="分页的每一页显示的条数",required = false)
            @RequestParam Integer pageSize) {
        if(keywords==null){
            return IMOOCJSONResult.errorMsg(null);
        }
        if(page==null){
            page=1;
        }
        if(pageSize==null){
            pageSize= PAGE_SIZE;
        }
        PagedGridResult grid=itemService.searchItems(keywords,sort,page,pageSize);
        return IMOOCJSONResult.ok(grid);
    }
    @ApiOperation(value="通过分类id搜索商品列表",notes="搜索商品列表",httpMethod = "GET")
    @GetMapping("/catItems")
    public IMOOCJSONResult catItems(
            @ApiParam(name="catId",value="三级分类id",required = true)
            @RequestParam  Integer catId,
            @ApiParam(name="sort",value="排序",required = false)
            @RequestParam @NotBlank Integer sort,
            @ApiParam(name="page",value="查询下一页的第几页",required = false)
            @RequestParam Integer page,
            @ApiParam(name="pageSize",value="分页的每一页显示的条数",required = false)
            @RequestParam Integer pageSize) {
        if(catId==null){
            return IMOOCJSONResult.errorMsg(null);
        }
        if(page==null){
            page=1;
        }
        if(pageSize==null){
            pageSize= PAGE_SIZE;
        }
        PagedGridResult grid=itemService.searchItems(catId,sort,page,pageSize);
        return IMOOCJSONResult.ok(grid);
    }
    }
