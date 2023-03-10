package com.example.shoppingApp.controller;

import com.example.shoppingApp.domain.entity.Order;
import com.example.shoppingApp.domain.entity.OrderItem;
import com.example.shoppingApp.domain.request.CreateOrderRequest;
import com.example.shoppingApp.domain.response.ResponseHandler;
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
import java.util.stream.Collectors;

@RestController
// purchase
@RequestMapping("user/order")
public class UserOrderController {

    private final OrderService orderService;
    private final UserService userService;

    @Autowired
    public UserOrderController(OrderService orderService, UserService userService) {
        this.orderService = orderService;
        this.userService = userService;
    }


    @GetMapping("")

    public List<Order> getOrdersByUserId(

    ){
        System.out.println(SecurityContextHolder.getContext().getAuthentication().getPrincipal());

        String username = (String) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        int id = userService.getIdByUsername(username);

        return orderService.getByUserId(id);
    }

    // user should not see product quantity and Wholesale_price
    @GetMapping("/order_id/{order_id}")
    public ResponseEntity<Object>  getOrderDetail(@PathVariable(value = "order_id")  Integer order_id){
        Order o = orderService.getById(order_id);
        List<OrderItem> orderItems = o.getOrderItems()
                .stream().map((orderItem) -> {
                    orderItem.getProduct().setWholesale_price(null);
                    orderItem.getProduct().setStock_quantity(null);
                    return orderItem;})
                .collect(Collectors.toList());

        JSONObject data = new JSONObject();
        data.put("order", o);
        data.put("orderItems", orderItems);

        return ResponseHandler
                .generateResponse(
                        "get order details for Order: " + order_id,
                        HttpStatus.OK,
                        data);
    }



    @PutMapping("/cancel")
    public ResponseEntity<Object>  cancelOrder(@RequestParam int order_id){
        Order updatedOrder = orderService.cancelOrder(order_id);

        JSONObject data = new JSONObject();
        data.put("updatedOrder", updatedOrder);

        return ResponseHandler
                .generateResponse("Updated order Id:" + order_id + " to cancel",
                        HttpStatus.OK,
                        data);

    }

    @PostMapping("/create")
    public ResponseEntity<Object> createOrder(
            @RequestBody List<CreateOrderRequest> createOrderRequest,
            @RequestParam Integer user_id) throws NotEnoughInventoryException{

        String username = (String) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        int id = userService.getIdByUsername(username);

        orderService.createOrder(createOrderRequest, id);

        JSONObject data = new JSONObject();
        data.put("All orders", orderService.getByUserId(id));

        return ResponseHandler
                .generateResponse("successfully created a order, " +
                                    "returning all orders by this user...",
                        HttpStatus.OK,
                        data);

    }



}
