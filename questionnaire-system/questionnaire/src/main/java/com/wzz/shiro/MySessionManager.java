package com.wzz.shiro;

import org.apache.shiro.web.session.mgt.DefaultWebSessionManager;
import org.apache.shiro.web.servlet.ShiroHttpServletRequest;
import org.apache.shiro.web.util.WebUtils;
import org.springframework.util.StringUtils;

import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import java.io.Serializable;

/**
 * @author Clarence1
 */
public class MySessionManager extends DefaultWebSessionManager {

    /**
     * 前端请求头传这个
     */
    private static final String AUTHORIZATION = "Authorization";
    private static final String REFERENCED_SESSION_ID_SOURCE = "Stateless request";

    public MySessionManager() {
        super();
        //  设置session有限时间：30分钟*48=1天
        this.setGlobalSessionTimeout(DEFAULT_GLOBAL_SESSION_TIMEOUT * 48);
        //  设置当session发现失效时自动删除
        this.setDeleteInvalidSessions(true);
    }

    @Override
    protected Serializable getSessionId(ServletRequest request, ServletResponse response) {
        String id = WebUtils.toHttp(request).getHeader(AUTHORIZATION);
        //如果请求头中有 Authorization 则其值为sessionId
        if (!StringUtils.isEmpty(id)) {
            request.setAttribute(ShiroHttpServletRequest.REFERENCED_SESSION_ID_SOURCE, REFERENCED_SESSION_ID_SOURCE);
            request.setAttribute(ShiroHttpServletRequest.REFERENCED_SESSION_ID, id);
            request.setAttribute(ShiroHttpServletRequest.REFERENCED_SESSION_ID_IS_VALID, Boolean.TRUE);
            return id;
        } else {
            //否则按默认规则从cookie取sessionId
            return super.getSessionId(request, response);
        }
    }

}
