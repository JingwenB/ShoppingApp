package com.example.shoppingApp.dao;


import com.example.shoppingApp.domain.entity.OrderItem;
import org.springframework.stereotype.Repository;



@Repository
public class OrderItemDao extends GenericDao<OrderItem> {

    public OrderItemDao() {
        entityClass = OrderItem.class;
    }






}
