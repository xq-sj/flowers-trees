package com.sj.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.sj.entity.Comment;
import com.sj.mapper.CommentMapper;
import com.sj.service.CommentService;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 评论表 服务实现类
 * </p>
 *
 * @author 花树
 * @since 2023-07-26
 */
@Service
public class CommentServiceImpl extends ServiceImpl<CommentMapper, Comment> implements CommentService {

}
