package com.example.shoppingApp.AOP;

import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.sql.Timestamp;


@Aspect
@Component
@Slf4j
public class LoggingAspect {

    private Logger logger = LoggerFactory.getLogger(LoggingAspect.class);

    @Before("com.example.shoppingApp.AOP.PointCuts.inAdminUpdateOrder()")
    public void logOrderUpdateTime(JoinPoint joinPoint){
        logger.info("\nLog Order Update Time: " + new Timestamp(System.currentTimeMillis()));
    }

    @Before("com.example.shoppingApp.AOP.PointCuts.inCreateOrder()")
    public void logOrderCreateTime(JoinPoint joinPoint){
        logger.info("\nLog Order Create Time: " + new Timestamp(System.currentTimeMillis()));
    }

    @Around("execution(* com.example.shoppingApp.controller.*.*(..))")
    public Object logMethodExecutionTime(ProceedingJoinPoint joinPoint) throws Throwable {
        String className = joinPoint.getTarget().getClass().getSimpleName();
        String methodName = joinPoint.getSignature().getName();

        long startTime = System.currentTimeMillis();
        Object result = joinPoint.proceed();
        long endTime = System.currentTimeMillis();

        log.info("Method " + className + "." + methodName + " executed in " + (endTime - startTime) + "ms");

        return result;
    }

}
