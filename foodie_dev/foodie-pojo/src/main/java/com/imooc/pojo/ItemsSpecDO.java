package com.imooc.pojo;

import lombok.Getter;
import lombok.Setter;

import java.util.Date;
@Setter
@Getter
public class ItemsSpecDO {
    /** 商品规格id */
    private String id ;
    /** 商品外键id */
    private String itemId ;
    /** 规格名称 */
    private String name ;
    /** 库存 */
    private Integer stock ;
    /** 折扣力度 */
    private Double discounts ;
    /** 优惠价 */
    private Integer priceDiscount ;
    /** 原价 */
    private Integer priceNormal ;
    /** 创建时间 */
    private Date createdTime ;
    /** 更新时间 */
    private Date updatedTime ;
}
