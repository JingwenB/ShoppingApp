package com.example.shoppingApp.controller;

import com.example.shoppingApp.domain.entity.Order;
import com.example.shoppingApp.domain.response.OrderResponse;
import com.example.shoppingApp.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

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
