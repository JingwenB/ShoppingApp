package com.example.transactionmanagementdemo.controller;

import com.example.transactionmanagementdemo.domain.entity.Order;
import com.example.transactionmanagementdemo.domain.entity.Product;
import com.example.transactionmanagementdemo.domain.entity.Watch;
import com.example.transactionmanagementdemo.domain.response.ProductResponse;
import com.example.transactionmanagementdemo.domain.response.WatchResponse;
import com.example.transactionmanagementdemo.service.OrderService;
import com.example.transactionmanagementdemo.service.WatchService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("watch")
public class WatchController {

    private final WatchService watchService;

    @Autowired
    public WatchController(WatchService watchService) {
        this.watchService = watchService;
    }

    @GetMapping("/all")
    public List<Watch> getAllWatch(){
        return watchService.getAll();
    }

    @GetMapping("/user_id/{id}")
    public List<Watch> getWatchByUserId(@PathVariable int id){
        return watchService.getByUserId(id);
    }

    @PutMapping("/{user_id}/{watch_id}")
    public WatchResponse saveWatch(
            @PathVariable("user_id") int user_id,
            @PathVariable("watch_id")  int watch_id){
        Watch watch = watchService.createWatch(user_id, watch_id);

        return WatchResponse.builder()
                .message("watch saved, committing...")
                .watch(watch)
                .build();
    }


}
