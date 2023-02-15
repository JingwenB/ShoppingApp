package com.example.transactionmanagementdemo.AOP;

import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;

@Aspect
public class PointCuts {


    // user create order
    @Pointcut("execution(* com.example.transactionmanagementdemo.service.OrderService.createOrder(..))")
    public void inCreateOrder(){}

    // admin complete/finish order
    @Pointcut("execution(* com.example.transactionmanagementdemo.controller.AdminOrderController.*(..))")
    public void inAdminUpdateOrder(){}


}
