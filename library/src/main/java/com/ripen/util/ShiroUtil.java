package com.ripen.util;

import com.ripen.dao.entity.SysUser;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.Authenticator;
import org.apache.shiro.authc.LogoutAware;
import org.apache.shiro.session.Session;
import org.apache.shiro.subject.SimplePrincipalCollection;
import org.apache.shiro.subject.support.DefaultSubjectContext;
import org.apache.shiro.web.mgt.DefaultWebSecurityManager;
import org.crazycake.shiro.RedisSessionDAO;

import java.util.Collection;
import java.util.Objects;

/**
 * Shiro 工具类
 *
 * @author Ripen.Y
 * @version 2021/01/09 23:17
 * @since 2021/01/09
 */
public class ShiroUtil {

	/** 私有构造器 **/
	private ShiroUtil(){ }

    private static RedisSessionDAO redisSessionDAO = ShiroSpringUtil.getBean(RedisSessionDAO.class);

    /**
     * 获取当前用户Session
     *
     * @return
     */
    public static Session getSession() {
        return SecurityUtils.getSubject().getSession();
    }

    /**
     * 用户登出
     */
    public static void logout() {
        SecurityUtils.getSubject().logout();
    }

	/**
	 * 获取当前用户信息
     *
     * @return
	 */
	public static SysUser getUserInfo() {
		return (SysUser) SecurityUtils.getSubject().getPrincipal();
	}

    /**
     * 删除用户缓存信息
     *
     * @param account
     * @param isRemoveSession
     */
    public static void deleteCache (String account, boolean isRemoveSession)
    {
        Session session = null;
        Collection<Session> sessions = redisSessionDAO.getActiveSessions();
        SysUser user;
        Object attribute = null;
        for (Session sessionInfo : sessions) {
            // 遍历Session，找到该用户名称对应的Session
            attribute = sessionInfo.getAttribute(DefaultSubjectContext.PRINCIPALS_SESSION_KEY);
            if (attribute == null) {
                continue;
            }
            user = (SysUser) ((SimplePrincipalCollection) attribute).getPrimaryPrincipal();
            if (user == null) {
                continue;
            }
            if (Objects.equals(user.getAccount(), account)) {
                session=sessionInfo;
            }
        }
        if (session == null || attribute == null) {
            return;
        }
        // 删除session
        if (isRemoveSession) {
            redisSessionDAO.delete(session);
        }
        // 删除Cache，在访问受限接口时会重新授权
        DefaultWebSecurityManager securityManager = (DefaultWebSecurityManager) SecurityUtils.getSecurityManager();
        Authenticator authenticator = securityManager.getAuthenticator();
        ((LogoutAware) authenticator).onLogout((SimplePrincipalCollection) attribute);
    }

}