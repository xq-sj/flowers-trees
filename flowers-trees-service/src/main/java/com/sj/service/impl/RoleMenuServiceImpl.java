package com.sj.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.sj.entity.Menu;
import com.sj.entity.RoleMenu;
import com.sj.mapper.RoleMenuMapper;
import com.sj.service.RoleMenuService;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * <p>
 * 角色菜单关联表 服务实现类
 * </p>
 *
 * @author 花树
 * @since 2023-07-26
 */
@Service
public class RoleMenuServiceImpl extends ServiceImpl<RoleMenuMapper, RoleMenu> implements RoleMenuService {

}
