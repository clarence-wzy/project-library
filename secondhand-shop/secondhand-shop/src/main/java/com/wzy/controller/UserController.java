package com.wzy.controller;

import com.aliyuncs.dysmsapi.model.v20170525.SendSmsResponse;
import com.wzy.config.ThreeDes;
import com.wzy.entity.AdminLog;
import com.wzy.entity.Mail;
import com.wzy.entity.Student;
import com.wzy.entity.User;
import com.wzy.service.AdminLogService;
import com.wzy.service.ProductService;
import com.wzy.service.UserService;
import com.wzy.util.*;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.http.HttpMethod;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.net.URL;
import java.net.URLEncoder;
import java.util.List;

/**
 * @Package: com.wzy.controller
 * @Author: Clarence1
 * @Date: 2019/11/26 20:29
 */
@RestController
@CrossOrigin
@RequestMapping(value = "/user")
@Slf4j
public class UserController {
    /**
     * 加密的key
     */
    final String key = "cf410f84904a44cc8a7f48fc4134e8f9";

    @Autowired
    private UserService userService;

    @Autowired
    private ProductService productService;

    @Autowired
    private AdminLogService adminLogService;

    @Autowired
    private RedisTemplate<Object, Object> redisTemplate;

    @ApiOperation(value = "注册用户")
    @PostMapping(value = "/register/{code}")
    public JSONResult addUser(@RequestBody User user, @PathVariable(value = "code") String code) throws Exception {
        log.info("==============注册用户【" + user + "】==============");
        //用户默认头像
        user.setHeadImage("http://106.15.74.177:9090/shop/user/person.png");

        List<Integer> adminIds = userService.findAllAdmin("二级管理员");
        int index = (int)(Math.random() * adminIds.size());
        user.setAdminId(adminIds.get(index));

        if (!user.getPhone().equals("")) {
            if (!code.equals(redisTemplate.opsForValue().get(user.getPhone() + "-phone-code"))) {
                return JSONResult.errorMsg("验证码错误");
            }
        }
        if (!user.getEmail().equals("")) {
            if (!code.equals(redisTemplate.opsForValue().get(user.getEmail() + "-mail-code"))) {
                return JSONResult.errorMsg("验证码错误");
            }
        }

        //  密码加密
        ThreeDes threeDES = new ThreeDes();
        String password = user.getPassword();
        password = threeDES.encryptThreeDESECB(URLEncoder.encode(password, "UTF-8"), key);
        user.setPassword(password);

        return userService.addUser(user) == 1? JSONResult.ok("注册成功"):JSONResult.errorMap("注册失败");
    }

    @ApiOperation(value = "根据用户id查询用户信息")
    @GetMapping(value = "/findUserByUserId/{user_id}")
    public JSONResult findUserByUserId(@PathVariable("user_id") int userId) {
        log.info("==============查询id【" + userId + "】的用户信息==============");
        return JSONResult.ok(userService.findUserByUserId(userId));
    }

    @ApiOperation(value = "根据用户名查询用户信息")
    @GetMapping(value = "/findUserByUsername/{username}")
    public JSONResult findUserByUsername(@PathVariable("username") String username) {
        log.info("==============查询【" + username + "】用户信息==============");
        return JSONResult.ok(userService.findUserByUsername(username));
    }

    @ApiOperation(value = "查询用户信息（含角色信息）")
    @GetMapping(value = "/findUserByUsernameWithRole/{username}")
    public JSONResult findUserByUsernameWithRole(@PathVariable("username") String username) {
        log.info("==============查询【" + username + "】用户信息（含角色信息）==============");
        return JSONResult.ok(userService.findUserByUsernameWithRole(username));
    }

    @ApiOperation(value = "查询角色信息")
    @GetMapping(value = "/getRolePermission/{role_name}")
    public JSONResult getRolePermission(@PathVariable("role_name") String roleName) {
        log.info("==============查询【" + roleName + "】角色信息==============");
        return JSONResult.ok(userService.getRolePermission(roleName));
    }

    @ApiOperation(value = "更新用户信息")
    @PostMapping(value = "/updateUserInfo")
    public JSONResult updateUserInfo(@RequestBody User user) {
        log.info("==============更新【" + user + "】用户信息==============");
        return userService.updateUserInfo(user) == 1 ? JSONResult.ok("更新用户信息成功"):JSONResult.errorMsg("更新用户信息失败");
    }

