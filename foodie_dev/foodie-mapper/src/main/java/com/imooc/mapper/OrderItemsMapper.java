package com.imooc.mapper;

import com.imooc.pojo.OrderItemsDO;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface OrderItemsMapper {
    public void insetOrderitems(OrderItemsDO orderItemsDO);
    public List<OrderItemsDO> queryByOrderId(OrderItemsDO  orderItemsDO);
}
