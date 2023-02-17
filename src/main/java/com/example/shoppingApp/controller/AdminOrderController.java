package com.example.shoppingApp.controller;

import com.example.shoppingApp.domain.entity.Order;
import com.example.shoppingApp.domain.response.ResponseHandler;
import com.example.shoppingApp.service.OrderService;
import net.minidev.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
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
    public ResponseEntity<Object> completeOrder(@PathVariable int order_id){
         Order updatedOrder = orderService.completeOrder(order_id);

        JSONObject data = new JSONObject();
        data.put("order", updatedOrder);

         return ResponseHandler.generateResponse(
                 "Updated order Id:" + order_id + " to complete",
                 HttpStatus.OK,
                 data
                 );
    }

    @PutMapping("/cancel/{order_id}")
    public ResponseEntity<Object> cancelOrder(@PathVariable int order_id){
        Order updatedOrder = orderService.cancelOrder(order_id);

        JSONObject data = new JSONObject();
        data.put("order", updatedOrder);
        return ResponseHandler.generateResponse(
                "Updated order Id:" + order_id + " to complete",
                HttpStatus.OK,
                data
        );

    }




}
