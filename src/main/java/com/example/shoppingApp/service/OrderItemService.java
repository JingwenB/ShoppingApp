package com.example.shoppingApp.service;

import com.example.shoppingApp.dao.OrderItemDao;
import com.example.shoppingApp.domain.entity.OrderItem;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class OrderItemService {
    private OrderItemDao orderItemDao;

    @Autowired
    public OrderItemService(OrderItemDao orderItemDao) {
        this.orderItemDao = orderItemDao;
    }


    @Transactional
    public List<OrderItem> getAll(){
        return orderItemDao.getAll();
    }

//    @Transactional
//    public List<OrderItem> getByUserId(int id){
//
//        return orderItemDao.getAll().stream().filter(
//                (order) -> order.getOrder().getUser().getId() == id).
//                sorted(Comparator.comparing(a -> a.getOrder().getDate_placed())).collect(Collectors.toList());
//    }
//
//    @Transactional
//    public List<OrderItem> getByOrderId(int id){
//        List<OrderItem> orderItems =  orderItemDao.getAll();
//
//        return orderItems.stream().filter(
//                (order)->order.getOrder().getId() == id).collect(Collectors.toList());
//    }







}
