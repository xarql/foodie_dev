package com.imooc.pojo.vo;

import lombok.Getter;
import lombok.Setter;

/**
 * 用户中心，我的订单列表嵌套商品VO
 */
@Getter
@Setter
public class MySubOrderItemVO {
    private String itemId;
    private String itemImg;
    private String itemName;
    private String itemSpecName;
    private String itemSpecId;
    private Integer buyCounts;
    private Integer price;

}