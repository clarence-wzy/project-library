package com.ripen.controller;

import com.ripen.dao.entity.SysUser;
import com.ripen.service.SysUserService;
import com.ripen.util.JsonResult;
import com.ripen.util.Page;
import com.ripen.util.ThreeDes;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.interceptor.TransactionAspectSupport;
import org.springframework.web.bind.annotation.*;
import org.slf4j.*;

import java.util.List;

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

    @ApiOperation(value = "用户登录")
    @PostMapping(value = "/login")
    public JsonResult login (@RequestBody SysUser sysUser) {
        SysUser tempSysUser = new SysUser();
        tempSysUser.setAccount(sysUser.getAccount());
        List<SysUser> sysUserList = sysUserService.getUserWithCondition(tempSysUser, null);
        if (sysUserList == null || sysUserList.size() <= 0) {
            return JsonResult.errorMsg("Login fail, account does not exist.");
        }
        String pwd = ThreeDes.encryptThreeDESECB(sysUser.getPwd());
        if (!pwd.equals(sysUserList.get(0).getPwd())) {
            return JsonResult.errorMsg("Login fail, password mistake.");
        }
        return JsonResult.ok("Login success.");
    }

    @ApiOperation(value = "添加用户")
    @PostMapping(value = "/add")
    @Transactional()
    public JsonResult add (@RequestBody SysUser sysUser)
    {
        if (sysUser == null) {
            return JsonResult.errorMsg("参数错误");
        }
        String result = sysUserService.addUser(sysUser);
        if (result == null) {
            TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
            return JsonResult.errorMsg("add error");
        }
        return JsonResult.ok(result);
    }

    @ApiOperation(value = "获取用户信息")
    @GetMapping(value = "/get")
    public JsonResult get (SysUser sysUser, Page page)
    {
        return JsonResult.ok(sysUserService.getUserWithCondition(sysUser, page));
    }

    @ApiOperation(value = "更新用户信息")
    @PostMapping(value = "/update")
    @Transactional()
    public JsonResult update (@RequestBody SysUser sysUser)
    {
        if (sysUserService.updateUser(sysUser) == -1) {
            TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
            return JsonResult.errorMsg("update error");
        }
        return JsonResult.ok();
    }

}
