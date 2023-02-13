package com.example.transactionmanagementdemo.dao;


import com.example.transactionmanagementdemo.domain.entity.Order;
import com.example.transactionmanagementdemo.domain.entity.OrderItem;
import com.example.transactionmanagementdemo.exception.UserSaveFailedException;
import org.hibernate.Session;
import org.springframework.stereotype.Repository;


@Repository
public class OrderItemDao extends GenericDao<OrderItem> {

    public OrderItemDao() {
        entityClass = OrderItem.class;
    }





}
