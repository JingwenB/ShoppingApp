package com.example.transactionmanagementdemo.controller;

import com.example.transactionmanagementdemo.domain.entity.Product;
import com.example.transactionmanagementdemo.domain.response.AllProductResponse;
import com.example.transactionmanagementdemo.domain.response.ProductResponse;
import com.example.transactionmanagementdemo.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("user/product")
public class UserProductController {

    private final ProductService productService;

    @Autowired
    public UserProductController(ProductService productService) {
        this.productService = productService;
    }


    @GetMapping("/all")
    public List<Product> getAllAsUser(){
        return productService.getAllAsUser();
    }



    // user get product, not visible to stock quantity and wholesale price
    @GetMapping("/product_id/{product_id}")
    public ProductResponse getProductByIdAsUser(@PathVariable int product_id){
        Product product = productService.getByIdAsUser(product_id);
        return ProductResponse.builder()
                .message("Returning product with id: " + product_id)
                .product(product)
                .build();
    }


}
