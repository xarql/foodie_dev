package com.imooc.service.center;

import com.imooc.pojo.OrderDO;
import com.imooc.pojo.OrderStatusDO;
import com.imooc.pojo.vo.MyOrdersVO;
import com.imooc.pojo.vo.OrderStatusCountsVO;
import com.imooc.utils.PagedGridResult;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

public interface MyOrdersService {
    /**
     * 查询我的订单列表
     * @param userId
     * @param orderStatus
     * @param page
     * @param pageSize
     * @return
     */
    public PagedGridResult queryMyorders(String userId,
                                         Integer orderStatus,
                                         Integer page,
                                         Integer pageSize);

    /**
     * 订单状态
     * @param orderId
     */
    public void updateDeliverOrderStatus(String orderId);

    /**
     * 查找我的订单
     *  @param userId
     * @param orderId
     * @return
     */
    public OrderDO querryMyOrderStatus(String userId,String orderId);

    /**
     * 更新订单状态-》确认收获
     * @param orderId
     * @return
     */
    public boolean updateReceiveOrderStatus(String orderId);

    /**
     * 删除订单(逻辑删除)
     * @param userId
     * @param orderId
     * @return
     */
    public boolean deleteOrder(String userId,String orderId);

    /**
     * 查询用户Id数
     * @param userId
     */
    public OrderStatusCountsVO getOrderStatusCounts(String userId);

    /**
     * 获得分页的订单动向
     * @param userId
     * @param page
     * @param pageSize
     * @return
     */
    public PagedGridResult getOrdersTrend(String userId,
                                          Integer page,
                                          Integer pageSize);
}
