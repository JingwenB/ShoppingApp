package com.example.shoppingApp.controller;

import com.example.shoppingApp.domain.entity.Order;
import com.example.shoppingApp.domain.entity.OrderItem;
import com.example.shoppingApp.domain.entity.Product;
import com.example.shoppingApp.domain.response.AllProductResponse;
import com.example.shoppingApp.domain.response.OrderDetailResponse;
import com.example.shoppingApp.service.OrderService;
import com.example.shoppingApp.service.SummaryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("user/summary")
public class UserSummaryController {

    private final OrderService orderService;
    private final SummaryService summaryService;

    @Autowired
    public UserSummaryController(OrderService orderService,
                                 SummaryService summaryService) {
        this.orderService = orderService;
        this.summaryService = summaryService;
    }


    @GetMapping("/order")
    public List<Order> getOrdersByUserId(@RequestParam(value = "user_id")
                                                    int user_id){
        return orderService.getByUserId(user_id);
    }

    // user view order detailsshould not see product quantity and Wholesale_price
    @GetMapping("/order/detail")
    public OrderDetailResponse getOrderDetail(@RequestParam(value = "order_id") int order_id){
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

    @GetMapping("/product/topItem")
    public AllProductResponse getTopKItem(@RequestParam(value = "user_id") Integer user_id,
                                          @RequestParam(value = "num") Integer num){
        List<Product> products = summaryService.topKPurchasedProductByUser(user_id, num);

        return AllProductResponse.builder()
                .message("return top frequent purchased item by user: " + user_id)
                .products(products)
                .build();
    }

    @GetMapping("/product/mostRecentProduct")
    public AllProductResponse getMostRecentKProduct(@RequestParam(value = "user_id") Integer user_id,
                                          @RequestParam(value = "num") Integer num){
        List<Product> products = summaryService.mostKRecentPurchaseByUser(user_id, num);

        return AllProductResponse.builder()
                .message("return most recent purchased item by user: " + user_id)
                .products(products)
                .build();
    }








}