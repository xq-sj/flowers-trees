package com.sj.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.sj.entity.UserRole;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

/**
 * <p>
 * 用户角色关联表 Mapper 接口
 * </p>
 *
 * @author 花树
 * @since 2023-07-26
 */
@Mapper
public interface UserRoleMapper extends BaseMapper<UserRole> {


    /**
     * 根据用户id获取当前用户角色id
     *
     * @param id 用户id
     * @return 角色id
     */
    @Select("select role_id from user_role where user_id = #{id}")
    Integer getRoleIdByUserId(Integer id);
}
