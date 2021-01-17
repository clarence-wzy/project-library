package com.ripen.controller;

import com.ripen.dao.entity.SysAdmin;
import com.ripen.service.SysAdminService;
import com.ripen.util.JsonResult;
import com.ripen.util.Page;
import com.ripen.util.ThreeDes;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.interceptor.TransactionAspectSupport;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * 管理员控制层
 *
 * @author Ripen.Y
 * @version 2021/01/09 23:17
 * @see SysAdminService
 * @since 2021/01/09
 */
@RestController
@RequestMapping(value = "/admin")
@Api(value = "管理员控制层")
public class AdminController {
    public final Logger LOGGER = LoggerFactory.getLogger (this.getClass ());

    @Autowired
    private SysAdminService sysAdminService;

    @ApiOperation(value = "管理员登录")
    @PostMapping(value = "/login")
    public JsonResult login (@RequestBody SysAdmin SysAdmin) {
        SysAdmin tempSysAdmin = new SysAdmin();
        tempSysAdmin.setAdmId(SysAdmin.getAdmId());
        List<SysAdmin> sysAdminList = sysAdminService.getAdminWithCondition(tempSysAdmin, null);
        if (sysAdminList == null || sysAdminList.size() <= 0) {
            return JsonResult.errorMsg("Login fail, account does not exist.");
        }
        String pwd = ThreeDes.encryptThreeDESECB(SysAdmin.getPwd());
        if (!pwd.equals(sysAdminList.get(0).getPwd())) {
            return JsonResult.errorMsg("Login fail, password mistake.");
        }
        return JsonResult.ok("Login success.");
    }

    @ApiOperation(value = "添加管理员")
    @PostMapping(value = "/add")
    @Transactional()
    public JsonResult add (@RequestBody SysAdmin sysAdmin)
    {
        if (sysAdmin == null) {
            return JsonResult.errorMsg("参数错误");
        }
        String result = sysAdminService.addAdmin(sysAdmin);
        if (result == null) {
            TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
            return JsonResult.errorMsg("add error");
        }
        return JsonResult.ok(result);
    }

    @ApiOperation(value = "获取管理员信息")
    @GetMapping(value = "/get")
    public JsonResult get (SysAdmin sysAdmin, Page page)
    {
        return JsonResult.ok(sysAdminService.getAdminWithCondition(sysAdmin, page));
    }

    @ApiOperation(value = "更新管理员信息")
    @PostMapping(value = "/update")
    @Transactional()
    public JsonResult update (@RequestBody SysAdmin sysAdmin)
    {
        if (sysAdminService.updateAdmin(sysAdmin) == -1) {
            TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
            return JsonResult.errorMsg("update error");
        }
        return JsonResult.ok();
    }

}
