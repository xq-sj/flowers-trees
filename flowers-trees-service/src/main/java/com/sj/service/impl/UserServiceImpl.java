package com.sj.service.impl;

import cn.hutool.json.JSONUtil;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.sj.config.flowerstrees.user.LoginUser;
import com.sj.config.flowerstrees.user.UserConfig;
import com.sj.constant.SecurityConst;
import com.sj.entity.Menu;
import com.sj.entity.Role;
import com.sj.entity.User;
import com.sj.entity.UserRole;
import com.sj.mapper.UserMapper;
import com.sj.service.*;
import com.sj.utils.redis.RedisUtils;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;
import java.util.stream.Collectors;

/**
 * <p>
 * 用户表 服务实现类
 * </p>
 *
 * @author 花树
 * @since 2023-07-26
 */
@Service
public class UserServiceImpl extends ServiceImpl<UserMapper, User> implements UserService {

    @Resource
    private UserRoleService userRoleService;

    @Resource
    private RoleService roleService;

    @Resource
    private MenuService menuService;

    @Resource
    private RedisUtils redisUtils;

    @Resource
    private UserConfig userConfig;

    @Resource
    private UserMapper userMapper;

    @Override
    public String getAuthorities(Integer id, String username) {
        String loginUserKey = userConfig.getLoginUserKey();
        // 首先判断缓存中是否有该用户的权限信息
        boolean flag = redisUtils.hHasKey(loginUserKey, username);
        if (flag) {
            // 如果有
            String loginUserJson = redisUtils.hget(loginUserKey, username).toString();
            LoginUser loginUser = JSONUtil.toBean(loginUserJson, LoginUser.class);
            if (loginUser != null) {
                return loginUser.getAuthority();
            }
        }
        String authority = "";
        // 如果没有，查询数据库，并且将查询到的数据存在redis中
        // 查询当前用户的角色id
        Integer roleId = userRoleService.getRoleIdByUserId(id);
        // 查询当前角色的基本信息
        Role role = roleService.getById(roleId);
        authority += SecurityConst.ROLE_PREFIX + role.getRoleCode();
        // 查询当前角色权限菜单列表
        List<String> authList = menuService.getAuthorityByRoleId(roleId);
        if (authList.size() > 0) {
            authority += ",";
            authority = authority.concat(String.join(",", authList));
        }
        // 保存当前用户的信息到redis
        LoginUser loginUser = new LoginUser();
        loginUser.setAuthority(authority);
        String loginUserJson = JSONUtil.toJsonStr(loginUser);
        redisUtils.hset(loginUserKey, username, loginUserJson);
        return authority;
    }

    @Override
    public Long getUserCountByPhone(String phone) {

        return userMapper.getUserCountByPhone(phone);
    }

    @Override
    public Long getUserCountByUsername(String username) {
        return userMapper.getUserCountByUsername(username);
    }
}
