package com.imooc.pojo.bo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;
import javax.validation.constraints.NotBlank;

@ApiModel(value = "用户对象BO",description = "从客户端，由用户传入的数据封装在此entity中")
@Getter
@Setter
public class UserBO {
    @ApiModelProperty(value = "用户名",name = "username",example = "imooc",required = true)
    @NotBlank
    private String username;
    @ApiModelProperty(value = "密码",name = "password",example = "123456",required = true)
    @NotBlank
    private String password;
    @ApiModelProperty(value = "确认密码",name = "confirmPassword",example = "123456",required = false)
    private String confirmPassword;

}
