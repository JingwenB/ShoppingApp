package com.example.shoppingApp.AOP;

import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;

@Aspect
public class PointCuts {


    // user create order
    @Pointcut("execution(* com.example.shoppingApp.service.OrderService.createOrder(..))")
    public void inCreateOrder(){}

    // admin complete/finish order
    @Pointcut("execution(* com.example.shoppingApp.controller.AdminOrderController.*(..))")
    public void inAdminUpdateOrder(){}


}
