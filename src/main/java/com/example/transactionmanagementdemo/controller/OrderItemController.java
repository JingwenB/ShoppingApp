package com.example.transactionmanagementdemo.controller;

import com.example.transactionmanagementdemo.domain.entity.Order;
import com.example.transactionmanagementdemo.domain.entity.OrderItem;
import com.example.transactionmanagementdemo.service.OrderItemService;
import com.example.transactionmanagementdemo.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("orderItem")
public class OrderItemController {

    private final OrderItemService orderItemService;


    @Autowired
    public OrderItemController(OrderItemService orderItemService) {
        this.orderItemService = orderItemService;
    }

    @GetMapping("/all")
    public List<OrderItem> getAllOrderItem(){
        return orderItemService.getAll();
    }

    @GetMapping("/user_id/{id}")
    public List<OrderItem> getOrderByUserId(@PathVariable int id){
        return orderItemService.getByUserId(id);
    }

    @GetMapping("/order_id/{id}")
    public List<OrderItem> getOrderByOrderId(@PathVariable int id){
        return orderItemService.getByOrderId(id);
    }

}
