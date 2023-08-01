package com.sj.model.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.io.Serializable;

/**
 * @ClassName RegisterVo
 * @Author 闪光灯
 * @Date 2023/8/1 17:05
 * @Description 注册参数数据模型
 */
@Data
@ApiModel(value = "注册用户参数", description = "注册用户参数")
public class RegisterVo implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty("用户名")
    @NotBlank(message = "用户名不能为空")
    private String username;

    @ApiModelProperty("密码")
    @NotBlank(message = "密码不能为空")
    private String password;

    @ApiModelProperty("重复密码")
    @NotBlank(message = "重复密码不能为空")
    private String rePassword;

    @ApiModelProperty("手机号码")
    @NotBlank(message = "手机号不能为空")
    private String phone;

    @ApiModelProperty("验证码")
    @NotBlank(message = "验证码不能为空")
    private String code;

}
