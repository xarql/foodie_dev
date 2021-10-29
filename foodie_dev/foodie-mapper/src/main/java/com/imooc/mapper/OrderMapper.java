package com.imooc.mapper;

import com.imooc.pojo.OrderDO;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface OrderMapper {
    public void insetOrder(OrderDO orderDO);
    public OrderDO selectByOrder(OrderDO orderDO);
    public int updateByOrder(OrderDO orderDO);
    public void updateIsComment(OrderDO orderDO);
}
