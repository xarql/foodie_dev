package com.imooc.pojo.vo;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class SubCategoryVO {
    /**三级目录信息*/
    private Integer subId;
    private String  subName;
    private Integer subType ;
    private Integer subFatherId;
}
