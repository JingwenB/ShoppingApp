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
@RequestMapping("user/product")
public class UserProductController {

    private final ProductService productService;

    @Autowired
    public UserProductController(ProductService productService) {
        this.productService = productService;
    }


    @GetMapping("/all")
    public ResponseEntity<Object> getAllAsUser(){
//        return productService.getAllAsUser();

        return ResponseHandler.generateResponse(
                "returning all in-stock products without pagination",
                HttpStatus.OK,
                productService.getAllAsUser());
    }

    @GetMapping(value = "/all", params = { "page", "size"})
    public ResponseEntity<Object> getAllProductPaginated(@RequestParam(value = "page") int page,
                                                         @RequestParam(value = "size") int size){
        JSONObject data = productService.getPaginatedProductAsUser(page, size);

        return ResponseHandler.generateResponse(
                "returning all in-stock products with pagination",
                HttpStatus.OK,
                data);
    }



    // user get product, not visible to stock quantity and wholesale price
    @GetMapping("/product_id/{product_id}")
    public ResponseEntity<Object> getProductByIdAsUser(@PathVariable int product_id){
        Product product = productService.getByIdAsUser(product_id);

//        return ProductResponse.builder()
//                .message("Returning product with id: " + product_id)
//                .product(product)
//                .build();

        return ResponseHandler.generateResponse(
                "Returning product (id:" + product_id +") detail",
                HttpStatus.OK,
                product);
    }


}
