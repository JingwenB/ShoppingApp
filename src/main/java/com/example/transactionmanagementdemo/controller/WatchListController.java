package com.example.transactionmanagementdemo.controller;

import com.example.transactionmanagementdemo.domain.entity.Product;
import com.example.transactionmanagementdemo.domain.response.AllProductResponse;
import com.example.transactionmanagementdemo.domain.response.ProductResponse;
import com.example.transactionmanagementdemo.service.ProductService;
import com.example.transactionmanagementdemo.service.UserService;
import com.example.transactionmanagementdemo.service.WatchListService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("watchlist")
public class WatchListController {
    private final WatchListService watchListService;

    @Autowired
    public WatchListController(WatchListService watchListService) {
        this.watchListService = watchListService;
    }

    @PutMapping("/{user_id}/{product_id}")
    public AllProductResponse addWatchList(
            @PathVariable Integer user_id,
            @PathVariable Integer product_id){
        List<Product> products = watchListService.addToWatchList(user_id, product_id);

        return AllProductResponse.builder()
                .message("adding new product: " + product_id +
                        "to user: " + user_id)
                .products(products)
                .build();
    }

    @DeleteMapping("/{user_id}/{product_id}")
    public AllProductResponse deleteProductFromUserWatchList(
            @PathVariable Integer user_id,
            @PathVariable Integer product_id){

        List<Product> products = watchListService.deleteProductFromUserWatchList(user_id, product_id);

        return AllProductResponse.builder()
                .message("getting all products watched by user_id : " + user_id +
                        "after remove product: " + product_id)
                .products(products)
                .build();
    }

    @GetMapping("/{user_id}")
    public AllProductResponse getWatchListItemByUserId(
            @PathVariable Integer user_id){
        List<Product> products = watchListService.getWatchListItemByUserId(user_id);

        return AllProductResponse.builder()
                .message("getting all products watched by user_id: " + user_id)
                .products(products)
                .build();
    }


}
