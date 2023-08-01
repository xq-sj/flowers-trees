package com.sj.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.sj.entity.DictType;
import com.sj.mapper.DictTypeMapper;
import com.sj.service.DictTypeService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * <p>
 * 字典类型表 服务实现类
 * </p>
 *
 * @author 花树
 * @since 2023-07-26
 */
@Service
public class DictTypeServiceImpl extends ServiceImpl<DictTypeMapper, DictType> implements DictTypeService {

    @Resource
    private DictTypeMapper dictTypeMapper;

    @Override
    public String getDictNameByCode(String dictCode) {
        return dictTypeMapper.getDictNameByCode(dictCode);
    }
}
