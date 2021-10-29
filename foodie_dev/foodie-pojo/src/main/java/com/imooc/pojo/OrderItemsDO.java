package com.imooc.pojo;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class OrderItemsDO {
    /** 主键id */
    private String id ;
    /** 归属订单id */
    private String orderId ;
    /** 商品id */
    private String itemId ;
    /** 商品图片 */
    private String itemImg ;
    /** 商品名称 */
    private String itemName ;
    /** 规格id */
    private String itemSpecId ;
    /** 规格名称 */
    private String itemSpecName ;
    /** 成交价格 */
    private Integer price ;
    /** 购买数量 */
    private Integer buyCounts ;
}
