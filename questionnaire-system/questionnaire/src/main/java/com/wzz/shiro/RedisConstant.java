package com.wzz.shiro;

/**
 * Redis常量类
 * @author Clarence1
 */
public interface RedisConstant {
    /**
     * TOKEN前缀
     */
//    String REDIS_PREFIX_LOGIN = "login_token_%s";
    String REDIS_PREFIX_LOGIN = "%s";
    /**
     * 过期时间2小时
     */
    Integer REDIS_EXPIRE_TWO = 7200;
    /**
     * 过期时间15分
     */
    Integer REDIS_EXPIRE_EMAIL = 900;
    /**
     * 过期时间5分钟
     */
    Integer REDIS_EXPIRE_KAPTCHA = 300;
    /**
     * 暂无过期时间
     */
    Integer REDIS_EXPIRE_NULL = -1;
}
