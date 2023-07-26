package com.sj.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.sj.entity.User;
import org.apache.ibatis.annotations.Mapper;

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

}
