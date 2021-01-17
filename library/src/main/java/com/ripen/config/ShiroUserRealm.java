package com.ripen.config;

import com.ripen.dao.entity.SysRolePerm;
import com.ripen.dao.entity.SysUser;
import com.ripen.dao.mapper.SysRolePermMapper;
import com.ripen.dao.mapper.SysUserMapper;
import com.ripen.util.ShiroUtil;
import com.ripen.util.ThreeDes;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.*;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.apache.shiro.subject.Subject;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

/**
 * Shiro 用户认证授权
 *
 * @author Ripen.Y
 * @version 2021/01/09 23:17
 * @since 2021/01/09
 */
public class ShiroUserRealm extends AuthorizingRealm {

    @Autowired
    private SysRolePermMapper rolePermMapper;
    @Autowired
    private SysUserMapper userMapper;

    /**
     * 授权
     */
    @Override
    protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principalCollection)
    {
        SimpleAuthorizationInfo info = new SimpleAuthorizationInfo();
        Subject subject = SecurityUtils.getSubject();
        SysUser user = (SysUser) subject.getPrincipal();
        if (user == null || user.getAccount() == null) {
            return null;
        }

        List<SysRolePerm> rolePermList = rolePermMapper.getRolePerm(user.getAccount(), 0);
        for (SysRolePerm rolePerm : rolePermList) {
            info.addStringPermission(rolePerm.getPermName());
        }

        return info;
    }

    /**
     * 认证
     */
    @Override
    protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken authenticationToken) throws AuthenticationException
    {
        // 判断用户名
        UsernamePasswordToken token = (UsernamePasswordToken) authenticationToken;
        SysUser tempSysUser = new SysUser();
        tempSysUser.setAccount(token.getUsername());
        List<SysUser> userList = userMapper.getUserWithCondition(tempSysUser, null);
        if (userList == null || userList.size() != 1) {
            // 用户名不存在，shiro底层会抛出 UnKnowAccountException 异常
            return null;
        }

        SysUser user = userList.get(0);
        SimpleAuthenticationInfo simpleAuthenticationInfo = null;
        // 判断密码
        try {
            simpleAuthenticationInfo = new SimpleAuthenticationInfo(user, ThreeDes.decryptThreeDESECB(user.getPwd()), "");
            // 验证成功开始踢人(清除缓存和Session)，即同一个用户只能登陆一个
            ShiroUtil.deleteCache(token.getUsername(), true);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return simpleAuthenticationInfo;
    }

    @Override
    public void clearCache(PrincipalCollection principals) {
        super.clearCache(principals);
    }

}