    @ApiOperation(value = "上传用户头像")
    @PostMapping(value = "/updateHeadImage/{username}")
    public JSONResult updateHeadImage(@PathVariable("username") String username ,@RequestParam(value = "image") MultipartFile image) {
        log.info("==============更新【" + username + "】用户的头像信息==============");
        //  上传商品图片
        try {
            if(!ApprovalUtil.pictureApprovalUtil(image).equals("合规")) {
                log.error("照片不合规");
            }
        } catch (Exception e) {
            log.error("检查图片出现异常，异常信息--》" + e.getMessage());
        }
        Object imagePath = productService.uploadPicture(image, "user/" + username + "/");

        //更新数据库头像信息
        int i = userService.updateHeadImage(username, String.valueOf(imagePath));
        if (i == 1) {
            log.info("==============更新用户【" + username + "】头像成功==============");
            return JSONResult.ok("更新头像成功");
        } else {
            return JSONResult.errorMsg("更新头像失败");
        }
    }

    @ApiOperation(value = "用户邮箱绑定")
    @PostMapping(value = "/bindEmail/{userId}/{email}/{code}")
    public JSONResult bindEmail(@PathVariable("userId") int userId, @PathVariable("email") String email, @PathVariable(value = "code") String code) {
        log.info("==============【" + userId + "】正在进行邮箱" + email + "绑定==============");
        if (!email.equals("")) {
            if (!code.equals(redisTemplate.opsForValue().get(email + "-mail-code"))) {
                return JSONResult.errorMsg("验证码错误");
            }
        }
        return userService.updateEmail(email, userId) == 1 ? JSONResult.ok("绑定邮箱成功"):JSONResult.errorMsg("绑定邮箱失败");
    }

    @ApiOperation(value = "用户手机绑定")
    @PostMapping(value = "/bindPhone/{userId}/{phone}/{code}")
    public JSONResult bindPhone(@PathVariable("userId") int userId, @PathVariable("phone") String phone, @PathVariable(value = "code") String code) {
        log.info("==============【" + userId + "】正在进行手机" + phone + "绑定==============");
        if (!phone.equals("")) {
            if (!code.equals(redisTemplate.opsForValue().get(phone + "-phone-code"))) {
                return JSONResult.errorMsg("验证码错误");
            }
        }
        return userService.updatePhone(phone, userId) == 1 ? JSONResult.ok("绑定手机成功"):JSONResult.errorMsg("绑定手机失败");
    }

    @ApiOperation(value = "学生认证")
    @PostMapping(value = "/scse")
    public JSONResult sendScse(@RequestBody Student student) {
        log.info("==============【" + student + "】正在进行学生认证==============");
        String password = Md5Util.getString(student.getPassword());
        String url = "http://my.scse.com.cn/login_pro.asp?number=" + student.getId() + "&pwd=" + password;
        HttpMethod method = HttpMethod.POST;
        MultiValueMap<String, String> params = new LinkedMultiValueMap<String, String>();
        String result = userService.sendScse(url, method, params);
        if (result.length() == 14) {
            return userService.updateStudentInfo(student.getUserId(), student.getId(), student.getPassword()) == 1? JSONResult.ok("认证成功"):JSONResult.errorMsg("更新信息失败");
        } else if (result.length() == 32) {
            return JSONResult.errorMap("密码错误");
        } else if (result.length() == 39) {
            return JSONResult.errorMap("该学号不存在");
        } else {
            return JSONResult.errorMsg("未知错误");
        }
    }

    @ApiOperation(value = "学生认证（外网）")
    @PostMapping(value = "/scse2")
    public JSONResult sendScse2(@RequestBody Student student) {
        log.info("==============【" + student + "】正在进行外网学生认证==============");

        Document document = null;
        try {
            document = Jsoup.parse(new URL("http://class.sise.com.cn:7001/sise/login.jsp"), 10000);
        } catch (IOException e) {
            e.printStackTrace();
        }
        Elements elements = document.getElementsByTag("input");
        String urlName = elements.get(0).attr("name");
        String urlValue = elements.get(0).attr("value");

        String url = "http://class.sise.com.cn:7001/sise/login_check_login.jsp?" + urlName + "=" + urlValue + "&token=0&random=1581399333591&username="
                + student.getId() + "&password=" + student.getPassword();
        HttpMethod method = HttpMethod.POST;
        MultiValueMap<String, String> params = new LinkedMultiValueMap<String, String>();
        String result = userService.sendScse(url, method, params);
        if (result.contains("/sise/index.jsp")) {
            return userService.updateStudentInfo(student.getUserId(), student.getId(), student.getPassword()) == 1? JSONResult.ok("认证成功"):JSONResult.errorMsg("更新信息失败");
        } else if (result.contains("密码错误")) {
            return JSONResult.errorMap("密码错误");
        } else if (result.contains("系统数据库中找不到你的数据")) {
            return JSONResult.errorMap("该学号不存在");
        } else {
            return JSONResult.errorMsg("未知错误");
        }
    }

