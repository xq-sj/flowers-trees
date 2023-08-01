package com.sj.entity;

import com.baomidou.mybatisplus.annotation.*;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateTimeDeserializer;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateTimeSerializer;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * <p>
 * 字典数据表
 * </p>
 *
 * @author 花树
 * @since 2023-07-26
 */
@Getter
@Setter
@Accessors(chain = true)
@TableName("dict_data")
@ApiModel(value = "DictData对象", description = "字典数据表")
public class DictData implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty("主键id 自增长")
    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    @ApiModelProperty("字典类型id")
    private Integer dictTypeId;

    @ApiModelProperty("显示顺序，数值越小越靠前")
    private Integer dictSort;

    @ApiModelProperty("字典标签")
    private String dictLabel;

    @ApiModelProperty("字典项编码")
    private String dictDataCode;

    @ApiModelProperty("字典值")
    private String dictValue;

    @ApiModelProperty("是否为数值")
    private Integer isNumber;

    @ApiModelProperty("样式属性")
    private String cssClass;

    @ApiModelProperty("类型属性")
    private String typeClass;

    @ApiModelProperty("备注")
    private String remark;

    @ApiModelProperty("是否默认[1：是，0：否]")
    private Integer isDefault;

    @ApiModelProperty("数据状态[1：启用，0：禁用]")
    private Integer status;

    @ApiModelProperty("创建时间")
    @TableField(fill = FieldFill.INSERT)
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @JsonDeserialize(using = LocalDateTimeDeserializer.class)
    @JsonSerialize(using = LocalDateTimeSerializer.class)
    private LocalDateTime createTime;

    @ApiModelProperty("创建人，用户名")
    private String createBy;

    @ApiModelProperty("更新时间")
    @TableField(fill = FieldFill.INSERT_UPDATE)
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @JsonDeserialize(using = LocalDateTimeDeserializer.class)
    @JsonSerialize(using = LocalDateTimeSerializer.class)
    private LocalDateTime updateTime;

    @ApiModelProperty("最后更新人，用户名")
    private String updateBy;
}
