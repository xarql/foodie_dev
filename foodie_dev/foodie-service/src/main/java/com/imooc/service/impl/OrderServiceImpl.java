package com.imooc.service.impl;
import com.imooc.enums.OrderStatusEnum;
import com.imooc.enums.YesOrNo;
import com.imooc.mapper.OrderItemsMapper;
import com.imooc.mapper.OrderMapper;
import com.imooc.mapper.OrderStatusMapper;
import com.imooc.pojo.*;
import com.imooc.pojo.bo.MerchantOrdersVO;
import com.imooc.pojo.bo.SubmitOrderBO;
import com.imooc.pojo.vo.OrderVO;
import com.imooc.service.AddressService;
import com.imooc.service.ItemService;
import com.imooc.service.OrderService;
import com.imooc.utils.DateUtil;
import com.imooc.utils.IdGenerate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;

@Service
public class OrderServiceImpl implements OrderService {
    @Autowired
    private OrderMapper orderMapper;
    @Autowired
    private OrderItemsMapper orderItemsMapper;
    @Autowired
    private OrderStatusMapper orderStatusMapper;
    @Autowired
    private AddressService addressService;
    @Autowired
    private ItemService itemService;

    @Transactional(propagation = Propagation.REQUIRED)
    @Override
    public OrderVO createOrder(SubmitOrderBO submitOrderBO) {
        String userId=submitOrderBO.getUserId();
        String addressId=submitOrderBO.getAddressId();
        String itemSpecIds=submitOrderBO.getItemSpecIds();
        Integer payMethod=submitOrderBO.getPayMethod();
        String leftMsg=submitOrderBO.getLeftMsg();
        Integer postAmount=0;

        UserAddressDO address=addressService.queryUserAddress(userId ,addressId);
        //1.新订单数据保存
        OrderDO newOrder=new OrderDO();
        String orderId=IdGenerate.uuid();
        newOrder.setId(orderId);
        newOrder.setUserId(userId);
        newOrder.setReceiverName(address.getReceiver());
        newOrder.setReceiverMobile(address.getMobile());
        newOrder.setReceiverAddress(address.getProvince()+" "+address.getCity()+" "
                                   +address.getDistrict()+" "+address.getDetail());
//      newOrder.setTotalAmount();
//      newOrder.setRealPayAmount();
        newOrder.setPostAmount(postAmount);
        newOrder.setPayMethod(payMethod);
        newOrder.setLeftMsg(leftMsg);
        newOrder.setIsComment(YesOrNo.NO.type);
        newOrder.setIsDelete(YesOrNo.NO.type);
        newOrder.setCreatedTime(new Date());
        newOrder.setUpdatedTime(new Date());
        //2.循环根据itemSpecIds保存订单商品信息表
        String itemSpecIdArr[]=itemSpecIds.split(",");

        Integer totalAmount=0;//商品原价累计
        Integer realPayAmount=0;//优惠后的实际支付价格累计
        for(String itemSpecId:itemSpecIdArr){
            //2.1根据规格id,查询规格的具体信息，主要获取价格
            ItemsSpecDO itemsSpec=itemService.queryItemsById(itemSpecId);
            //TODO 整合redis 后，商品购买的数量重新从redis的购物车中获取
            int buyCount=1;
            totalAmount += itemsSpec.getPriceNormal()*buyCount;
            realPayAmount += itemsSpec.getPriceDiscount()*buyCount;
            //2.2根据规格id,获得商品信息以及商品图片
            String itemId=itemsSpec.getId();
            ItemsDO item=itemService.queryItemById(itemId);
            String url=itemService.querryItemMainImgById(itemId);
            //2.3循环保存子订单数据到数据库
            OrderItemsDO subOrderItem=new OrderItemsDO();
            String subOrderId=IdGenerate.uuid();
            subOrderItem.setId(subOrderId);
            subOrderItem.setOrderId(orderId);
            subOrderItem.setItemId(itemId);
            subOrderItem.setItemName(item.getItemName());
            subOrderItem.setItemImg(url);
            subOrderItem.setBuyCounts(buyCount);
            subOrderItem.setItemSpecId(itemSpecId);
            subOrderItem.setItemSpecName(itemsSpec.getName());
            subOrderItem.setPrice(itemsSpec.getPriceDiscount());
            orderItemsMapper.insetOrderitems(subOrderItem);
            //2.4
            itemService.decreapItemSpecStock(itemSpecId,buyCount);
        }
        newOrder.setTotalAmount(totalAmount);
        newOrder.setRealPayAmount(realPayAmount);
        orderMapper.insetOrder(newOrder);

        //3.保存订单状态表
        OrderStatusDO waitPayOrderStatus=new OrderStatusDO();
        waitPayOrderStatus.setOrderId(orderId);
        waitPayOrderStatus.setOrderStatus(OrderStatusEnum.WAIT_PAY.type);
        waitPayOrderStatus.setCreatedTime(new Date());
        orderStatusMapper.insertOrderStatus(waitPayOrderStatus);
        //4.构建商户订单，用于传给支付中心
        MerchantOrdersVO merchantOrdersVO=new MerchantOrdersVO();
        merchantOrdersVO.setMerchantOrderId(orderId);
        merchantOrdersVO.setMerchantUserId(userId);
        merchantOrdersVO.setAmount(realPayAmount+postAmount);
        merchantOrdersVO.setPayMethod(payMethod);
        //自定义订单VO
        OrderVO orderVO=new OrderVO();
        orderVO.setOrderId(orderId);
        orderVO.setMerchantOrdersVO(merchantOrdersVO);
        return orderVO ;
    }

    @Transactional(propagation = Propagation.REQUIRED)
    @Override
    public void updateOrderStatus(String orderId, Integer orderStatus) {
    OrderStatusDO paidStatus=new OrderStatusDO();
        paidStatus.setOrderId(orderId);
        paidStatus.setOrderStatus(orderStatus);
        paidStatus.setPayTime(new Date());
        orderStatusMapper.updateByPrimaryKeySelective(paidStatus);
    }
    @Transactional(propagation = Propagation.SUPPORTS)
    @Override
    public OrderStatusDO queryOrderStatusInfo(String orderId) {
        return orderStatusMapper.selectByOrderId(orderId);
    }
    @Transactional(propagation = Propagation.REQUIRED)
    @Override
    public void closeOrder() {
        //查询所有未支付订单,判断时间是否超时（1天）
        OrderStatusDO queryOrder=new OrderStatusDO();
        queryOrder.setOrderStatus(OrderStatusEnum.WAIT_PAY.type);
        List<OrderStatusDO> list= orderStatusMapper.selectByStatus(queryOrder);
        for(OrderStatusDO os:list){
            //获得订单创建时间
            Date createdTime=os.getCreatedTime();
            //和当前时间对应
            int days= DateUtil.daysBetween(createdTime,new Date());
            if(days>=1){
                //超过一天，关闭订单
            }
        }
    }
    @Transactional(propagation=Propagation.REQUIRED)
    void doCloseOrder(String orderId){
        OrderStatusDO close=new OrderStatusDO();
        close.setOrderId(orderId);
        close.setOrderStatus(OrderStatusEnum.CLOSE.type);
        close.setCloseTime(new Date());
        orderStatusMapper.updateByIdStatus(close);
    }
}
