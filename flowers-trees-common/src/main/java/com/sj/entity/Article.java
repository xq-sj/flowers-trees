package com.sj.entity;

import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
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
 * 文章表
 * </p>
 *
 * @author 花树
 * @since 2023-07-26
 */
@Getter
@Setter
@Accessors(chain = true)
@ApiModel(value = "Article对象", description = "文章表")
public class Article implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty("主键id 自增长")
    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    @ApiModelProperty("文章封面")
    private String cover;

    @ApiModelProperty("文章标题")
    private String title;

    @ApiModelProperty("文章内容")
    private String content;

    @ApiModelProperty("文章视频链接")
    private String videoUrl;

    @ApiModelProperty("浏览量")
    private Integer viewCount;

    @ApiModelProperty("点赞数")
    private Integer praiseCount;

    @ApiModelProperty("评论数")
    private Integer commentCount;

    @ApiModelProperty("收藏数")
    private Integer collectCount;

    @ApiModelProperty("可见状态[1：所有人可见，0：仅自己可见]")
    private Integer visibleStatus;

    @ApiModelProperty("文章状态[1：已完成，0：草稿]")
    private Integer articleStatus;

    @ApiModelProperty("文章置顶[所有文章默认0，大于0都为置顶，然后根据置顶数值排序，数值越大，排序越靠前]")
    private Integer topSort;

    @ApiModelProperty("是否为推荐文章[1：是，0：否]")
    private Integer recommendStatus;

    @ApiModelProperty("是否启用评论[1：是，0：否]")
    private Integer commentStatus;

    @ApiModelProperty("作者id")
    private Integer userId;

    @ApiModelProperty("分类id")
    private Integer categoryId;

    @ApiModelProperty("创建时间")
    @TableField(fill = FieldFill.INSERT)
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @JsonDeserialize(using = LocalDateTimeDeserializer.class)
    @JsonSerialize(using = LocalDateTimeSerializer.class)
    private LocalDateTime createTime;

    @ApiModelProperty("更新时间")
    @TableField(fill = FieldFill.INSERT_UPDATE)
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @JsonDeserialize(using = LocalDateTimeDeserializer.class)
    @JsonSerialize(using = LocalDateTimeSerializer.class)
    private LocalDateTime updateTime;

    @ApiModelProperty("最后修改人，用户名")
    private String updateBy;
}
