package com.imooc.controller;
import com.imooc.enums.OrderStatusEnum;
import com.imooc.pojo.OrderStatusDO;
import com.imooc.pojo.bo.MerchantOrdersVO;
import com.imooc.pojo.bo.SubmitOrderBO;
import com.imooc.pojo.vo.OrderVO;
import com.imooc.service.OrderService;
import com.imooc.utils.CookieUtils;
import com.imooc.utils.IMOOCJSONResult;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@Api(value="订单相关",tags="订单相关的api接口")
@RequestMapping("orders")
@RestController
public class OrdersController extends BaseController {
    @Autowired
    private OrderService orderService;
    @Autowired
    private RestTemplate restTemplate;

    @ApiOperation(value="用户下单",notes = "用户下单" )
    @PostMapping("/create")
    public IMOOCJSONResult create(@RequestBody SubmitOrderBO submitOrderBO
                                  , HttpServletResponse response
                                  , HttpServletRequest request){

        if(submitOrderBO.getPayMethod()!=null){

        }
        System.out.println(submitOrderBO.toString());
        //1.创建订单
        OrderVO order= orderService.createOrder(submitOrderBO);
        String orderId=order.getOrderId();

        //2.创建订单以后，移除购物车中已结算（已提交）的商品
        //TODO 整合redis 之后完善购物车中的已计算商品清除,平且同步到前端cookie
        CookieUtils.setCookie(request,response,FOODIE_SHOPCART
                              ,"");
        //3.向支付中心发送当前订单，用于保存支付中心的订单数据
        MerchantOrdersVO merchantOrdersVO=order.getMerchantOrdersVO();
        merchantOrdersVO.setReturnUrl(payReturnUrl);
        merchantOrdersVO.setAmount(1);

        HttpHeaders headers=new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.add("imoocUserId","imooc");
        headers.add("password","imooc");

        HttpEntity<MerchantOrdersVO> entity=new HttpEntity<>(merchantOrdersVO,headers);
                         restTemplate.postForEntity(
                                               "paymentUrl",
                                                    entity,
                                                    IMOOCJSONResult.class);

        ResponseEntity<IMOOCJSONResult>  responseEntity=
                         restTemplate.postForEntity(paymentUrl,
                                                    entity,
                                                    IMOOCJSONResult.class);

        IMOOCJSONResult paymentResult=responseEntity.getBody();
        if (paymentResult.getStatus()!=200){
            return IMOOCJSONResult.errorMsg("支付中心订单创建失败，请联系管理员");
        }
        return IMOOCJSONResult.ok(orderId);
    }
    /**通知接口*/
    @PostMapping("notifyMerchantOrderPaid")
    public Integer ontifyMerchantOrderPaid(String merchantOrderId){
        orderService.updateOrderStatus(merchantOrderId, OrderStatusEnum.WAIT_DELIVER.type);
        return HttpStatus.OK.value();
    }

    @PostMapping("getPaidOrderInfo")
    public IMOOCJSONResult getPaidOrderInfo(String orderId){
        OrderStatusDO orderStatus=orderService.queryOrderStatusInfo(orderId);
        return IMOOCJSONResult.ok
                (orderStatus);
    }
}