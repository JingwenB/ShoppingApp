package com.example.shoppingApp.controller;

import com.example.shoppingApp.domain.entity.Product;
import com.example.shoppingApp.domain.response.AllProductResponse;
import com.example.shoppingApp.service.WatchListService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("user/watchlist")
public class UserWatchListController {
    private final WatchListService watchListService;

    @Autowired
    public UserWatchListController(WatchListService watchListService) {
        this.watchListService = watchListService;
    }

    @PutMapping("/add")
    public AllProductResponse addWatchList(
            @RequestParam(value = "user_id") Integer user_id,
            @RequestParam(value = "product_id") Integer product_id){
        List<Product> products = watchListService.addToWatchList(user_id, product_id);

        return AllProductResponse.builder()
                .message("adding new product: " + product_id +
                        "to user: " + user_id)
                .products(products)
                .build();
    }

    @DeleteMapping("/delete")
    public AllProductResponse deleteProductFromUserWatchList(
            @RequestParam(value = "user_id") Integer user_id,
            @RequestParam(value = "product_id") Integer product_id){

        List<Product> products = watchListService.deleteProductFromUserWatchList(user_id, product_id);

        return AllProductResponse.builder()
                .message("getting all products watched by user_id : " + user_id +
                        "after remove product: " + product_id)
                .products(products)
                .build();
    }

    @GetMapping("/getList")
    public AllProductResponse getWatchListItemByUserId(
            @RequestParam(value = "user_id") Integer user_id){
        List<Product> products = watchListService.getWatchListItemByUserId(user_id);

        return AllProductResponse.builder()
                .message("getting all products watched by user_id: " + user_id)
                .products(products)
                .build();
    }


}
