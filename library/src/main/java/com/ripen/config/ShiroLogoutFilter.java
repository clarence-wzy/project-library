package com.ripen.config;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.subject.PrincipalCollection;
import org.apache.shiro.subject.Subject;
import org.apache.shiro.web.filter.authc.LogoutFilter;
import org.apache.shiro.web.mgt.DefaultWebSecurityManager;

import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;

/**
 * Shiro 登出过滤器
 *
 * @author Ripen.Y
 * @version 2021/01/09 23:17
 * @since 2021/01/09
 */
public class ShiroLogoutFilter extends LogoutFilter {

    @Override
    protected boolean preHandle(ServletRequest request, ServletResponse response) throws Exception {
        //登出操作 清除缓存  subject.logout() 可以自动清理缓存信息
        Subject subject = getSubject(request,response);
        DefaultWebSecurityManager securityManager = (DefaultWebSecurityManager) SecurityUtils.getSecurityManager();
        ShiroUserRealm shiroUserRealm = (ShiroUserRealm) securityManager.getRealms().iterator().next();
        PrincipalCollection principals = subject.getPrincipals();
        shiroUserRealm.clearCache(principals);

        subject.logout();

        //获取登出后重定向到的地址
        String redirectUrl = getRedirectUrl(request,response,subject);
        //重定向
        issueRedirect(request,response,redirectUrl);
        return false;
    }

}

