package com.miku.minadevelop.common.aspect;

import com.alibaba.fastjson2.JSON;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.stereotype.Component;
import org.springframework.util.StopWatch;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;

@Aspect
@Component
@Slf4j
public class RequestLogAspect {

    @Pointcut("execution(public * com.miku.minadevelop.modules.controller..*.*(..))")
    public void requestLog() {

    }

    @Before("requestLog()")
    public void requestLog(JoinPoint joinPoint) {
        ServletRequestAttributes attributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        HttpServletRequest request = attributes.getRequest();
        log.info("========================================== Start ==========================================");
        log.info("请求URL：{}", request.getRequestURL());
        log.info("请求方法类型：{}", request.getMethod());
        log.info("请求IP：{}", request.getRemoteAddr());
        log.info("请求入参：{}", JSON.toJSONString(joinPoint.getArgs()));
    }

    @Around("requestLog()")
    public Object doAround(ProceedingJoinPoint proceedingJoinPoint) throws Throwable {
        StopWatch stopWatch = new StopWatch();
        stopWatch.start();
        Object result = proceedingJoinPoint.proceed();
        stopWatch.stop();
        log.info("响应结果：{}", JSON.toJSONString(result));
        log.info("响应耗时：{}ms", stopWatch.getTotalTimeMillis());
        log.info("=========================================== End ===========================================");
        return result;
    }


}