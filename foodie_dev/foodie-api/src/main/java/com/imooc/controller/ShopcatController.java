package com.imooc.controller;
import com.imooc.pojo.bo.ShopcartBO;
import com.imooc.utils.IMOOCJSONResult;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@Api(value="购物车接口controller",tags={"购物车接口相关api"})
@RestController
@RequestMapping("shopcart")
public class ShopcatController {

    @ApiOperation(value="同步购物车到后端",tags="同步购物车到后端",httpMethod = "POST")
    @GetMapping("/add")
    public IMOOCJSONResult add(
            @RequestParam String userId,
            @RequestBody ShopcartBO shopcartBO,
            HttpServletRequest request,
            HttpServletResponse response){

        if(userId==null){
            return IMOOCJSONResult.errorMsg("");
        }
        //TODO  前端用户在登录的情况下，添加商品到购物车，会同时在后端同步购物车到redis缓存
        System.out.println(shopcartBO);
        return IMOOCJSONResult.ok();
    }

    @ApiOperation(value="从购物车中删除商品",tags="从购物车中删除商品",httpMethod = "POST")
    @GetMapping("/del")
    public IMOOCJSONResult del(
            @RequestParam String userId,
            @RequestParam String itemSpecId,
            HttpServletRequest request,
            HttpServletResponse response){

        if(userId==null || itemSpecId==null){
            return IMOOCJSONResult.errorMsg("参数不能为空");
        }
        //TODO  用户在页面删除购物车中的商品数据，如果此时用户已经登录，则需要同步删除后端购物车中的商品

        return IMOOCJSONResult.ok();
    }
}
