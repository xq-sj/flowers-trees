package com.sj.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.sj.entity.Menu;

import java.util.List;

/**
 * <p>
 * 菜单表 服务类
 * </p>
 *
 * @author 花树
 * @since 2023-07-26
 */
public interface MenuService extends IService<Menu> {

    /**
     * 根据角色id获取当前角色的菜单列表
     *
     * @param roleId 角色id
     * @return 菜单列表
     */
    List<Menu> getMenuListByRoleId(Integer roleId);

    /**
     * 根据角色id获取当前角色的权限信息
     *
     * @param roleId 角色id
     * @return 权限信息
     */
    List<String> getAuthorityByRoleId(Integer roleId);
}
