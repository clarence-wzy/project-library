package com.ripen.util;

import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Component;

/**
 * Shiro spring工具类
 *
 * @author Ripen.Y
 * @version 2021/01/09 23:17
 * @since 2021/01/09
 */
@Component
public class ShiroSpringUtil implements ApplicationContextAware {

    private static ApplicationContext context;

    /**
     * Spring在bean初始化后会判断是不是ApplicationContextAware的子类
     * 如果该类是，setApplicationContext()方法，会将容器中ApplicationContext作为参数传入进去
     *
     * @param applicationContext
     * @throws BeansException
     */
    @Override
    public void setApplicationContext (ApplicationContext applicationContext) throws BeansException
    {
        context = applicationContext;
    }

    /**
     * 通过Name返回指定的Bean
     *
     * @param beanClass
     * @param <T>
     * @return
     */
    public static <T> T getBean(Class<T> beanClass)
    {
        return context.getBean(beanClass);
    }

}