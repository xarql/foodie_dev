package com.imooc.pojo.bo;

import lombok.Getter;
import lombok.Setter;

/**
 * 用户新增或修改地址的BO
 */
@Getter
@Setter
public class AddressBO {

    private String addressId;
    private String userId;
    private String receiver;
    private String mobile;
    private String province;
    private String city;
    private String district;
    private String detail;
}
