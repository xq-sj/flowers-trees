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
 * 用户表
 * </p>
 *
 * @author 花树
 * @since 2023-07-26
 */
@Getter
@Setter
@Accessors(chain = true)
@ApiModel(value = "User对象", description = "用户表")
public class User implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty("主键id 自增长")
    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    @ApiModelProperty("用户名:用户登录标志")
    private String username;

    @ApiModelProperty("昵称")
    private String nickname;

    @ApiModelProperty("密码")
    private String password;

    @ApiModelProperty("手机号")
    private String phone;

    @ApiModelProperty("邮箱")
    private String email;

    @ApiModelProperty("QQ")
    private String qq;

    @ApiModelProperty("微信")
    private String weChat;

    @ApiModelProperty("性别[0：女，1：男，2：保密]")
    private Integer gender;

    @ApiModelProperty("openId")
    private String openId;

    @ApiModelProperty("头像")
    private String avatar;

    @ApiModelProperty("简介")
    private String introduction;

    @ApiModelProperty("生日")
    @JsonFormat(pattern = "yyyy-MM-dd")
    @JsonDeserialize(using = LocalDateTimeDeserializer.class)
    @JsonSerialize(using = LocalDateTimeSerializer.class)
    private LocalDateTime birthday;

    @ApiModelProperty("生肖")
    private String chineseZodiac;

    @ApiModelProperty("星座")
    private String constellation;

    @ApiModelProperty("最后登录时间")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @JsonDeserialize(using = LocalDateTimeDeserializer.class)
    @JsonSerialize(using = LocalDateTimeSerializer.class)
    private LocalDateTime lastLogin;

    @ApiModelProperty("最后登录ip")
    private String lastIp;

    @ApiModelProperty("注册ip")
    private String registerIp;

    @ApiModelProperty("状态[1：启用，0：禁用]")
    private Integer status;

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

    public static UserBuilder builder() {
        return new UserBuilder();
    }


    public static final class UserBuilder {
        private Integer id;
        private String username;
        private String nickname;
        private String password;
        private String phone;
        private String email;
        private String qq;
        private String weChat;
        private Integer gender;
        private String openId;
        private String avatar;
        private String introduction;
        private LocalDateTime birthday;
        private String chineseZodiac;
        private String constellation;
        private LocalDateTime lastLogin;
        private String lastIp;
        private String registerIp;
        private Integer status;
        private LocalDateTime createTime;
        private LocalDateTime updateTime;
        private String updateBy;

        private UserBuilder() {
        }

        public UserBuilder id(Integer id) {
            this.id = id;
            return this;
        }

        public UserBuilder username(String username) {
            this.username = username;
            return this;
        }

        public UserBuilder nickname(String nickaname) {
            this.nickname = nickaname;
            return this;
        }

        public UserBuilder password(String password) {
            this.password = password;
            return this;
        }

        public UserBuilder phone(String phone) {
            this.phone = phone;
            return this;
        }

        public UserBuilder email(String email) {
            this.email = email;
            return this;
        }

        public UserBuilder qq(String qq) {
            this.qq = qq;
            return this;
        }

        public UserBuilder weChat(String weChat) {
            this.weChat = weChat;
            return this;
        }

        public UserBuilder gender(Integer gender) {
            this.gender = gender;
            return this;
        }

        public UserBuilder openId(String openId) {
            this.openId = openId;
            return this;
        }

        public UserBuilder avatar(String avatar) {
            this.avatar = avatar;
            return this;
        }

        public UserBuilder introduction(String introduction) {
            this.introduction = introduction;
            return this;
        }

        public UserBuilder birthday(LocalDateTime birthday) {
            this.birthday = birthday;
            return this;
        }

        public UserBuilder chineseZodiac(String chineseZodiac) {
            this.chineseZodiac = chineseZodiac;
            return this;
        }

        public UserBuilder constellation(String constellation) {
            this.constellation = constellation;
            return this;
        }

        public UserBuilder lastLogin(LocalDateTime lastLogin) {
            this.lastLogin = lastLogin;
            return this;
        }

        public UserBuilder lastIp(String lastIp) {
            this.lastIp = lastIp;
            return this;
        }

        public UserBuilder registerIp(String registerIp) {
            this.registerIp = registerIp;
            return this;
        }

        public UserBuilder status(Integer status) {
            this.status = status;
            return this;
        }

        public UserBuilder createTime(LocalDateTime createTime) {
            this.createTime = createTime;
            return this;
        }

        public UserBuilder updateTime(LocalDateTime updateTime) {
            this.updateTime = updateTime;
            return this;
        }

        public UserBuilder updateBy(String updateBy) {
            this.updateBy = updateBy;
            return this;
        }

        public User build() {
            User user = new User();
            user.setId(id);
            user.setUsername(username);
            user.setNickname(nickname);
            user.setPassword(password);
            user.setPhone(phone);
            user.setEmail(email);
            user.setQq(qq);
            user.setWeChat(weChat);
            user.setGender(gender);
            user.setOpenId(openId);
            user.setAvatar(avatar);
            user.setIntroduction(introduction);
            user.setBirthday(birthday);
            user.setChineseZodiac(chineseZodiac);
            user.setConstellation(constellation);
            user.setLastLogin(lastLogin);
            user.setLastIp(lastIp);
            user.setRegisterIp(registerIp);
            user.setStatus(status);
            user.setCreateTime(createTime);
            user.setUpdateTime(updateTime);
            user.setUpdateBy(updateBy);
            return user;
        }
    }
}
