package com.sj.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.sj.entity.Menu;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.util.List;

/**
 * <p>
 * 菜单表 Mapper 接口
 * </p>
 *
 * @author 花树
 * @since 2023-07-26
 */
@Mapper
public interface MenuMapper extends BaseMapper<Menu> {

    /**
     * 根据角色id获取菜单列表
     *
     * @param roleId 角色id
     * @return 菜单列表
     */
    @Select("select id, menu_name from menu")
    List<Menu> getMenuListByRoleId(Integer roleId);

    /**
     * 根据角色id获取权限信息
     *
     * @param roleId 角色id
     * @return 权限信息
     */
    @Select("select perms from menu where id in (select menu_id from role_menu where role_id = #{roleId})")
    List<String> getAuthorityByRoleId(Integer roleId);
}
