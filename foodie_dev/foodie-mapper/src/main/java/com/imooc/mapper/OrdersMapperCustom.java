package com.imooc.mapper;
import com.imooc.pojo.OrderStatusDO;
import com.imooc.pojo.vo.MyOrdersVO;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import java.util.List;
import java.util.Map;
@Mapper
public interface OrdersMapperCustom {
    public List<MyOrdersVO> queryMyOrders(@Param("paramsMap") Map<String,Object> map);
    public void updateStatusById(OrderStatusDO orderStatusDO);
    public int updateStatusByOrder(OrderStatusDO orderStatusDO);
    public int getMyOrderStatusCounts(@Param("paramsMap") Map<String,Object> map);
    public List<OrderStatusDO> getMyOrderTrend(@Param("paramsMap") Map<String,Object> map);

}
