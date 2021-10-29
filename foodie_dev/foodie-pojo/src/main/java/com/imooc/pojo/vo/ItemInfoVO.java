package com.imooc.pojo.vo;

import com.imooc.pojo.ItemsDO;
import com.imooc.pojo.ItemsImgDO;
import com.imooc.pojo.ItemsParamDO;
import com.imooc.pojo.ItemsSpecDO;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

/**
 * 商品详情VO
 */
@Getter
@Setter
public class ItemInfoVO {
    private  ItemsDO item;
    private  List<ItemsImgDO> itemImgList;
    private  List<ItemsSpecDO> itemSpecList;
    private  ItemsParamDO itemsParam;
}
