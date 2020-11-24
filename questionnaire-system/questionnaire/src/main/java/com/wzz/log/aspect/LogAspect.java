package com.wzz.log.aspect;

import com.wzz.entity.User;
import com.wzz.log.annotation.ControllerLog;
import com.wzz.log.annotation.ServiceLog;
import com.wzz.log.entity.Log;
import com.wzz.log.service.LogService;
import com.wzz.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.NamedThreadLocal;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;
import org.springframework.stereotype.Component;


import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import java.lang.reflect.Method;
import java.text.SimpleDateFormat;
import java.util.Date;

import java.util.UUID;


/**
 * <p>Title: LogAspect</p>
 * <p>Description:日志切面类，定义切面，存储操作记录 </p>
 *
 * @author lizihao
 * @version 1.0.0
 * @date 2019/8/27 18:37
 */
@Aspect
@Component
@Slf4j
public class LogAspect {

    private static Logger logger = LoggerFactory.getLogger(LogAspect.class);

    private static final ThreadLocal<Date> BEGIN_TIME_THREAD_LOCAL = new NamedThreadLocal<Date>("ThreadLocal beginTime");

    private static final ThreadLocal<Log> LOG_THREAD_LOCAL = new NamedThreadLocal<Log>("ThreadLocal log");

    private static final ThreadLocal<User> CURRENT = new NamedThreadLocal<>("ThreadLocal user");

    @Autowired(required = false)
    HttpServletRequest request;

    @Autowired
    LogService logService;

    /**
     * 模拟用户已登录
     */
    @Autowired
    UserService userService;

//    @Autowired
//    RedisUtil redisUtil;

    /**
     * Service层切面
     */
    @Pointcut("@annotation(com.wzz.log.annotation.ServiceLog)")
    public void serviceAspect() {
    }

    /**
     * Controller层切面
     */
    @Pointcut("@annotation(com.wzz.log.annotation.ControllerLog)")
    public void controllerAspect() {
    }

    /**
     * 前置通知 用于拦截Controller层记录用户的操作开始时间
     *
     * @param joinPoint 切点
     * @throws InterruptedException 异常
     */
    @Before("controllerAspect()")
    public void doBefore(JoinPoint joinPoint) throws InterruptedException {
        logger.info("进入日志切面前置通知！");
        Date beginTime = new Date();
        //线程绑定变量（该数据只有当前请求的线程可见）
        BEGIN_TIME_THREAD_LOCAL.set(beginTime);
        //日志级别为debug
        if (logger.isDebugEnabled()) {
            logger.debug("开始计时：{} URI:{}", new SimpleDateFormat("yyyy-MM-dd HH:mm:ss")
                    .format(beginTime), request.getRequestURI());
        }

        //读取用户信息
        HttpSession session = request.getSession();
        User user = (User) session.getAttribute("user");


//        User user = userService.getUserInfoByName("admin");
        CURRENT.set(user);
    }

    /**
     * 后置通知 用于拦截Controller层，记录用户的操作
     *
     * @param joinPoint 切点
     */
    @After("controllerAspect()")
    public void doAfter(JoinPoint joinPoint) {
        logger.info("进入日志切面后置通知！");
        User user = CURRENT.get();
        if (user != null) {
            String title = "";
            String type = "info";
            //请求的IP
            String remoteAddr = request.getRemoteAddr();
            //请求的URI
            String requestUri = request.getRequestURI();
            //请求的方法类型
            String method = request.getMethod();

            try {
                title = getControllerMethodDescription2(joinPoint);
            } catch (Exception e) {
                e.printStackTrace();
            }

            //打印信息
            //得到线程绑定的局部变量（开始时间）
            long beginTime = BEGIN_TIME_THREAD_LOCAL.get().getTime();
            //结束时间
            long endTime = System.currentTimeMillis();
            if (logger.isDebugEnabled()) {
                logger.debug("计时结束：{} URI：{} 耗时：{} 最大内存：{}m 已分配内存：{}m 已分配内存中的剩余空间：{}m 最大可用内存：{}m",
                        new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(endTime),
                        request.getRequestURI(),
                        (endTime - beginTime),
                        Runtime.getRuntime().maxMemory() / 1024 / 1024,
                        Runtime.getRuntime().totalMemory() / 1024 / 1024,
                        Runtime.getRuntime().freeMemory() / 1024 / 1024,
                        (Runtime.getRuntime().maxMemory() - Runtime.getRuntime().totalMemory() + Runtime.getRuntime().freeMemory()) / 1024 / 1024);
            }
            logger.info("设置日志信息存储到表中！");
            Log log = new Log();
            log.setLogId(UUID.randomUUID().toString());
            log.setTitle(title);
            log.setType(type);
            log.setRemoteAddr(remoteAddr);
            log.setRequestUri(requestUri);
            log.setMethod(method);
            log.setException("无异常！");
            log.setUserId(user.getUserId());
            Date operateDate = BEGIN_TIME_THREAD_LOCAL.get();
            log.setOperateDate(operateDate);
            log.setTimeout(String.valueOf(endTime - beginTime));

            //保存日志
            ThreadPoolTaskExecutor threadPoolExecutor = new ThreadPoolTaskExecutor();
            //初始化线程池
            threadPoolExecutor.initialize();
            threadPoolExecutor.execute(new SaveLogThread(log, logService));

        }

    }

