package com.example.shoppingApp.controller;

import com.example.shoppingApp.domain.common.ResponseStatus;
import com.example.shoppingApp.domain.entity.Order;
import com.example.shoppingApp.domain.entity.OrderItem;
import com.example.shoppingApp.domain.entity.User;
import com.example.shoppingApp.domain.request.CreateOrderRequest;
import com.example.shoppingApp.domain.response.*;
import com.example.shoppingApp.exception.NotEnoughInventoryException;
import com.example.shoppingApp.service.OrderService;
import com.example.shoppingApp.service.UserService;
import net.minidev.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.stream.Collectors;

@RestController
// purchase

public class UserOrderOrderItemController {

    private final OrderService orderService;
    private final UserService userService;

    @Autowired
    public UserOrderOrderItemController(OrderService orderService, UserService userService) {
        this.orderService = orderService;
        this.userService = userService;
    }


    @GetMapping("/user/{userId}/order/{orderId}")

    public UserOrderResponse getOrderByUserIdAndOrderId(
            @PathVariable int userId,
            @PathVariable int orderId
    ){
        Order order = orderService.getById(orderId);
        User user = userService.getById(userId);
        List<OrderItemResponse> orderItemResponses = order.getOrderItems()
                .stream()
                .map(orderItem -> OrderItemResponse.builder()
                        .itemName(orderItem.getProduct().getName())
                        .quantity(orderItem.getPurchased_quantity())
                        .build())
                .collect(Collectors.toList());

        Double totalPrice = order.getOrderItems()
                .stream()
                .mapToDouble(orderItem -> orderItem.getPurchased_quantity() * orderItem.getPurchased_price())
                .sum();

        return UserOrderResponse.builder()
                .serviceStatus(
                        ResponseStatus.builder()
                                .message("success retrieved")
                                .success(true)
                                .build())
                .userResponse(
                        UserResponse.builder()
                                .username(user.getUsername())
                                .lastname(user.getLastname())
                                .firstname(user.getFirstname())
                                .email(user.getEmail()).build())
                .orderResponse(
                        OrderResponse.builder()
                                .orderId(orderId)
                                .time(order.getDate_placed())
                                .totalPrice(totalPrice)
                                .orderItemResponseList(orderItemResponses).build()
                ).build();

    }


    @GetMapping("async/user/{userId}/order/{orderId}")

    public UserOrderResponse getOrderByUserIdAndOrderIdAsync(
            @PathVariable int userId,
            @PathVariable int orderId
    ){
        UserOrderResponse userOrderResponse = new UserOrderResponse();

        CompletableFuture<Void> orderFuture = CompletableFuture
                .runAsync(() -> {
                    Order order = orderService.getById(orderId);
                    List<OrderItemResponse> orderItemResponses = order.getOrderItems()
                            .stream()
                            .map(orderItem -> OrderItemResponse.builder()
                                    .itemName(orderItem.getProduct().getName())
                                    .quantity(orderItem.getPurchased_quantity())
                                    .build())
                            .collect(Collectors.toList());

                    Double totalPrice = order.getOrderItems()
                            .stream()
                            .mapToDouble(orderItem -> orderItem.getPurchased_quantity() * orderItem.getPurchased_price())
                            .sum();

                    userOrderResponse.setOrderResponse(OrderResponse.builder()
                            .orderId(orderId)
                            .time(order.getDate_placed())
                            .totalPrice(totalPrice)
                            .orderItemResponseList(orderItemResponses).build());
                });

        CompletableFuture<Void> userFuture = CompletableFuture
                .runAsync(() -> {
                    User user = userService.getById(userId);
                    userOrderResponse.setUserResponse(
                            UserResponse.builder()
                            .username(user.getUsername())
                            .lastname(user.getLastname())
                            .firstname(user.getFirstname())
                            .email(user.getEmail()).build());

                });

        CompletableFuture.allOf(orderFuture, userFuture).join();


        userOrderResponse.setServiceStatus(
                ResponseStatus.builder()
                        .message("success retrieved")
                        .success(true)
                        .build());

        return userOrderResponse;

    }

}