    @ApiOperation(value = "短信验证码服务")
    @GetMapping(value = "/phone/{phone}")
    public JSONResult smsXxs(@PathVariable("phone") String phone) throws Exception {
        log.info("==============手机【" + phone + "】获取验证码==============");
        // 验证码（指定长度的随机数）
        String code = CodeUtil.generateVerifyCode(6);
        String templateParam = "{\"code\":\""+code+"\"}";
        SendSmsResponse response = SmsUtil.sendSms(phone, templateParam);
        if(response.getCode().equals("OK")) {
            redisTemplate.opsForValue().set(phone + "-phone-code", code);
            return JSONResult.ok(code);
        } else {
            return JSONResult.errorMsg("发送短信失败");
        }
    }

    @ApiOperation(value = "邮件验证码服务")
    @GetMapping(value = "/mail/{mail}")
    public JSONResult sendMail(@PathVariable(value = "mail") String recipient) {
        log.info("==============邮件【" + recipient + "】获取验证码==============");
        Mail mail = new Mail();
        mail.setRecipient(recipient);
        mail.setTheme("园猿二手平台");
        String code = CodeUtil.generateVerifyCode(6);
        mail.setContent("尊敬的用户，您的注册码："+ code + "，工作人员不会索取，请勿泄露！");
        try{
            userService.sendSimpleMail(mail);
            redisTemplate.opsForValue().set(recipient + "-mail-code", code);
            return JSONResult.ok(code);
        } catch (Exception e) {
            log.error(e.getMessage());
            return JSONResult.errorMap("发送邮件失败");
        }
    }

    @ApiOperation(value = "根据管理员id查询用户信息")
    @GetMapping(value = "/findAllByAdminId/{admin_id}")
    public JsonUtil findAllByAdminId(@PathVariable("admin_id") int adminId) {
        log.info("==============查询管理员id【" + adminId + "】的所有用户信息==============");
        List<User> users = userService.findAllByAdminId(adminId);
        for (User user : users) {
            user.setProductList(productService.findProductByUserId(user.getUserId()));
        }
        return JsonUtil.ok(users.size(), users);
    }

    @ApiOperation(value = "根据用户id修改用户状态并记录管理员日志")
    @PostMapping(value = "/updateUserStatus/{admin_id}/{user_id}/{user_status}")
    public JSONResult updateUserStatus(@PathVariable("admin_id") int adminId, @PathVariable("user_id") int userId,
                                       @PathVariable("user_status") String userStatus, @RequestParam("log_content") String logContent,
                                       @RequestParam("reason") String reason) {
        log.info("==============管理员【" + adminId + "】根据用户id【" + userId + "】修改用户状态【" + userStatus + "】==============");
        int i = userService.updateUserStatusByUserId(userId, userStatus);
        if (i == 1) {
            AdminLog adminLog = new AdminLog();
            adminLog.setAdminId(adminId);
            adminLog.setLogContent(logContent);
            adminLog.setReason(reason);
            adminLog.setUserId(userId);
            return adminLogService.insertAdminLog(adminLog) == 1? JSONResult.ok("更新成功"):JSONResult.errorMsg("插入日志失败");
        }
        return JSONResult.errorMsg("更新失败");
    }

    @ApiOperation(value = "查询管理员")
    @GetMapping(value = "/findAdmin/{admin_id}")
    public JSONResult findAdmin(@PathVariable("admin_id") int adminId) {
        log.info("==============查询管理员【" + adminId + "】==============");
        return JSONResult.ok(userService.findAmdinByAdminId(adminId));
    }

    @ApiOperation(value = "测试接口")
    @GetMapping(value = "/seller")
    public JSONResult ceshi() {
        log.info((String) redisTemplate.opsForValue().get("1093944886@qq.com-mail-code"));


        return JSONResult.ok("您拥有seller权限");
    }

}
