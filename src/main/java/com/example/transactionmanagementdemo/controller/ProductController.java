package com.example.transactionmanagementdemo.controller;

import com.example.transactionmanagementdemo.domain.entity.Product;
import com.example.transactionmanagementdemo.domain.entity.User;
import com.example.transactionmanagementdemo.domain.response.ProductResponse;
import com.example.transactionmanagementdemo.domain.response.UserResponse;
import com.example.transactionmanagementdemo.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("product")
public class ProductController {

    private final ProductService productService;

    @Autowired
    public ProductController(ProductService productService) {
        this.productService = productService;
    }

    @GetMapping("/admin")
    public List<Product> getAllProduct(){
        return productService.getAll();
    }
    @GetMapping("/user")
    public List<Product> getAllAsUser(){
        return productService.getAllAsUser();
    }

    @GetMapping("/admin/id/{id}")
    public ProductResponse getProductById(@PathVariable int id){
        Product product = productService.getById(id);
        return ProductResponse.builder()
                .message("Returning product with id: " + id)
                .product(product)
                .build();
    }

    @GetMapping("/user/id/{id}")
    public ProductResponse getProductByIdAsUser(@PathVariable int id){
        Product product = productService.getByIdAsUser(id);
        return ProductResponse.builder()
                .message("Returning product with id: " + id)
                .product(product)
                .build();
    }

    @PutMapping("/admin")
    public ProductResponse saveProduct(
            @RequestBody Product product){
        productService.createProduct(product);

        return ProductResponse.builder()
                .message("product saved, committing...")
                .product(product)
                .build();
    }



}
