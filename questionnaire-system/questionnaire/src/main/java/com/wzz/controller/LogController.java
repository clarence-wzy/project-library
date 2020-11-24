package com.wzz.controller;

import com.wzz.entity.User;
import com.wzz.log.entity.Log;
import com.wzz.log.service.LogService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpSession;
import java.util.List;

/**
 * <p>Title: IndexController</p>
 * <p>Description: </p>
 *
 * @author lizihao
 * @version 1.0.0
 * @date 2019/8/29 19:49
 */
@Api(tags = "日志信息接口")
@RestController
public class LogController {

    @Resource
    private LogService logService;

    @ApiOperation("获取用户操作日志")
    @RequestMapping(value = "log/", method = RequestMethod.GET, produces = "application/json")
    @ResponseBody
    public List<Log> getLogsByUserId(HttpSession session) {
        User user = (User)session.getAttribute("user");
        return logService.getLogsByUserId(user.getUserId());
    }
}
