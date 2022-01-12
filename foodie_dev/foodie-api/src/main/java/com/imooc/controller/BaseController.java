package com.imooc.controller;

import com.github.pagehelper.PageInfo;
import com.imooc.pojo.OrderDO;
import com.imooc.service.center.MyOrdersService;
import com.imooc.utils.IMOOCJSONResult;
import com.imooc.utils.PagedGridResult;
import org.springframework.beans.factory.annotation.Autowired;

import java.io.File;
import java.util.List;

public class BaseController {
   @Autowired
   private MyOrdersService myOrdersService;

   public static final Integer COMMENT_PAGE_SIZE=10;
   public static final Integer PAGE_SIZE=20;
   public static final String  FOODIE_SHOPCART="shopcart";
   //支付中心地址
   String paymentUrl="http://payment.t.mukewang.com/doodie-payment/payment/createMerchantOrder";

   String x1="http://n6xvu3.natappfree.cc";
   String x2="http://39.103.45.45:8088/foodie-api";
   //微信支付成功->支付中心->天天吃货平台
   //                     ->回调通知的url
   String payReturnUrl=x2+"/order/notifyMerchantOrderPaid";
   //用户上传头像的位置
   public static final String IMAGE_USER_FACE_LOCATION= File.separator+"D:"+
                                                        File.separator+"workspaces"+
                                                        File.separator+"images"+
                                                        File.separator+"foodie"+
                                                        File.separator+"faces";

   public IMOOCJSONResult checkUserOrder(String userId, String orderId){
      OrderDO orderDO=new OrderDO();
      orderDO.setUserId(userId);
      orderDO.setId(orderId);
      if (orderDO==null){
         return IMOOCJSONResult.errorMsg("订单不存在");
      }
      return IMOOCJSONResult.ok(orderDO);
   }




}
