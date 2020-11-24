package com.wzy.shiro;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.subject.PrincipalCollection;
import org.apache.shiro.subject.Subject;
import org.apache.shiro.web.filter.authc.LogoutFilter;
import org.apache.shiro.web.mgt.DefaultWebSecurityManager;

import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;

/**
 * @Package: com.wzy.shiro
 * @Author: Clarence1
 * @Date: 2019/11/26 20:24
 */
public class ShiroLogoutFilter extends LogoutFilter {

    @Override
    protected boolean preHandle(ServletRequest request, ServletResponse response) throws Exception {
        //登出操作 清除缓存  subject.logout() 可以自动清理缓存信息
        Subject subject = getSubject(request,response);
        DefaultWebSecurityManager securityManager = (DefaultWebSecurityManager) SecurityUtils.getSecurityManager();
        UserRealm shiroRealm = (UserRealm) securityManager.getRealms().iterator().next();
        PrincipalCollection principals = subject.getPrincipals();
        shiroRealm.clearCache(principals);

        subject.logout();

        //获取登出后重定向到的地址
        String redirectUrl = getRedirectUrl(request,response,subject);
        //重定向
        issueRedirect(request,response,redirectUrl);
        return false;
    }

}

