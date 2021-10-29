package com.imooc.controller.center;

import com.imooc.controller.BaseController;
import com.imooc.pojo.vo.OrderStatusCountsVO;
import com.imooc.service.center.MyOrdersService;
import com.imooc.utils.IMOOCJSONResult;
import com.imooc.utils.PagedGridResult;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.validation.constraints.NotBlank;

@Api(value = "用户中心我的订单" ,tags = "用户中心我的订单相关接口")
@RestController
@RequestMapping("myorders")
public class MyOrdersController extends BaseController {
    @Autowired
    private MyOrdersService myOrdersService;

    @ApiOperation(value="查询订单列表",notes="查询订单列表",httpMethod = "POST")
    @PostMapping("/query")
    public IMOOCJSONResult query(
            @ApiParam(name="userId",value="用户Id",required = true)
            @RequestParam String userId,
            @ApiParam(name="orderStatus",value="订单状态",required = false)
            @RequestParam @NotBlank Integer orderStatus,
            @ApiParam(name="page",value="查询下一页的第几页",required = false)
            @RequestParam Integer page,
            @ApiParam(name="pageSize",value="分页的每一页显示的条数",required = false)
            @RequestParam Integer pageSize) {
        if(userId==null){
            return IMOOCJSONResult.errorMsg(null);
        }
        if(page==null){
            page=1;
        }
        if(pageSize==null){
            pageSize= PAGE_SIZE;
        }
        PagedGridResult grid=myOrdersService.queryMyorders(userId,orderStatus,page,pageSize);
        return IMOOCJSONResult.ok(grid);
    }
    @ApiOperation(value="商家发货", notes="商家发货", httpMethod = "GET")
    @GetMapping("/deliver")
    public IMOOCJSONResult deliver(
            @ApiParam(name = "orderId", value = "订单id", required = true)
            @RequestParam String orderId) throws Exception {

        if (orderId==null) {
            return IMOOCJSONResult.errorMsg("订单不能为空");
        }
        myOrdersService.updateDeliverOrderStatus(orderId);
        return IMOOCJSONResult.ok();
    }
    @ApiOperation(value="用户确认收货", notes="用户确认收货", httpMethod = "POST")
    @PostMapping("/confirmReceive")
    public IMOOCJSONResult confirmReceive(
            @ApiParam(name = "orderId", value = "订单id", required = true)
            @RequestParam String orderId,
            @ApiParam(name="userId",value = "用户id", required = true)
            @RequestParam String userId) throws Exception {

        if (orderId==null) {
            return IMOOCJSONResult.errorMsg("订单不能为空");
        }

        boolean res=myOrdersService.updateReceiveOrderStatus(orderId);
        if(!res){
            return  IMOOCJSONResult.errorMsg("订单确认收货失败");
        }
        return IMOOCJSONResult.ok();
    }

    /**
     * 用于验证用户和订单是否有关联关系，避免非法用户调用
     * @return
     */
    @ApiOperation(value="用户删除订单", notes="用户删除订单", httpMethod = "POST")
    @PostMapping("/delete")
    private IMOOCJSONResult delete(String userId,String orderId){

        boolean res=myOrdersService.deleteOrder(userId,orderId);
        if(!res){
            return  IMOOCJSONResult.errorMsg("订单删除失败");
        }
            return IMOOCJSONResult.ok();
    }


    @ApiOperation(value="获得订单状态数概况", notes="获得订单状态数概况", httpMethod = "POST")
    @PostMapping("/statusCounts")
    private IMOOCJSONResult statusCounts( @ApiParam(name="userId",value = "用户id", required = true)
                                        @RequestParam String userId){

        if(userId==null){
            return  IMOOCJSONResult.errorMsg("null");
        }

        OrderStatusCountsVO result=myOrdersService.getOrderStatusCounts(userId);
        return IMOOCJSONResult.ok(result);
    }

    @ApiOperation(value="查询订单动向", notes="查询订单动向", httpMethod = "POST")
    @PostMapping("/trend")
    private IMOOCJSONResult trend(
               @ApiParam(name="userId",value = "用户id", required = true)
               @RequestParam String userId,
               @ApiParam(name="page",value="查询下一页的第几页",required = false)
               @RequestParam Integer page,
               @ApiParam(name="pageSize",value="分页的每一页显示的条数",required = false)
               @RequestParam Integer pageSize){

        if(userId==null){
            return  IMOOCJSONResult.errorMsg("null");
        }
        if(page==null){
            page=1;
        }
        if(pageSize==null){
            pageSize=COMMENT_PAGE_SIZE;
        }
        PagedGridResult grid=myOrdersService.getOrdersTrend(userId,page,pageSize);
        return IMOOCJSONResult.ok(grid);
    }

    }
