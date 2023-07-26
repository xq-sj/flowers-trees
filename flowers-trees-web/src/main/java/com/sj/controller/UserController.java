package com.sj.controller;

import com.sj.entity.Article;
import com.sj.entity.User;
import com.sj.service.UserService;
import com.sj.utils.response.ApiResult;
import com.sj.utils.response.ResultInfo;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.Getter;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

/**
 * <p>
 * 用户表 前端控制器
 * </p>
 *
 * @author 花树
 * @since 2023-07-26
 */
@RestController
@RequestMapping("/user")
@Api(tags = "用户模块")
public class UserController {
    
}
