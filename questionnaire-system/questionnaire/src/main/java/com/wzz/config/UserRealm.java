package com.wzz.config;

import com.wzz.entity.Permission;
import com.wzz.entity.RolePermission;
import com.wzz.entity.User;
import com.wzz.service.UserService;
import com.wzz.util.ShiroUtils;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.*;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.apache.shiro.subject.Subject;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * @author Clarence1
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

        User dbUser = userService.getUserInfoByName(user.getUsername());
        RolePermission rolePermission = userService.getPermIdByRoleId(dbUser.getRoleId());
        Permission permission = userService.getPermDetailByPermId(rolePermission.getPermId());
        //为用户添加权限
        info.addStringPermission(permission.getPermDetail());

        return info;
    }

    /**
     * 执行认证逻辑
     */
    @Override
    protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken authenticationToken) throws
    AuthenticationException {
        ThreeDES threeDES = new ThreeDES();

        //判断用户名
        UsernamePasswordToken token = (UsernamePasswordToken) authenticationToken;
        User user = userService.getUserInfoByName(token.getUsername());
        if (user == null) {
            //用户名不存在
            //shiro底层会抛出UnKnowAccountException
            return null;
        }

        //判断密码
        try {
            SimpleAuthenticationInfo simpleAuthenticationInfo = new SimpleAuthenticationInfo(user, threeDES.decryptThreeDESECB(user.getPassword(), key), "");
            //验证成功开始踢人(清除缓存和Session)
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
