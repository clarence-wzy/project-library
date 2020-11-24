package com.wzz.log.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * <p>Title: SysControllerLog</p>
 * <p>Description: </p>
 *
 * @author lizihao
 * @version 1.0.0
 * @date 2019/8/27 17:31
 */
@Target({ElementType.PARAMETER, ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
public @interface ControllerLog {
    /**
     * 描述业务操作 例：XXX管理-执行XXX操作
     *
     * @return
     */
    String description() default "";
}
