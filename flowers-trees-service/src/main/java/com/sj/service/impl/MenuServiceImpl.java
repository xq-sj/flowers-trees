package com.sj.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.sj.entity.Menu;
import com.sj.mapper.MenuMapper;
import com.sj.service.MenuService;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 菜单表 服务实现类
 * </p>
 *
 * @author 花树
 * @since 2023-07-26
 */
@Service
public class MenuServiceImpl extends ServiceImpl<MenuMapper, Menu> implements MenuService {

}
