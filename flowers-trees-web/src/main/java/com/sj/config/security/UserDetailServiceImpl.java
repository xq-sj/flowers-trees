package com.sj.config.security;

import com.sj.entity.User;
import com.sj.service.UserService;
import com.sj.utils.response.ResultInfo;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Collection;

/**
 * @ClassName UserDetailServiceImpl
 * @Author 闪光灯
 * @Date 2023/7/27 22:28
 * @Description SpringSecurity登录实现类
 */
@Service
public class UserDetailServiceImpl implements UserDetailsService {

    @Resource
    private UserService userService;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = userService.lambdaQuery().eq(User::getUsername, username).or().eq(User::getPhone, username).one();
        if (user == null) {
            throw new UsernameNotFoundException(ResultInfo.USER_NO_REGISTER.getMessage());
        }
        return new SecurityUser(user.getId(), user.getUsername(), user.getPassword(), user.getStatus() == 1, getAuthorities(user.getId(), user.getUsername()));
    }

    public Collection<? extends GrantedAuthority> getAuthorities(Integer id, String username) {
        String authorities = userService.getAuthorities(id, username);
        return AuthorityUtils.commaSeparatedStringToAuthorityList(authorities);
    }
}
