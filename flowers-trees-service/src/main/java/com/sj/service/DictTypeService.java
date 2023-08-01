package com.sj.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.sj.entity.DictType;

/**
 * <p>
 * 字典类型表 服务类
 * </p>
 *
 * @author 花树
 * @since 2023-07-26
 */
public interface DictTypeService extends IService<DictType> {

    /**
     * 根据字典编码，获取字典名字
     *
     * @param dictCode 字典编码
     * @return 字典名字
     */
    String getDictNameByCode(String dictCode);
}
