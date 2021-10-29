package com.imooc.pojo.vo;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class SearchItemsVO {
    private String itemId;
    private String ItemName;
    private int  sellCounts;
    private String imgUrl;
    private int prive;
}
