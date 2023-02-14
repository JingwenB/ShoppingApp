package com.example.transactionmanagementdemo.controller;

import com.example.transactionmanagementdemo.domain.entity.Order;
import com.example.transactionmanagementdemo.domain.entity.OrderItem;
import com.example.transactionmanagementdemo.domain.entity.Product;
import com.example.transactionmanagementdemo.domain.entity.User;
import com.example.transactionmanagementdemo.domain.request.CreateOrderRequest;
import com.example.transactionmanagementdemo.domain.response.*;
import com.example.transactionmanagementdemo.exception.NotEnoughInventoryException;
import com.example.transactionmanagementdemo.exception.UserGetFailedException;
import com.example.transactionmanagementdemo.exception.UserSaveFailedException;
import com.example.transactionmanagementdemo.service.OrderService;
import com.example.transactionmanagementdemo.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

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

    @GetMapping("/user_id/{user_id}")
    public List<Order> getOrderByUserId(@PathVariable(value = "user_id")
                                                    int user_id){
        return orderService.getByUserId(user_id);
    }

    // user should not see product quantity and Wholesale_price
    @GetMapping("/user/order_id/{order_id}")
    public OrderDetailResponse getOrderDetail(@PathVariable(value = "order_id")  Integer order_id){
        Order o = orderService.getById(order_id);
        List<OrderItem> orderItems = o.getOrderItems()
                .stream().map((orderItem) -> {
                    orderItem.getProduct().setWholesale_price(null);
                    orderItem.getProduct().setStock_quantity(null);
                    return orderItem;})
                .collect(Collectors.toList());
        return OrderDetailResponse.builder()
                .message("get order details for Order: " + order_id)
                .order(o)
                .orderItems(orderItems)
                .build();
    }
    @GetMapping("/admin/order_id/{order_id}")
    public OrderDetailResponse getOrderDetailAsAdmin(@PathVariable(value = "order_id")  Integer order_id){
        Order o = orderService.getById(order_id);
        List<OrderItem> orderItems = new ArrayList<>(o.getOrderItems());
        return OrderDetailResponse.builder()
                .message("get order details for Order: " + order_id)
                .order(o)
                .orderItems(orderItems)
                .build();
    }

    @PutMapping("/admin/complete/{id}")
    public OrderResponse completeOrder(@PathVariable int id){
         Order updatedOrder = orderService.completeOrder(id);

         return OrderResponse.builder()
                 .message("Updated order Id:" + id + " to complete")
                 .order(updatedOrder)
                 .build();
    }

    @PutMapping("/cancel/{id}")
    public OrderResponse cancelOrder(@PathVariable int id){
        Order updatedOrder = orderService.cancelOrder(id);

        return OrderResponse.builder()
                .message("Updated order Id:" + id + " to cancel")
                .order(updatedOrder)
                .build();

    }

    @PostMapping("/createOrder/{user_id}")
    public CreateOrderResponse createOrder(@RequestBody List<CreateOrderRequest> createOrderRequest,
                            @PathVariable Integer user_id) {
        try{
            orderService.createOrder(createOrderRequest, user_id);
            return CreateOrderResponse.builder()
                    .message("successfully created a order, " +
                            "returning all orders by this user...")
                    .orders(orderService.getByUserId(user_id))
                    .build();
        } catch (NotEnoughInventoryException e){
            return CreateOrderResponse.builder()
                    .message("created a order failed with error: " +
                            e.getMessage() +
                            "\nreturning all orders by this user...")
                    .orders(orderService.getByUserId(user_id))
                    .build();
        }

    }



}
