package com.wzz.controller;

import com.wzz.config.ThreeDES;
import com.wzz.entity.Permission;
import com.wzz.entity.RolePermission;
import com.wzz.entity.User;
import com.wzz.log.annotation.ControllerLog;
import com.wzz.service.UserService;
import com.wzz.util.ShiroUtils;
import com.wzz.util.TimeFormatUtil;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.IncorrectCredentialsException;
import org.apache.shiro.authc.UnknownAccountException;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.subject.Subject;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.net.URLEncoder;
import java.util.HashMap;
import java.util.Map;

/**
 * @author Clarence1
 */
@Api(tags = "用户信息接口")
@Controller
public class UserInfoController {

    /**
     * 加密的key
     */
    final String key = "cf410f84904a44cc8a7f48fc4134e8f9";

    @Resource
    UserService userService;

    @ControllerLog(description = "获取用户信息")
    @ApiOperation(value = "获取用户信息")
    @RequestMapping(value = "/user", method = RequestMethod.GET)
    @ResponseBody
    public Map<String, Object> getUserInfoByName(HttpSession session) {
        User user = (User)session.getAttribute("user");
        Map<String, Object> map = new HashMap<>(16);
        map.put("username", user.getUsername());
        map.put("roleId", user.getRoleId());

        //增加用户基本信息
        map.put("email",user.getEmailCode());
        map.put("maxEmail",user.getMaxEmail());
        map.put("maxMessage",user.getMaxMessage());
        map.put("maxStorage",user.getMaxStorage());
        map.put("maxAccount",user.getMaxAccount());
        map.put("latestTime", TimeFormatUtil.stampToTime(user.getLatestTime()));
        map.put("userId",user.getUserId());

        RolePermission rolePermission = userService.getPermIdByRoleId(user.getRoleId());
        Permission permission = userService.getPermDetailByPermId(rolePermission.getPermId());
        map.put("permDetail", permission.getPermDetail());

        return map;
    }

    @ApiOperation(value = "拦截后返回登录页的接口")
    @RequestMapping(value = "/toLogin", method = RequestMethod.GET)
    public Object toLogin(HttpServletResponse response) {
        response.setCharacterEncoding("utf-8");

        Map<String, Object> map = new HashMap<>(16);
        map.put("code", 200);
        map.put("msg", "未登录");
        return "login";
    }

    @ControllerLog(description = "用户登录")
    @ApiOperation(value = "登录测试页面的接口")
    @RequestMapping(value = "/login", method = RequestMethod.POST)
    @ResponseBody
    public Object login(@RequestParam String name, String password, Model model,
                        HttpServletResponse response, HttpServletRequest request) {
        response.setCharacterEncoding("utf-8");

        HttpSession session = request.getSession();

        //  获取Subject
        Subject subject = SecurityUtils.getSubject();
        //  封装用户数据
        UsernamePasswordToken token = new UsernamePasswordToken(name, password);
        //  执行登录方法
        Map<String, Object> map = new HashMap<>(16);
        try {
            subject.login(token);
            map.put("code", 100);
            map.put("msg", "您登录成功");
            map.put("token", ShiroUtils.getSession().getId().toString());

            User user = userService.getUserInfoByName(token.getUsername());
            map.put("userId", user.getUserId());
            map.put("username", user.getUsername());

            //  session存储用户信息
            session.setAttribute("token", ShiroUtils.getSession().getId().toString());
            session.setAttribute("user", user);

        } catch (UnknownAccountException e) {
            //  登录失败：用户名不存在
            model.addAttribute("msg", "用户名不存在");
            map.put("code", 200);
            map.put("msg", "用户名不存在");
        } catch (IncorrectCredentialsException e) {
            //  登录失败：密码错误
            model.addAttribute("msg", "密码错误");
            map.put("code", 200);
            map.put("msg", "密码错误");
        }
        return map;
    }

    @ApiOperation(value = "退出登录接口")
    @RequestMapping(value = "/logout", method = RequestMethod.GET)
    @ResponseBody
    public Object logout(HttpSession session, HttpServletResponse response) {
        response.setCharacterEncoding("utf-8");
        //  session失效
        session.invalidate();

        Map<String, Object> map = new HashMap<>(3);
        map.put("code", 100);
        map.put("msg", "您已退出登录");

        SecurityUtils.getSubject().logout();
        return map;
    }

    @ApiOperation(value = "未授权提示")
    @RequestMapping(value = "/noAuth", method = RequestMethod.GET)
    @ResponseBody
    public Object noAuth(HttpServletResponse response) {
        response.setCharacterEncoding("utf-8");

        Map<String, Object> map = new HashMap<>(16);
        map.put("code", 200);
        map.put("msg", "您没有该授权");
        return map;
    }

    @ApiOperation("根据用户名为密码加密")
    @RequestMapping(value = "/encrypt/{username}", method = RequestMethod.GET)
    @ResponseBody
    public Object updatePasswordWithEncryption(@PathVariable String username,
                                               HttpServletResponse response) throws Exception {
        response.setCharacterEncoding("utf-8");
        ThreeDES threeDES = new ThreeDES();

        User user = userService.getUserInfoByName(username);
        String password = user.getPassword();
        //  密码加密
        password = threeDES.encryptThreeDESECB(URLEncoder.encode(password, "UTF-8"), key);

        int i = userService.updatePasswordWithEncryption(username, password);
        return i == 1 ? "加密成功" : "加密失败";
    }


}
