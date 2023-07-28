package com.sj.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.sj.entity.UserRole;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 用户角色关联表 服务类
 * </p>
 *
 * @author 花树
 * @since 2023-07-26
 */
public interface UserRoleService extends IService<UserRole> {

    /**
     * 根据用户id获取角色id
     *
     * @param id 用户id
     * @return 角色id
     */
    Integer getRoleIdByUserId(Integer id);
}
