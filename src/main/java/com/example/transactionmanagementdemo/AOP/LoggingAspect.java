package com.example.transactionmanagementdemo.AOP;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.sql.Timestamp;


@Aspect
@Component
public class LoggingAspect {

    private final String POINTCUTS_PATH = "com.example.transactionmanagementdemo.AOP.PointCuts";
    private Logger logger = LoggerFactory.getLogger(LoggingAspect.class);


    @Before("com.example.transactionmanagementdemo.AOP.PointCuts.inAdminUpdateOrder()")
    public void logOrderUpdateTime(JoinPoint joinPoint){
        logger.info("\nLog Order Update Time: " + new Timestamp(System.currentTimeMillis()));
    }

    @Before("com.example.transactionmanagementdemo.AOP.PointCuts.inCreateOrder()")
    public void logOrderCreateTime(JoinPoint joinPoint){
        logger.info("\nLog Order Create Time: " + new Timestamp(System.currentTimeMillis()));
    }
}
