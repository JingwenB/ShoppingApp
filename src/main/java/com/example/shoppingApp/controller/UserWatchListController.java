package com.example.shoppingApp.controller;

import com.example.shoppingApp.domain.entity.Product;
import com.example.shoppingApp.domain.response.ResponseHandler;
import com.example.shoppingApp.service.WatchListService;
import net.minidev.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
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
    public ResponseEntity<Object> addWatchList(
            @RequestParam(value = "user_id") Integer user_id,
            @RequestParam(value = "product_id") Integer product_id){
        List<Product> products = watchListService.addToWatchList(user_id, product_id);

        JSONObject data = new JSONObject();
        data.put("watchlist", products);
        return ResponseHandler
                .generateResponse(
                        "adding new product (id: " + product_id +
                                ") to user (id: " + user_id +") watchlist",
                        HttpStatus.OK,
                        data);
    }

    @DeleteMapping("/delete")
    public ResponseEntity<Object> deleteProductFromUserWatchList(
            @RequestParam(value = "user_id") Integer user_id,
            @RequestParam(value = "product_id") Integer product_id){

        List<Product> products = watchListService.deleteProductFromUserWatchList(user_id, product_id);

        JSONObject data = new JSONObject();
        data.put("watchlist", products);

        return ResponseHandler
                .generateResponse(
                        "getting all products watched by user_id : " + user_id +
                                "after remove product: " + product_id,
                        HttpStatus.OK,
                        data);
    }

    @GetMapping("/getList")
    public ResponseEntity<Object> getWatchListItemByUserId(
            @RequestParam(value = "user_id") Integer user_id){
        List<Product> products = watchListService.getWatchListItemByUserId(user_id);

        JSONObject data = new JSONObject();
        data.put("watchlist", products);
        return ResponseHandler
                .generateResponse(
                        "getting all products watched by user_id: " + user_id,
                        HttpStatus.OK,
                        data);
    }


}
