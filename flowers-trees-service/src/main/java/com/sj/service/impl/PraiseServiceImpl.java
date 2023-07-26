package com.sj.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.sj.entity.Praise;
import com.sj.mapper.PraiseMapper;
import com.sj.service.PraiseService;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 点赞表 服务实现类
 * </p>
 *
 * @author 花树
 * @since 2023-07-26
 */
@Service
public class PraiseServiceImpl extends ServiceImpl<PraiseMapper, Praise> implements PraiseService {

}