    /**
     * 用于拦截Service层，记录用户操作
     *
     * @param joinPoint 切点
     */
    @After("serviceAspect()")
    public void doAfterService(JoinPoint joinPoint) {
        logger.info("进入service层后置通知");
        User user = CURRENT.get();
        if (user != null) {
            String title = "";
            String type = "info";
            //请求的IP
            String remoteAddr = request.getRemoteAddr();
            //请求的URI
            String requestUri = request.getRequestURI();
            //请求的方法类型
            String method = request.getMethod();

            try {
                title = getServiceMethodDescription2(joinPoint);
            } catch (Exception e) {
                e.printStackTrace();
            }

            //打印信息
            //得到线程绑定的局部变量（开始时间）
            long beginTime = BEGIN_TIME_THREAD_LOCAL.get().getTime();
            //结束时间
            long endTime = System.currentTimeMillis();
            if (logger.isDebugEnabled()) {
                logger.debug("计时结束：{} URI：{} 耗时：{} 最大内存：{}m 已分配内存：{}m 已分配内存中的剩余空间：{}m 最大可用内存：{}m",
                        new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(endTime),
                        request.getRequestURI(),
                        (endTime - beginTime),
                        Runtime.getRuntime().maxMemory() / 1024 / 1024,
                        Runtime.getRuntime().totalMemory() / 1024 / 1024,
                        Runtime.getRuntime().freeMemory() / 1024 / 1024,
                        (Runtime.getRuntime().maxMemory() - Runtime.getRuntime().totalMemory() + Runtime.getRuntime().freeMemory()) / 1024 / 1024);
            }
            logger.info("设置日志信息存储到表中！");
            Log log = new Log();
            log.setLogId(UUID.randomUUID().toString());
            log.setTitle(title);
            log.setType(type);
            log.setRemoteAddr(remoteAddr);
            log.setRequestUri(requestUri);
            log.setMethod(method);
            log.setException("无异常！");
            log.setUserId(user.getUserId());
            Date operateDate = BEGIN_TIME_THREAD_LOCAL.get();
            log.setOperateDate(operateDate);
            log.setTimeout(String.valueOf(endTime - beginTime));

            //保存日志
            ThreadPoolTaskExecutor threadPoolExecutor = new ThreadPoolTaskExecutor();
            //初始化线程池
            threadPoolExecutor.initialize();
            threadPoolExecutor.execute(new SaveLogThread(log, logService));
        }

        //加快内存回收
//        BEGIN_TIME_THREAD_LOCAL.remove();
//        CURRENT.remove();
//        LOG_THREAD_LOCAL.remove();
    }


    /**
     * 获取注解中对方法的描述信息 用于Service层注解
     *
     * @param joinPoint 切点
     * @return description
     */
    private static String getServiceMethodDescription2(JoinPoint joinPoint) {
        MethodSignature signature = (MethodSignature) joinPoint.getSignature();
        Method method = signature.getMethod();
        ServiceLog serviceLog = method.getAnnotation(ServiceLog.class);
        return serviceLog.description();
    }

    /**
     * 获取注解中对方法的描述信息 用于Controller层注解
     *
     * @param joinPoint 切点
     * @return description
     */
    private static String getControllerMethodDescription2(JoinPoint joinPoint) {
        MethodSignature signature = (MethodSignature) joinPoint.getSignature();
        Method method = signature.getMethod();
        ControllerLog controllerLog = method.getAnnotation(ControllerLog.class);
        return controllerLog.description();
    }

    /**
     * 保存日志线程
     */
    private static class SaveLogThread implements Runnable {
        private Log log;
        private LogService logService;

        SaveLogThread(Log log, LogService logService) {
            this.log = log;
            this.logService = logService;
        }

        @Override
        public void run() {
            logService.addLog(log);
        }
    }

//    /**
//     * 日志更新线程
//     */
//    private static class UpdateLogThread extends Thread {
//        private Log log;
//        private LogService logService;
//
//        public UpdateLogThread(Log log, LogService logService) {
//            super(UpdateLogThread.class.getSimpleName());
//            this.log = log;
//            this.logService = logService;
//        }
//
//        @Override
//        public void run() {
//            this.logService.putLog(log);
//        }
//    }
}
