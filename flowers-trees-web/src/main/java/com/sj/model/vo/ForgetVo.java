package com.sj.model.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import java.io.Serializable;

/**
 * @ClassName ForgetVo
 * @Author 闪光灯
 * @Date 2023/8/1 21:56
 * @Description 忘记密码参数数据模型
 */
@Data
@ApiModel(value = "忘记密码参数", description = "忘记密码参数")
public class ForgetVo implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty("手机号")
    @NotBlank(message = "手机号不能为空")
    private String phone;

    @ApiModelProperty("密码")
    @NotBlank(message = "密码不能为空")
    private String password;

    @ApiModelProperty("重复密码")
    @NotBlank(message = "重复密码不能为空")
    private String rePassword;

    @ApiModelProperty("验证码")
    @NotBlank(message = "验证码不能为空")
    private String code;
}
