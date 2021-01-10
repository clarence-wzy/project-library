package com.ripen.controller;

import com.ripen.dao.entity.SysAdmin;
import com.ripen.service.SysAdminService;
import com.ripen.util.JsonResult;
import com.ripen.util.Page;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

/**
 * 管理员控制层
 *
 * @author Ripen.Y
 * @version 2021/01/09 23:17
 * @see SysAdminService
 * @since 2021/01/09
 */
@Api(value = "管理员控制层")
@RestController
@RequestMapping(value = "/admin")
public class AdminController {
    public final Logger LOGGER = LoggerFactory.getLogger (this.getClass ());

    @Autowired
    private SysAdminService sysAdminService;

    @ApiOperation(value = "添加管理员")
    @PostMapping(value = "/add")
    @Transactional()
    public JsonResult add (@RequestBody SysAdmin sysAdmin)
    {
        if (sysAdmin == null) {
            return JsonResult.errorMsg("参数错误");
        }
        String result = sysAdminService.addAdmin(sysAdmin);
        return result == null ? JsonResult.errorMsg("add error") : JsonResult.ok(result);
    }

    @ApiOperation(value = "获取管理员信息")
    @GetMapping(value = "/get")
    public JsonResult get (@RequestBody SysAdmin sysAdmin, @RequestBody Page page)
    {
        return JsonResult.ok(sysAdminService.getAdminWithCondition(sysAdmin, page));
    }

    @ApiOperation(value = "更新管理员信息")
    @PostMapping(value = "/update")
    @Transactional()
    public JsonResult update (@RequestBody SysAdmin sysAdmin)
    {
        return JsonResult.ok(sysAdminService.updateAdmin(sysAdmin));
    }


}
