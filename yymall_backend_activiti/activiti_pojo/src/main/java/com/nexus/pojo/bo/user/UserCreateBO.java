package com.nexus.pojo.bo.user;

import com.nexus.common.validator.FlagValidator;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotEmpty;
@Data
@AllArgsConstructor
@NoArgsConstructor
@ApiModel("用户创建对象")
public class UserCreateBO {
    /**
     * 用户名
     */
    @ApiModelProperty(value = "用户名", required = true)
    @NotEmpty(message = "用户名不能为空")
    private String username;

    /**
     * 姓名
     */
    @ApiModelProperty(value = "姓名", required = true)
    private String nickName;

    /**
     * 密码
     */
    @ApiModelProperty(value = "密码", required = true)
    @NotEmpty(message = "密码不能为空")
    private String password;
    /**
     * 备注
     */
    @ApiModelProperty(value = "备注")
    private String note;
    /**
     * 状态
     */
    @FlagValidator(values = "0,1",message = "显示状态不正确")
    private Integer status;
}
