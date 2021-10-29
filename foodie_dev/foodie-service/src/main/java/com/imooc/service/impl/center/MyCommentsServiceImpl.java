package com.imooc.service.impl.center;

import com.github.pagehelper.PageHelper;
import com.imooc.enums.YesOrNo;
import com.imooc.mapper.ItemsCommentsMapper;
import com.imooc.mapper.OrderItemsMapper;
import com.imooc.mapper.OrderMapper;
import com.imooc.mapper.OrderStatusMapper;
import com.imooc.pojo.OrderDO;
import com.imooc.pojo.OrderItemsDO;
import com.imooc.pojo.OrderStatusDO;
import com.imooc.pojo.bo.center.OrderItemsCommentBO;
import com.imooc.pojo.vo.MyCommentVO;
import com.imooc.service.center.MyCommentsService;
import com.imooc.utils.IdGenerate;
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
public class MyCommentsServiceImpl extends BaseService implements MyCommentsService {
    @Autowired
    private OrderItemsMapper orderItemsMapper;
    @Autowired
    private ItemsCommentsMapper itemsCommentsMapper;
    @Autowired
    private OrderMapper orderMapper;
    @Autowired
    private OrderStatusMapper orderStatusMapper;

    @Transactional(propagation = Propagation.SUPPORTS)
    @Override
    public List<OrderItemsDO> queryPendingComment(String orderId) {
        OrderItemsDO orderItemsDO=new OrderItemsDO();
        orderItemsDO.setOrderId(orderId);
        return orderItemsMapper.queryByOrderId(orderItemsDO);
    }

    @Transactional(propagation = Propagation.REQUIRED)
    @Override
    public void saveComments(String orderId, String userId,
                             List<OrderItemsCommentBO> commentList) {
        //1.保存评价 items_comments
        for(OrderItemsCommentBO oic:commentList){
            oic.setCommentId(IdGenerate.uuid());
        }
        Map<String,Object> map=new HashMap<>();
        map.put("userId",userId);
        map.put("commentList",commentList);
        itemsCommentsMapper.saveComments(map);

        //2.修改订单表改为已评价
         OrderDO orderDO=new OrderDO();
         orderDO.setId(orderId);
         orderDO.setIsComment(YesOrNo.YES.type);
         orderMapper.updateIsComment(orderDO);
        //3.订单状态表order_status  的留言时间
        OrderStatusDO orderStatusDO=new OrderStatusDO();
        orderStatusDO.setOrderId(orderId);
        orderStatusDO.setCommentTime(new Date());
        orderStatusMapper.updateCommentTime(orderStatusDO);
    }
    @Transactional(propagation = Propagation.SUPPORTS)
    @Override
    public PagedGridResult queryMyComments(String userId, Integer page, Integer pageSize) {

        Map<String,Object> map=new HashMap<>();
        map.put("userId",userId);

        PageHelper.startPage(page,pageSize);
        List<MyCommentVO> list=itemsCommentsMapper.queryMyComments(map);
        PagedGridResult grid=new PagedGridResult();

        return setterPagedGrid(list,page);
    }
}
