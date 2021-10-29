package com.imooc.controller;
import com.imooc.enums.CategoryRoot;
import com.imooc.enums.YesOrNo;
import com.imooc.pojo.CarouselDO;
import com.imooc.pojo.CategoryDO;
import com.imooc.pojo.vo.CategoryVO;
import com.imooc.pojo.vo.NewItemsVO;
import com.imooc.service.CarouselService;
import com.imooc.service.CategoryService;
import com.imooc.utils.IMOOCJSONResult;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@Api(value="首页",tags={"首页展示项"})
@RestController
@RequestMapping("index")

public class IndexController {
    @Autowired
    private CarouselService carouselService;
    @Autowired
    private CategoryService categoryService;

    @ApiOperation(value="获取首页轮播图列表",notes="获取首页轮播图列表",httpMethod = "GET")
    @GetMapping("/carousel")
    public IMOOCJSONResult carousel() {
       List<CarouselDO> list=carouselService.querryAll(YesOrNo.YES.type);
       return IMOOCJSONResult.ok(list);
    }

    @ApiOperation(value="获取商品分类(一级分类)",notes="获取商品分类(一级分类)",httpMethod = "GET")
    @GetMapping("/cats")
    public IMOOCJSONResult cats() {
        List<CategoryDO> list=categoryService.queryAllRootLevelCat(CategoryRoot.ONE.type);
        return IMOOCJSONResult.ok(list);
    }

    @ApiOperation(value="获取商品子分类",notes="获取商品子分类",httpMethod = "GET")
    @GetMapping("/subCat/{rootCatId}")
    public IMOOCJSONResult subCat(
            @ApiParam(name="rootCatId",value="一级分类id",required = true)
            @PathVariable Integer rootCatId) {

        if(rootCatId==null){
            return IMOOCJSONResult.errorMsg("");
        }

        List<CategoryVO> list=categoryService.getSubCatList(rootCatId);
        return IMOOCJSONResult.ok(list);
    }

    @ApiOperation(value="查询每个一级分类下的最新6条商品数据",notes="查询每个一级分类下的最新6条商品数据",httpMethod = "GET")
    @GetMapping("/sixNewItems/{rootCatId}")
    public IMOOCJSONResult sixNewItems(
            @ApiParam(name="rootCatId",value="一级分类id",required = true)
            @PathVariable Integer rootCatId) {

        if(rootCatId==null){
            return IMOOCJSONResult.errorMsg("");
        }

        List<NewItemsVO> list=categoryService.getSixNewItemslazy(rootCatId);
        return IMOOCJSONResult.ok(list);
    }
}
