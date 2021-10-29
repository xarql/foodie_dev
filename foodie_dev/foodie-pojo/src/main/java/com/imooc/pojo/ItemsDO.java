package com.imooc.pojo;

import lombok.Getter;
import lombok.Setter;

import java.util.Date;

@Getter
@Setter
public class ItemsDO {
    /** 商品名称;商品id */
    private String id ;
    /** 商品名称;商品名称 */
    private String itemName ;
    /** 分类外键id;分类id */
    private Integer catId ;
    /** 一级分类外键id;一级分类id，用于优化查询 */
    private Integer rootCatId ;
    /** 累计销售;累计销售 */
    private Integer sellCounts ;
    /** 上下架状态;上下架状态,1:上架 2:下架 */
    private Integer onOffStatus ;
    /** 商品内容;商品内容 */
    private String content ;
    /** 创建时间 */
    private Date createdTime ;
    /** 更新时间 */
    private Date updatedTime ;

}
