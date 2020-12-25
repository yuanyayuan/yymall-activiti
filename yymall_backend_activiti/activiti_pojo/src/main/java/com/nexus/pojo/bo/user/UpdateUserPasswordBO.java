package com.nexus.pojo.bo.user;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.validation.constraints.NotEmpty;
@Data
@EqualsAndHashCode(callSuper = false)
public class UpdateUserPasswordBO {
    @NotEmpty(message = "用户名不能为空")
    @ApiModelProperty(value = "用户名", required = true)
    private String username;
    @NotEmpty(message = "旧密码")
    @ApiModelProperty(value = "旧密码", required = true)
    private String oldPassword;
    @NotEmpty(message = "新密码")
    @ApiModelProperty(value = "新密码", required = true)
    private String newPassword;
}
