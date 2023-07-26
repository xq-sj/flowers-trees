package com.sj.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.sj.entity.RoleMenu;
import org.apache.ibatis.annotations.Mapper;

/**
 * <p>
 * 角色菜单关联表 Mapper 接口
 * </p>
 *
 * @author 花树
 * @since 2023-07-26
 */
@Mapper
public interface RoleMenuMapper extends BaseMapper<RoleMenu> {

}
