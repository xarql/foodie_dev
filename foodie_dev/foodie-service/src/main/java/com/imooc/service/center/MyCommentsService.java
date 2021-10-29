package com.imooc.service.center;

import com.imooc.pojo.OrderItemsDO;
import com.imooc.pojo.UserDO;
import com.imooc.pojo.bo.center.OrderItemsCommentBO;
import com.imooc.utils.PagedGridResult;

import java.util.List;

public interface MyCommentsService {
    /**
     * 根据订单id查询关联的商品
     * @param orderId
     * @return
     */
    public List<OrderItemsDO> queryPendingComment(String orderId);

    /**
     * 保存用户评论
     * @param orderId
     * @param userId
     * @param commentList
     */
    public void saveComments(String orderId, String userId, List<OrderItemsCommentBO> commentList);

    /**
     * 我的评价查询分页
     * @param userId
     * @param page
     * @param pageSize
     * @return
     */
    public PagedGridResult queryMyComments(String userId,Integer page
                                           ,Integer pageSize);
}
