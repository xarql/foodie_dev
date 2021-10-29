package com.imooc.pojo.vo;

import com.imooc.pojo.bo.MerchantOrdersVO;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class OrderVO {
    private String orderId;
    private MerchantOrdersVO merchantOrdersVO;
}
