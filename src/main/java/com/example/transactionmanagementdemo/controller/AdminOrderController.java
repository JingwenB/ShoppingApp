package com.example.transactionmanagementdemo.controller;

import com.example.transactionmanagementdemo.domain.entity.Order;
import com.example.transactionmanagementdemo.domain.entity.OrderItem;
import com.example.transactionmanagementdemo.domain.request.CreateOrderRequest;
import com.example.transactionmanagementdemo.domain.response.CreateOrderResponse;
import com.example.transactionmanagementdemo.domain.response.OrderDetailResponse;
import com.example.transactionmanagementdemo.domain.response.OrderResponse;
import com.example.transactionmanagementdemo.exception.NotEnoughInventoryException;
import com.example.transactionmanagementdemo.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("admin/order")
public class AdminOrderController {

    private final OrderService orderService;

    @Autowired
    public AdminOrderController(OrderService orderService) {
        this.orderService = orderService;
    }

    @PutMapping("/complete/{order_id}")
    public OrderResponse completeOrder(@PathVariable int order_id){
         Order updatedOrder = orderService.completeOrder(order_id);

         return
                 OrderResponse.builder()
                 .message("Updated order Id:" + order_id + " to complete")
                 .order(updatedOrder)
                 .build();
    }

    @PutMapping("/cancel/{order_id}")
    public OrderResponse cancelOrder(@PathVariable int order_id){
        Order updatedOrder = orderService.cancelOrder(order_id);

        return OrderResponse.builder()
                .message("Updated order Id:" + order_id + " to cancel")
                .order(updatedOrder)
                .build();

    }




}
