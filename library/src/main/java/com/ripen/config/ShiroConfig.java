package com.ripen.config;

import org.apache.shiro.session.mgt.SessionManager;
import org.apache.shiro.spring.web.ShiroFilterFactoryBean;
import org.apache.shiro.web.mgt.DefaultWebSecurityManager;
import org.crazycake.shiro.RedisCacheManager;
import org.crazycake.shiro.RedisManager;
import org.crazycake.shiro.RedisSessionDAO;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.servlet.Filter;
import java.util.LinkedHashMap;
import java.util.Map;

/**
 * 管理员控制层
 *
 * @author Ripen.Y
 * @version 2021/01/09 23:17
 * @since 2021/01/09
 */
@Configuration
public class ShiroConfig {

    private final String CACHE_KEY = "shiro:cache:";
    private final String SESSION_KEY = "shiro:session:";
    private final int EXPIRE = 86400;

    /** Redis配置 **/
    @Value("${spring.redis.host}")
    private String host;
    @Value("${spring.redis.port}")
    private int port;
    @Value("${spring.redis.timeout}")
    private int timeout;

    /**
     * 创建ShiroFilterFactoryBean
     */
    @Bean
    public ShiroFilterFactoryBean getShiroFilterFactoryBean(@Qualifier("securityManager") DefaultWebSecurityManager defaultWebSecurityManager) {
        ShiroFilterFactoryBean shiroFilterFactoryBean = new ShiroFilterFactoryBean();
        // 设置安全管理器
        shiroFilterFactoryBean.setSecurityManager(defaultWebSecurityManager);
        // 添加内置过滤器
        Map<String, String> filerMap = new LinkedHashMap<>();
        // 放行swagger2
        filerMap.put("/swagger-ui.html", "anon");
        filerMap.put("/swagger-resources", "anon");
        filerMap.put("/swagger-resources/**", "anon");
        filerMap.put("/v2/api-docs", "anon");
        filerMap.put("/webjars/springfox-swagger-ui/**", "anon");
        // 放行登录与注册接口
        filerMap.put("/login", "anon");
        filerMap.put("/admin/login", "anon");
        filerMap.put("/user/add", "anon");
        // 添加页面需要的权限
//        filerMap.put("/user/get", "perms[SE_BK]");
//        filerMap.put("/admin/get", "perms[aaa]");
        // 全部拦截
//        filerMap.put("/**", "authc");
        shiroFilterFactoryBean.setFilterChainDefinitionMap(filerMap);

        LinkedHashMap<String, Filter> filtersMap = new LinkedHashMap<>();
        //  自定义logout方法
        filtersMap.put("/logout", shiroLogoutFilter());
        shiroFilterFactoryBean.setFilters(filtersMap);

        // 修改调整的登录页面
        shiroFilterFactoryBean.setLoginUrl("/toLogin");
        // 设置未授权提示页面
        shiroFilterFactoryBean.setUnauthorizedUrl("/noAuth");

        return shiroFilterFactoryBean;
    }

    /**
     * 创建DefaultWebSecurityManager
     */
    @Bean(name = "securityManager")
    public DefaultWebSecurityManager defaultWebSecurityManager(
            @Qualifier("sessionManager") SessionManager sessionManager,
            @Qualifier("redisCacheManger") RedisCacheManager redisCacheManger,
            @Qualifier("shiroUserRealm") ShiroUserRealm shiroUserRealm) {
        DefaultWebSecurityManager defaultWebSecurityManager = new DefaultWebSecurityManager();
        defaultWebSecurityManager.setSessionManager(sessionManager);
        defaultWebSecurityManager.setCacheManager(redisCacheManger);
        defaultWebSecurityManager.setRealm(shiroUserRealm);
        return defaultWebSecurityManager;
    }

    /**
     * 创建Realm
     */
    @Bean(name = "shiroUserRealm")
    public ShiroUserRealm getRealm() {
        return new ShiroUserRealm();
    }

    /**
     * 配置redis管理器
     * @return
     */
    @Bean(name = "redisManager")
    public RedisManager redisManager() {
        RedisManager redisManager = new RedisManager();
        redisManager.setHost(host);
        redisManager.setPort(port);
        redisManager.setTimeout(timeout);
        return redisManager;
    }

    /**
     * 配置cache管理器
     * @param redisManager
     * @return
     */
    @Bean(name = "redisCacheManger")
    public RedisCacheManager cacheManager(@Qualifier("redisManager") RedisManager redisManager) {
        RedisCacheManager redisCacheManager = new RedisCacheManager();
        redisCacheManager.setRedisManager(redisManager);
        // 设置redis 存储cache 文件路径（默认 shiro:cache）
        redisCacheManager.setKeyPrefix(CACHE_KEY);
        // 配置缓存的话要求放在session里面的实体类必须有个id标识
        redisCacheManager.setPrincipalIdFieldName("account");
        return redisCacheManager;
    }

    /**
     * sessionId生成器
     * @return
     */
    @Bean(name = "sessionIdGenerator")
    public ShiroSessionIdGenerator sessionIdGenerator() {
        return new ShiroSessionIdGenerator();
    }

    /**
     * 配置RedisSessionDAO
     * @param redisManager
     * @param sessionIdGenerator
     * @return
     */
    @Bean(name = "redisSessionDao")
    public RedisSessionDAO redisSessionDAO(
            @Qualifier("redisManager") RedisManager redisManager,
            @Qualifier("sessionIdGenerator") ShiroSessionIdGenerator sessionIdGenerator) {
        RedisSessionDAO redisSessionDAO = new RedisSessionDAO();
        redisSessionDAO.setRedisManager(redisManager);
        // session 序列化
        redisSessionDAO.setSessionIdGenerator(sessionIdGenerator);
        // 设置redis 存储session 文件路径（默认 shiro:session）
        redisSessionDAO.setKeyPrefix(SESSION_KEY);
        redisSessionDAO.setExpire(EXPIRE);
        return redisSessionDAO;
    }

    @Bean(name = "sessionManager")
    public SessionManager sessionManager(@Qualifier("redisSessionDao") RedisSessionDAO redisSessionDAO) {
        ShiroSessionManager shiroSessionManager = new ShiroSessionManager();
        shiroSessionManager.setSessionDAO(redisSessionDAO);
        return shiroSessionManager;
    }

    public ShiroLogoutFilter shiroLogoutFilter(){
        ShiroLogoutFilter shiroLogoutFilter = new ShiroLogoutFilter();
        // 配置登出后重定向的地址，等出后配置跳转到登录接口
        shiroLogoutFilter.setRedirectUrl("/login");
        return shiroLogoutFilter;
    }

}
