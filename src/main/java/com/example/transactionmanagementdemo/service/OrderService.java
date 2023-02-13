package com.example.transactionmanagementdemo.service;

import com.example.transactionmanagementdemo.dao.OrderDao;
import com.example.transactionmanagementdemo.dao.UserDao;
import com.example.transactionmanagementdemo.domain.entity.Order;
import com.example.transactionmanagementdemo.domain.entity.User;
import com.example.transactionmanagementdemo.exception.UserGetFailedException;
import com.example.transactionmanagementdemo.exception.UserSaveFailedException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class OrderService {
    private OrderDao orderDao;

    @Autowired
    public OrderService(OrderDao orderDao) {
        this.orderDao = orderDao;
    }


    @Transactional
    public List<Order> getAll(){
        return orderDao.getAll();
    }

    @Transactional
    public List<Order> getByUserId(int id){
        List<Order> orders =  orderDao.getAll();
        orders.stream().filter(
                (order)->order.getId() == id).collect(Collectors.toList());

        return orders;
    }



}
