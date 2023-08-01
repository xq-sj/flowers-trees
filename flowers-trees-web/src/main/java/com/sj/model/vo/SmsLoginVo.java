package com.sj.model.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import java.io.Serializable;

/**
 * @ClassName SmsLoginVo
 * @Author 闪光灯
 * @Date 2023/8/1 22:11
 * @Description 验证码登录参数数据模型
 */
@Data
@ApiModel(value = "验证码登录参数", description = "验证码登录参数")
public class SmsLoginVo implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty("手机号")
    @NotBlank(message = "手机号不能为空")
    private String phone;

    @ApiModelProperty("验证码")
    @NotBlank(message = "验证码不能为空")
    private String code;
}
