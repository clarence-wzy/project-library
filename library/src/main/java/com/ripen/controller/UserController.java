package com.ripen.controller;

import com.ripen.dao.entity.SysUser;
import com.ripen.service.SysUserService;
import com.ripen.util.JsonResult;
import com.ripen.util.Page;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;
import org.slf4j.*;

/**
 * 用户控制层
 *
 * @author Ripen.Y
 * @version 2021/01/09 23:17
 * @see SysUserService
 * @since 2021/01/09
 */
@Api(value = "用户控制层")
@RestController
@RequestMapping(value = "/user")
public class UserController {
    public final Logger LOGGER = LoggerFactory.getLogger (this.getClass ());

    @Autowired
    private SysUserService sysUserService;

    @ApiOperation(value = "添加用户")
    @PostMapping(value = "/add")
    @Transactional()
    public JsonResult add (@RequestBody SysUser sysUser)
    {
        if (sysUser == null) {
            return JsonResult.errorMsg("参数错误");
        }
        String result = sysUserService.addUser(sysUser);
        return result == null ? JsonResult.errorMsg("add error") : JsonResult.ok(result);
    }

    @ApiOperation(value = "获取用户信息")
    @GetMapping(value = "/get")
    public JsonResult get (@RequestBody SysUser sysUser, @RequestBody Page page)
    {
        return JsonResult.ok(sysUserService.getUserWithCondition(sysUser, page));
    }

    @ApiOperation(value = "更新用户信息")
    @PostMapping(value = "/update")
    @Transactional()
    public JsonResult update (@RequestBody SysUser sysUser)
    {
        return JsonResult.ok(sysUserService.updateUser(sysUser));
    }


}
