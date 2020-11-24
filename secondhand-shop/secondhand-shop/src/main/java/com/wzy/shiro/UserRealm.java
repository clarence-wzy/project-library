package com.wzy.shiro;

import com.wzy.config.ThreeDes;
import com.wzy.entity.RolePermission;
import com.wzy.entity.User;
import com.wzy.service.UserService;
import com.wzy.util.ShiroUtils;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.*;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.Permission;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.apache.shiro.subject.Subject;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

/**
 * @Package: com.wzy.shiro
 * @Author: Clarence1
 * @Date: 2019/11/26 20:24
 */
public class UserRealm extends AuthorizingRealm {

    final String key = "cf410f84904a44cc8a7f48fc4134e8f9";

    @Autowired
    private UserService userService;

    /**
     * 执行授权逻辑
     */
    @Override
    protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principalCollection) {
        SimpleAuthorizationInfo info = new SimpleAuthorizationInfo();

        //获取当前登录用户
        Subject subject = SecurityUtils.getSubject();
        User user = (User) subject.getPrincipal();

        List<User> dbUser = userService.findUserByUsernameWithRole(user.getUsername());
        List<RolePermission> rolePermissions = userService.getRolePermission(dbUser.get(0).getRoleName());
        //为用户添加权限
        for (RolePermission rolePermission : rolePermissions) {
            info.addStringPermission(rolePermission.getPermName());
        }

        return info;
    }

    /**
     * 执行认证逻辑
     */
    @Override
    protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken authenticationToken) throws AuthenticationException {
        ThreeDes threeDES = new ThreeDes();

        //判断用户名
        UsernamePasswordToken token = (UsernamePasswordToken) authenticationToken;
        User user = userService.findUserByUsername(token.getUsername());
        if (user == null) {
            //用户名不存在，shiro底层会抛出 UnKnowAccountException 异常
            return null;
        }

        //判断密码
        try {
            SimpleAuthenticationInfo simpleAuthenticationInfo = new SimpleAuthenticationInfo(user, threeDES.decryptThreeDESECB(user.getPassword(), key), "");
            //验证成功开始踢人(清除缓存和Session)，即同一个用户只能登陆一个
            ShiroUtils.deleteCache(token.getUsername(),true);
            return simpleAuthenticationInfo;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    @Override
    public void clearCache(PrincipalCollection principals) {
        super.clearCache(principals);
    }

}
