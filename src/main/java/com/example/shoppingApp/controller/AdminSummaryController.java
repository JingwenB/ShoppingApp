package com.example.shoppingApp.controller;

import com.example.shoppingApp.domain.entity.Order;
import com.example.shoppingApp.domain.entity.OrderItem;
import com.example.shoppingApp.domain.entity.Product;
import com.example.shoppingApp.domain.entity.User;
import com.example.shoppingApp.domain.response.ResponseHandler;
import com.example.shoppingApp.service.OrderService;
import com.example.shoppingApp.service.SummaryService;
import net.minidev.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.*;

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
    public ResponseEntity<Object> getAllOrder(){
        List<Order> orders = orderService.getAll();

        JSONObject data = new JSONObject();
        data.put("all orders", orders);
        return ResponseHandler
                .generateResponse("get all orders for admin",
                        HttpStatus.OK,
                        data);
    }

    @GetMapping(value = "/order/all", params = { "page", "size"})
    public ResponseEntity<Object> getAllOrderPaginated(@RequestParam(value = "page") int page,
                                                         @RequestParam(value = "size") int size){
        JSONObject data = orderService.getPaginatedOrder(page, size);

        return ResponseHandler.generateResponse(
                "returning all order with pagination",
                HttpStatus.OK,
                data);
    }

    // get order list for user
    @GetMapping("/order/user")
    public ResponseEntity<Object> getOrderByUserId(@RequestParam(value = "user_id")
                                                    int user_id){
        List<Order> orders = orderService.getByUserId(user_id);

        JSONObject data = new JSONObject();
        data.put("orders", orders);
        return ResponseHandler
                .generateResponse("get all orders for user: " + user_id + " for admin preview",
                        HttpStatus.OK,
                        data);
    }

    @GetMapping(value = "/order/user", params = { "user_id","page",  "size"})
    public ResponseEntity<Object> getByUserIdPaginated(@RequestParam(value = "user_id")int user_id,
                                                   @RequestParam(value = "page") int page,
                                                   @RequestParam(value = "size") int size ){
        JSONObject data = orderService.getByUserIdPaginated(user_id, page, size);

        return ResponseHandler
                .generateResponse("get all orders for user: " + user_id + " for admin preview",
                        HttpStatus.OK,
                        data);
    }


    // summary order detail
    @GetMapping("/order/detail")
    public ResponseEntity<Object> getOrderDetailAsAdmin(@RequestParam(value = "order_id")  Integer order_id){
        Order o = orderService.getById(order_id);
        List<OrderItem> orderItems = new ArrayList<>(o.getOrderItems());

        JSONObject data = new JSONObject();
        data.put("order", o);
        data.put("orderItems", orderItems);

        return ResponseHandler
                .generateResponse("get order details for Order: " + order_id,
                        HttpStatus.OK,
                        data);
    }


    @GetMapping("/product/topSold")
    public ResponseEntity<Object> getTopKPurchasedProduct(@RequestParam(value = "num") Integer num){
        List<Product> products = summaryService.topKPurchasedProduct(num);


        JSONObject data = new JSONObject();
        data.put("products", products);
        return ResponseHandler
                .generateResponse("return top " + num + " frequent purchased product: ",
                        HttpStatus.OK,
                        data);
    }

    @GetMapping("/product/topProfit")
    public ResponseEntity<Object> getTopKProfitedProduct(@RequestParam(value = "num") Integer num){
        List<Product> products = summaryService.topKProfitedProduct(num);

        JSONObject data = new JSONObject();
        data.put("products", products);
        return  ResponseHandler
                .generateResponse("return top " + num + " most profits product: ",
                        HttpStatus.OK,
                        data);

    }

    @GetMapping("/user/topSpent")
    public ResponseEntity<Object> getTopKSpentUser(@RequestParam(value = "num") Integer num){
        List<User> users = summaryService.topKSpentUser(num);

        JSONObject data = new JSONObject();
        data.put("users", users);
        return ResponseHandler
                .generateResponse("getting top " + num + " money spent user",
                        HttpStatus.OK,
                        data);
    }

    @GetMapping("/product/count_all")
    public ResponseEntity<Object> getAllSuccessSoldItem(){
        int overall_quantity = summaryService.totalQuantitySoldSofar();

        JSONObject data = new JSONObject();
        data.put("overall_quantity", overall_quantity);
        return ResponseHandler
                .generateResponse("getting overall sold items quantity",
                        HttpStatus.OK,
                        data);

    }


}
