package com.imooc.service.impl.center;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.imooc.enums.OrderStatusEnum;
import com.imooc.enums.YesOrNo;
import com.imooc.mapper.OrderMapper;
import com.imooc.mapper.OrdersMapperCustom;
import com.imooc.pojo.OrderDO;
import com.imooc.pojo.OrderStatusDO;
import com.imooc.pojo.vo.MyOrdersVO;
import com.imooc.pojo.vo.OrderStatusCountsVO;
import com.imooc.service.center.MyOrdersService;
import com.imooc.utils.PagedGridResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
@Service
public class MyOrdersServiceImpl extends BaseService implements MyOrdersService {
    @Autowired
    private OrdersMapperCustom ordersMapperCustom;
    @Autowired
    private OrderMapper orderMapper;

    @Override
    public PagedGridResult queryMyorders(String userId,
                                         Integer orderStatus,
                                         Integer page,
                                         Integer pageSize) {
        Map<String,Object> map=new HashMap<>();
        map.put("userId",userId);
        if(orderStatus!=null){
        map.put("orderStatus",orderStatus);
        }

        PageHelper.startPage(page,pageSize);
        List<MyOrdersVO> list=ordersMapperCustom.queryMyOrders(map);

        return setterPagedGrid(list,page);
    }


    @Override
    public void updateDeliverOrderStatus(String orderId) {

        OrderStatusDO updateStatusDO=new OrderStatusDO();
        updateStatusDO.setOrderStatus(OrderStatusEnum.WAIT_RECEIVE.type);
        updateStatusDO.setDeliverTime(new Date());
        ordersMapperCustom.updateStatusById(updateStatusDO);
    }
    @Transactional(propagation = Propagation.SUPPORTS)
    @Override
    public OrderDO querryMyOrderStatus(String userId,String orderId) {

        OrderDO order=new OrderDO();
        order.setUserId(userId);
        order.setId(orderId);
        order.setIsDelete(YesOrNo.NO.type);
        return orderMapper.selectByOrder(order);
    }

    @Transactional(propagation = Propagation.REQUIRED)
    @Override
    public boolean updateReceiveOrderStatus(String orderId) {

        OrderStatusDO orderStatusDO=new OrderStatusDO();
        orderStatusDO.setOrderStatus(OrderStatusEnum.SUCCESS.type);
        orderStatusDO.setSuccessTime(new Date());
        orderStatusDO.setOrderId(orderId);
       int b=ordersMapperCustom.updateStatusByOrder(orderStatusDO);
        return b==1? true:false ;
    }

    @Transactional(propagation = Propagation.REQUIRED)
    @Override
    public boolean deleteOrder(String userId, String orderId) {

        OrderDO orderDO=new OrderDO();
        orderDO.setIsDelete(YesOrNo.YES.type);
        orderDO.setUpdatedTime(new Date());
        orderDO.setId(orderId);
        orderDO.setUserId(userId);
        int result=orderMapper.updateByOrder(orderDO);
        return result==1 ?true:false;
    }

    @Transactional(propagation = Propagation.SUPPORTS)
    @Override
    public OrderStatusCountsVO  getOrderStatusCounts(String userId) {
        Map<String,Object> map=new HashMap<>();
        map.put("userId",userId);
        map.put("orderStatus",OrderStatusEnum.WAIT_PAY.type);
        int waitPayCounts=ordersMapperCustom.getMyOrderStatusCounts(map);

        map.put("orderStatus",OrderStatusEnum.WAIT_DELIVER.type);
        int waitDeliverCounts=ordersMapperCustom.getMyOrderStatusCounts(map);

        map.put("orderStatus",OrderStatusEnum.WAIT_RECEIVE.type);
        int waitReceiveCounts=ordersMapperCustom.getMyOrderStatusCounts(map);

        map.put("orderStatus",OrderStatusEnum.WAIT_PAY.type);
        map.put("isComment",YesOrNo.NO.type);
        int waitCommentCounts=ordersMapperCustom.getMyOrderStatusCounts(map);

        OrderStatusCountsVO countsVO=new OrderStatusCountsVO(waitPayCounts
                                                        ,waitDeliverCounts
                                                        ,waitReceiveCounts
                                                        ,waitCommentCounts);
        return countsVO;
    }

    @Transactional(propagation = Propagation.SUPPORTS)
    @Override
    public PagedGridResult getOrdersTrend(String userId, Integer page, Integer pageSize) {
        Map<String,Object> map=new HashMap<>();
        map.put("userId",userId);

        PageHelper.startPage(page,pageSize);
        List<OrderStatusDO> list= ordersMapperCustom.getMyOrderTrend(map);
        return setterPagedGrid(list,page);
    }
}
