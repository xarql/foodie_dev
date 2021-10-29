package com.imooc.controller.center;

import com.imooc.pojo.UserDO;
import com.imooc.service.center.CenterUserService;
import com.imooc.utils.IMOOCJSONResult;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@Api(value = "center -用户中心",tags = "用户中心展示的相关接口")
@RestController
@RequestMapping("center")
public class CenterController {
    @Autowired
    private CenterUserService centerUserService;
    @ApiOperation(value = "获取用户信息",notes = "获取用户信息" ,httpMethod = "POST")
    @PostMapping("userInfo")
    public IMOOCJSONResult userInfo(@ApiParam(name="userId",value = "用户Id",required = true)
                                    @RequestParam  String userId){
        UserDO user=centerUserService.queryUserInfo(userId);
        return IMOOCJSONResult.ok(user);
    }

}
