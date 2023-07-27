package com.sj.controller;

import io.swagger.annotations.Api;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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
