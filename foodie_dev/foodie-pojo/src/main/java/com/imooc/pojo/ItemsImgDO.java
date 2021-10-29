package com.imooc.pojo;

import lombok.Getter;
import lombok.Setter;

import java.util.Date;
@Setter
@Getter
public class ItemsImgDO {
    /** 图片主键 */
    private String id ;
    /** 商品外键id;商品外键id */
    private String itemId ;
    /** 图片地址;图片地址 */
    private String url ;
    /** 顺序;图片顺序，从小到大 */
    private Integer sort ;
    /** 是否主图;是否主图，1：是，0：否 */
    private Integer isMain ;
    /** 创建时间 */
    private Date createdTime ;
    /** 更新时间 */
    private Date updatedTime ;
}
