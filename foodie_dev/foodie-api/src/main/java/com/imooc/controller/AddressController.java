package com.imooc.controller;

import com.imooc.pojo.UserAddressDO;
import com.imooc.pojo.bo.AddressBO;
import com.imooc.service.AddressService;
import com.imooc.utils.IMOOCJSONResult;
import com.imooc.utils.MobileEmailUtils;
import com.mysql.cj.util.StringUtils;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Api(value="地址相关",tags = {"地址相关的api接口"})
@RestController
@RequestMapping("address")
public class AddressController {
    /**
     * 用户在确认订单页面，可以针对收货地址做如下操作：
     * 1.查询用户的所有收获地址列表
     * 2.新增收获地址
     * 3.删除收获地址
     * 4.修改收获地址
     * 5.设置默认收获地址
     */
    @Autowired
    private AddressService addressService;

    @ApiOperation(value="根据用户Id 查询收获地址列表",notes="根据用户Id 查询收获地址列表",httpMethod = "POST")
    @PostMapping("/list")
    public IMOOCJSONResult list(
            @ApiParam(name="userId",value="用户id",required = true)
            @RequestParam String userId) {

        if(userId==null){
            IMOOCJSONResult.errorMsg("");
        }
        List<UserAddressDO> list=addressService.queryAll(userId);
        return  IMOOCJSONResult.ok(list);
    }

    @ApiOperation(value="用户新增地址",notes="用户新增地址",httpMethod = "POST")
    @PostMapping("/add")
    public IMOOCJSONResult add(
            @ApiParam(name="addressBO",value="用户地址",required = true)
            @RequestBody AddressBO addressBO) {

        IMOOCJSONResult check=checkAddress(addressBO);
        if(check.getStatus()!=200){
            return check;
        }
        if(addressBO==null){
            IMOOCJSONResult.errorMsg("");
        }
        addressService.addNewUserAddress(addressBO);
        return  IMOOCJSONResult.ok();
    }

    private IMOOCJSONResult checkAddress(AddressBO addressBO){
        String receiver=addressBO.getReceiver();
        if(receiver==null){
            return IMOOCJSONResult.errorMsg("收货人不能为空");
        }
        if(receiver.length()>12){
            return IMOOCJSONResult.errorMsg("收货人姓名不能太长");
        }

        String mobile=addressBO.getMobile();
        if(mobile==null){
            return IMOOCJSONResult.errorMsg("收货人手机号不能为空");
        }
        if(mobile.length()!=11){
            return IMOOCJSONResult.errorMsg("收货人手机长度不正确");
        }
        boolean isMobileOk= MobileEmailUtils.checkMobileIsOk(mobile);
        if(!isMobileOk){
            return IMOOCJSONResult.errorMsg("收货人手机格式不正确");
        }

        String province=addressBO.getProvince();
        String city=addressBO.getCity();
        String district=addressBO.getDistrict();
        String detial=addressBO.getDetail();
        if(province==null||city==null||district==null||detial==null){
            return IMOOCJSONResult.errorMsg("收货地址信息不能为空");
        }
        return IMOOCJSONResult.ok();
    }

    @ApiOperation(value="用户修改地址",notes="用户修改地址",httpMethod = "POST")
    @PostMapping("/update")
    public IMOOCJSONResult update(
            @ApiParam(name="addressBO",value="用户地址",required = true)
            @RequestBody AddressBO addressBO) {

        if(addressBO.getAddressId()==null){
            return IMOOCJSONResult.errorMsg("修改地址错误：addressId不能为空");
        }
        IMOOCJSONResult check=checkAddress(addressBO);
        if(check.getStatus()!=200){
            return check;
        }
        addressService.updateUserAddress(addressBO);
        return  IMOOCJSONResult.ok();
    }

    @ApiOperation(value="用户删除地址",notes="用户删除地址",httpMethod = "POST")
    @PostMapping("/delete")
    public IMOOCJSONResult delete(
            @ApiParam(name="userId",value="用户id",required = true)
            @RequestParam String userId,
            @ApiParam(name="addressId",value="地址Id",required = true)
            @RequestParam String addressId) {

        if(userId==null||addressId==null){
            return IMOOCJSONResult.errorMsg("");
        }

        addressService.deleteUserAddress(userId,addressId);
        return  IMOOCJSONResult.ok();
    }

    @ApiOperation(value="用户设置默认地址",notes="用户设置默认地址",httpMethod = "POST")
    @PostMapping("/setDefault")
    public IMOOCJSONResult setDefault(
            @ApiParam(name="userId",value="用户id",required = true)
            @RequestParam String userId,
            @ApiParam(name="addressId",value="地址Id",required = true)
            @RequestParam String addressId) {

        if(userId==null||addressId==null){
            return IMOOCJSONResult.errorMsg("");
        }

        addressService.updateUserAddressToBeDefault(userId,addressId);
        return  IMOOCJSONResult.ok();
    }
}
