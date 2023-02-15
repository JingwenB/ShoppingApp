package com.example.transactionmanagementdemo.controller;

import com.example.transactionmanagementdemo.domain.entity.Order;
import com.example.transactionmanagementdemo.domain.entity.OrderItem;
import com.example.transactionmanagementdemo.domain.entity.Product;
import com.example.transactionmanagementdemo.domain.entity.User;
import com.example.transactionmanagementdemo.domain.response.AllProductResponse;
import com.example.transactionmanagementdemo.domain.response.OrderDetailResponse;
import com.example.transactionmanagementdemo.domain.response.ResponseHandler;
import com.example.transactionmanagementdemo.service.OrderService;
import com.example.transactionmanagementdemo.service.SummaryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@RestController
@RequestMapping("admin/summary")
public class AdminSummaryController {

    private final OrderService orderService;
    private final SummaryService summaryService;

    @Autowired
    public AdminSummaryController(OrderService orderService,
                                  SummaryService summaryService) {
        this.orderService = orderService;
        this.summaryService = summaryService;
    }

    // summary get all order
    @GetMapping("/order/all")
    public List<Order> getAllOrder(){
        return orderService.getAll();
    }

    // get order list for user
    @GetMapping("/order/user")
    public List<Order> getOrderByUserId(@RequestParam(value = "user_id")
                                                    int user_id){
        return orderService.getByUserId(user_id);
    }


    // summary order detail
    @GetMapping("/order/detail")
    public OrderDetailResponse getOrderDetailAsAdmin(@RequestParam(value = "order_id")  Integer order_id){
        Order o = orderService.getById(order_id);
        List<OrderItem> orderItems = new ArrayList<>(o.getOrderItems());
        return OrderDetailResponse.builder()
                .message("get order details for Order: " + order_id)
                .order(o)
                .orderItems(orderItems)
                .build();
    }


    @GetMapping("/product/topSold")
    public AllProductResponse getTopKPurchasedProduct(@RequestParam(value = "num") Integer num){
        List<Product> products = summaryService.topKPurchasedProduct(num);

        return AllProductResponse.builder()
                .message("return top " + num + " frequent purchased product: ")
                .products(products)
                .build();
    }

    @GetMapping("/product/topProfit")
    public AllProductResponse getTopKProfitedProduct(@RequestParam(value = "num") Integer num){
        List<Product> products = summaryService.topKProfitedProduct(num);

        return AllProductResponse.builder()
                .message("return top " + num + " most profits product: ")
                .products(products)
                .build();
    }

    @GetMapping("/user/topSpent")
    public ResponseEntity<Object> getTopKSpentUser(@RequestParam(value = "num") Integer num){
        List<User> users = summaryService.topKSpentUser(num);

        return ResponseHandler
                .generateResponse("getting top " + num + " money spent user",
                        HttpStatus.OK,
                        users);
    }

    @GetMapping("/product/count_all")
    public ResponseEntity<Object> getAllSuccessSoldItem(){
        int overall_quantity = summaryService.totalQuantitySoldSofar();

        return ResponseHandler
                .generateResponse("getting overall sold items quantity",
                        HttpStatus.OK,
                        overall_quantity);

    }


}
