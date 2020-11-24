package com.wzy.controller;

import com.wzy.config.ThreeDes;
import com.wzy.entity.User;
import com.wzy.service.UserService;
import com.wzy.util.JSONResult;
import com.wzy.util.ShiroUtils;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
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
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @Package: com.wzy.controller
 * @Author: Clarence1
 * @Date: 2019/12/9 17:45
 */
@Controller
@CrossOrigin
@Slf4j
public class LoginController {

    /**
     * 加密的key
     */
    final String key = "cf410f84904a44cc8a7f48fc4134e8f9";

    @Autowired
    private UserService userService;

    @ApiOperation(value = "拦截后返回登录页的接口")
    @GetMapping(value = "/toLogin")
    public Object toLogin() {
        log.info("==============登录重定向==============");
        return "login";
    }

    @ApiOperation(value = "登录测试页面的接口")
    @PostMapping(value = "/login")
    @ResponseBody
    public Object login(@RequestParam String name, String password, Model model, HttpServletRequest request) throws Exception {
        log.info("==============【" + name + "】用户正在登录==============");
        HttpSession session = request.getSession();
        //  获取Subject
        Subject subject = SecurityUtils.getSubject();

        //  封装用户数据
        UsernamePasswordToken token = new UsernamePasswordToken(name, password);
        //  执行登录方法
        Map<String, Object> map = new HashMap<>(16);
        try {
            subject.login(token);
            map.put("token", ShiroUtils.getSession().getId().toString());

            List<User> user = userService.findUserByUsernameWithRole(token.getUsername());
            map.put("user", user);

            //  session存储用户信息
            session.setAttribute("token", ShiroUtils.getSession().getId().toString());
            session.setAttribute("user", user);
        } catch (UnknownAccountException e) {
            //  登录失败：用户名不存在
            model.addAttribute("msg", "用户名不存在");
            return JSONResult.errorMsg("用户名不存在");
        } catch (IncorrectCredentialsException e) {
            //  登录失败：密码错误
            model.addAttribute("msg", "密码错误");
            return JSONResult.errorMsg("密码错误");
        }
        return JSONResult.ok(map);
    }

    @ApiOperation(value = "退出登录接口")
    @GetMapping(value = "/logout")
    @ResponseBody
    public Object logout(HttpSession session) {
        log.info("==============退出登录==============");
        //  session失效
        session.invalidate();

        SecurityUtils.getSubject().logout();
        return JSONResult.ok("您已退出登录");
    }

    @ApiOperation(value = "未授权提示")
    @GetMapping(value = "/noAuth")
    @ResponseBody
    public Object noAuth() {
        log.info("==============未授权==============");
        return JSONResult.errorMsg("您没有该授权");
    }

    @ApiOperation("根据用户名为密码加密")
    @GetMapping(value = "/encrypt/{username}")
    @ResponseBody
    public Object updatePasswordWithEncryption(@PathVariable String username) throws Exception {
        log.info("==============用户【" +username+ "】密码加密中==============");
        ThreeDes threeDES = new ThreeDes();

        User user = userService.findUserByUsername(username);
        String password = user.getPassword();
        //  密码加密
        password = threeDES.encryptThreeDESECB(URLEncoder.encode(password, "UTF-8"), key);

        return userService.updatePasswordWithEncryption(username, password) == 1 ? JSONResult.ok("加密成功") : JSONResult.errorMsg("加密失败");
    }

}
