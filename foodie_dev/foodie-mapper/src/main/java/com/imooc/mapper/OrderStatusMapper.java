package com.imooc.mapper;

import com.imooc.pojo.OrderStatusDO;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface OrderStatusMapper {
    public void insertOrderStatus(OrderStatusDO orderStatusDO);
    public void updateByPrimaryKeySelective(OrderStatusDO orderStatusDO);
    public OrderStatusDO selectByOrderId(String orderId);
    public List<OrderStatusDO> selectByStatus(OrderStatusDO orderStatusDO);
    public void updateByIdStatus(OrderStatusDO orderStatusDO);
    public void updateCommentTime(OrderStatusDO orderStatusDO);
}
