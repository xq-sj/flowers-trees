package com.sj.config.flowerstrees.user;

import com.sj.entity.User;
import lombok.Data;

/**
 * @ClassName LoginUser
 * @Author 闪光灯
 * @Date 2023/7/27 21:42
 * @Description 登录用户数据模型
 */
@Data
public class LoginUser {

    /**
     * 登录用户的token令牌
     */
    private String token;


    /**
     * 登录用户的权限
     */
    private String authority;

    /**
     * 用户信息
     */
    private User user;

    public static LoginUserBuilder builder() {
        return new LoginUserBuilder();
    }


    public static final class LoginUserBuilder {
        private String token;
        private String authority;
        private User user;

        private LoginUserBuilder() {
        }

        public LoginUserBuilder token(String token) {
            this.token = token;
            return this;
        }

        public LoginUserBuilder authority(String authority) {
            this.authority = authority;
            return this;
        }

        public LoginUserBuilder user(User user) {
            this.user = user;
            return this;
        }

        public LoginUser build() {
            LoginUser loginUser = new LoginUser();
            loginUser.setToken(token);
            loginUser.setAuthority(authority);
            loginUser.setUser(user);
            return loginUser;
        }
    }
}
