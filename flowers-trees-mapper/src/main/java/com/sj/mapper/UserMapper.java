package com.sj.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.sj.entity.User;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

/**
 * <p>
 * 用户表 Mapper 接口
 * </p>
 *
 * @author 花树
 * @since 2023-07-26
 */
@Mapper
public interface UserMapper extends BaseMapper<User> {

    /**
     * 根据手机号码获取用户个数
     *
     * @param phone 手机号码
     * @return 用户个数
     */
    @Select("select count(*) from user where phone = #{phone}")
    Long getUserCountByPhone(@Param("phone") String phone);

    /**
     * 根据用户名获取用户个数
     *
     * @param username 用户名
     * @return 用户个数
     */
    @Select("select count(*) from user where username = #{username}")
    Long getUserCountByUsername(@Param("username") String username);
}
