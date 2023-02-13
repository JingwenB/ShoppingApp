package com.example.transactionmanagementdemo.controller;

import com.example.transactionmanagementdemo.domain.entity.Order;
import com.example.transactionmanagementdemo.domain.entity.Product;
import com.example.transactionmanagementdemo.domain.entity.User;
import com.example.transactionmanagementdemo.domain.response.AllOrderResponse;
import com.example.transactionmanagementdemo.domain.response.ProductResponse;
import com.example.transactionmanagementdemo.domain.response.UserResponse;
import com.example.transactionmanagementdemo.exception.UserGetFailedException;
import com.example.transactionmanagementdemo.exception.UserSaveFailedException;
import com.example.transactionmanagementdemo.service.OrderService;
import com.example.transactionmanagementdemo.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("order")
public class OrderController {

    private final OrderService orderService;

    @Autowired
    public OrderController(OrderService orderService) {
        this.orderService = orderService;
    }

    @GetMapping("/all")
    public List<Order> getAllOrder(){
        return orderService.getAll();
    }

    @GetMapping("/user_id/{id}")
    public List<Order> getOrderByUserId(@PathVariable int id){
        return orderService.getByUserId(id);
    }

}
