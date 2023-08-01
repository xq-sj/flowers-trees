package com.sj.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.sj.entity.DictType;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

/**
 * <p>
 * 字典类型表 Mapper 接口
 * </p>
 *
 * @author 花树
 * @since 2023-07-26
 */
@Mapper
public interface DictTypeMapper extends BaseMapper<DictType> {

    /**
     * 根据字典编码获取字典名字
     *
     * @param dictCode 字典编码
     * @return 字典名字
     */
    @Select("select dict_name from dict_type where dict_code = #{dictCode}")
    String getDictNameByCode(@Param("dictCode") String dictCode);
}
