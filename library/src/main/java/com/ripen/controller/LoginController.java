package com.ripen.controller;

import com.ripen.dao.entity.SysUser;
import com.ripen.service.SysUserService;
import com.ripen.util.JsonResult;
import com.ripen.util.ShiroUtil;
import io.swagger.annotations.ApiOperation;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.IncorrectCredentialsException;
import org.apache.shiro.authc.UnknownAccountException;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.subject.Subject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 登录控制层
 *
 * @author Ripen.Y
 * @version 2021/01/17 0:57
 * @since 2021/01/17
 */
@Controller
@CrossOrigin
public class LoginController {

    @Autowired
    private SysUserService userService;

    @ApiOperation(value = "拦截后返回登录页的接口")
    @RequestMapping(value = "/toLogin")
    public Object toLogin() {
        return "/login";
    }

    @ApiOperation(value = "登录接口")
    @PostMapping(value = "/login")
    @ResponseBody
    public Object login(@RequestParam String account, String pwd,
                        Model model, HttpServletRequest request)
    {
        HttpSession session = request.getSession();
        Subject subject = SecurityUtils.getSubject();
        UsernamePasswordToken token = new UsernamePasswordToken(account, pwd);
        Map<String, Object> map = new HashMap<>(4);
        try {
            subject.login(token);
            map.put("token", ShiroUtil.getSession().getId().toString());
            SysUser tempSysUser = new SysUser();
            tempSysUser.setAccount(account);
            List<SysUser> userList = userService.getUserWithCondition(tempSysUser, null);
            if (userList != null && userList.size() == 1) {
                map.put("user", userList.get(0));
                //  session存储用户信息
                session.setAttribute("token", ShiroUtil.getSession().getId().toString());
                session.setAttribute("user", userList.get(0));
            }
        } catch (UnknownAccountException e) {
            //  登录失败：用户名不存在
            model.addAttribute("msg", "用户名不存在");
            return JsonResult.errorMsg("用户名不存在");
        } catch (IncorrectCredentialsException e) {
            //  登录失败：密码错误
            model.addAttribute("msg", "密码错误");
            return JsonResult.errorMsg("密码错误");
        }
        return JsonResult.ok(map);
    }

    @ApiOperation(value = "退出登录接口")
    @GetMapping(value = "/logout")
    @ResponseBody
    public Object logout(HttpSession session) {
        //  session失效
        session.invalidate();
        SecurityUtils.getSubject().logout();
        return JsonResult.ok("您已退出登录");
    }

    @ApiOperation(value = "未授权提示")
    @GetMapping(value = "/noAuth")
    @ResponseBody
    public Object noAuth() {
        return JsonResult.errorMsg("您没有该授权");
    }

}
