package com.example.shoppingApp.controller;

import com.example.shoppingApp.domain.entity.Order;
import com.example.shoppingApp.domain.entity.OrderItem;
import com.example.shoppingApp.domain.request.CreateOrderRequest;
import com.example.shoppingApp.domain.response.CreateOrderResponse;
import com.example.shoppingApp.domain.response.OrderDetailResponse;
import com.example.shoppingApp.domain.response.OrderResponse;
import com.example.shoppingApp.exception.NotEnoughInventoryException;
import com.example.shoppingApp.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
// purchase
@RequestMapping("user/order")
public class UserOrderController {

    private final OrderService orderService;

    @Autowired
    public UserOrderController(OrderService orderService) {
        this.orderService = orderService;
    }


    @GetMapping("/user_id/{user_id}")
    public List<Order> getOrdersByUserId(@PathVariable(value = "user_id")
                                                    int user_id){
        return orderService.getByUserId(user_id);
    }

    // user should not see product quantity and Wholesale_price
    @GetMapping("/order_id/{order_id}")
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



    @PutMapping("/cancel")
    public OrderResponse cancelOrder(@RequestParam int order_id){
        Order updatedOrder = orderService.cancelOrder(order_id);

        return OrderResponse.builder()
                .message("Updated order Id:" + order_id + " to cancel")
                .order(updatedOrder)
                .build();

    }

    @PostMapping("/create")
    public CreateOrderResponse createOrder(
            @RequestBody List<CreateOrderRequest> createOrderRequest,
            @RequestParam Integer user_id) {
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
