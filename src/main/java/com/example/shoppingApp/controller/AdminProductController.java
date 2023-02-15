package com.example.shoppingApp.controller;

import com.example.shoppingApp.domain.entity.Product;
import com.example.shoppingApp.domain.response.ResponseHandler;
import com.example.shoppingApp.service.ProductService;
import net.minidev.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("admin/product")
public class AdminProductController {

    private final ProductService productService;

    @Autowired
    public AdminProductController(ProductService productService) {
        this.productService = productService;
    }

    @GetMapping("/all")
    public ResponseEntity<Object> getAllProduct(){
        return ResponseHandler.generateResponse(
                "returning all products without  pagination",
                HttpStatus.OK,
                productService.getAll());
    }

    @GetMapping(value = "/all", params = { "page", "size"})
    public ResponseEntity<Object> getAllProductPaginated(@RequestParam(value = "page") int page,
                                                         @RequestParam(value = "size") int size){
        JSONObject data = productService.getPaginatedProduct(page, size);

        return ResponseHandler.generateResponse(
                "returning products with pagination",
                HttpStatus.OK,
                data);
    }

    // admin get product, visible to all attribute
    @GetMapping("/detail")
    public ResponseEntity<Object> getProductById(@RequestParam int product_id){
        Product product = productService.getById(product_id);

        return ResponseHandler.generateResponse(
                "Returning product with id: " + product_id,
                HttpStatus.OK,
                product);
    }


    @PutMapping("/create")
    public ResponseEntity<Object> saveProduct(
            @RequestBody Product product){
        List<Product> products = productService.createProduct(product);

        return ResponseHandler.generateResponse(
                "New Product saved, retrieving all products",
                HttpStatus.OK,
                products);
    }

    @PutMapping("/update/{product_id}")
    public ResponseEntity<Object> updateProduct(
            @PathVariable Integer product_id,
            @RequestBody Product product){
        Product addedProduct = productService.update(product_id, product);

        return ResponseHandler.generateResponse(
                "Product updated, retrieving updated product",
                HttpStatus.OK,
                addedProduct);

    }




}
