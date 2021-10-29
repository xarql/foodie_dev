package com.imooc.pojo.vo;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class CategoryVO {
    /**二级目录id*/
    private Integer id;
    private String  name;
    private Integer type ;
    private Integer fatherId;
    /**三级目录list*/
    private List<SubCategoryVO> subCategoryList;

}
