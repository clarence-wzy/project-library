package com.ripen.config;

import org.apache.shiro.session.Session;
import org.apache.shiro.session.mgt.eis.JavaUuidSessionIdGenerator;
import org.apache.shiro.session.mgt.eis.SessionIdGenerator;

import java.io.Serializable;

/**
 * Shiro session_id 生成器
 *
 * @author Ripen.Y
 * @version 2021/01/09 23:17
 * @since 2021/01/09
 */
public class ShiroSessionIdGenerator implements SessionIdGenerator {

    @Override
    public Serializable generateId(Session session) {
        Serializable sessionId = new JavaUuidSessionIdGenerator().generateId(session);
        return String.format("%s", sessionId);
    }

}
