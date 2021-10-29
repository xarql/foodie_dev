package com.imooc.pojo;

import lombok.Getter;
import lombok.Setter;

import java.util.Date;

@Getter
@Setter
public class OrderDO {
    /** 订单主键;同时也是订单编号 */
    private String id ;
    /** 用户id */
    private String userId ;
    /** 收货人快照 */
    private String receiverName ;
    /** 收货人手机号快照 */
    private String receiverMobile ;
    /** 收货地址快照 */
    private String receiverAddress ;
    /** 订单总价格 */
    private Integer totalAmount ;
    /** 实际支付总价格 */
    private Integer realPayAmount ;
    /** 邮费;默认可以为零，代表包邮 */
    private Integer postAmount ;
    /** 支付方式;1:微信 2:支付宝 */
    private Integer payMethod ;
    /** 买家留言 */
    private String leftMsg ;
    /** 扩展字段 */
    private String extand ;
    /** 买家是否评价;1：已评价，0：未评价 */
    private Integer isComment ;
    /** 逻辑删除状态;1: 删除 0:未删除 */
    private Integer isDelete ;
    /** 创建时间（成交时间） */
    private Date createdTime ;
    /** 更新时间 */
    private Date updatedTime ;

}
