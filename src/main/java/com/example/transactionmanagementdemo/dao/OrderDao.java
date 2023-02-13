package com.example.transactionmanagementdemo.dao;


import com.example.transactionmanagementdemo.domain.entity.Order;
import com.example.transactionmanagementdemo.domain.entity.User;
import com.example.transactionmanagementdemo.exception.UserGetFailedException;
import com.example.transactionmanagementdemo.exception.UserSaveFailedException;
import org.hibernate.Session;
import org.springframework.stereotype.Repository;


@Repository
public class OrderDao extends GenericDao<Order> {

    public OrderDao() {
        entityClass = Order.class;
    }





}
