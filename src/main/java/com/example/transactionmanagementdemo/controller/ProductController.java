package com.example.transactionmanagementdemo.controller;

import com.example.transactionmanagementdemo.domain.entity.Product;
import com.example.transactionmanagementdemo.domain.entity.User;
import com.example.transactionmanagementdemo.domain.response.AllProductResponse;
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

    // admin get product,  visible to all attribute
    @GetMapping("/admin/{product_id}")
    public ProductResponse getProductById(@PathVariable int product_id){
        Product product = productService.getById(product_id);
        return ProductResponse.builder()
                .message("Returning product with id: " + product_id)
                .product(product)
                .build();
    }

    // user get product, not visible to stock quantity and wholesale price
    @GetMapping("/user/{product_id}")
    public ProductResponse getProductByIdAsUser(@PathVariable int product_id){
        Product product = productService.getByIdAsUser(product_id);
        return ProductResponse.builder()
                .message("Returning product with id: " + product_id)
                .product(product)
                .build();
    }

    @PutMapping("/admin")
    public AllProductResponse saveProduct(
            @RequestBody Product product){
        List<Product> products = productService.createProduct(product);

        return AllProductResponse.builder()
                .message("product saved, committing...")
                .products(products)
                .build();
    }

    @PutMapping("/update/{id}")
    public ProductResponse updateProduct(
            @PathVariable Integer id,
            @RequestBody Product product){
        Product addedProduct = productService.update(id, product);

        return ProductResponse.builder()
                .message("updated product with id: " + id)
                .product(addedProduct)
                .build();
    }




}
