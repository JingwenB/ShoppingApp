package com.example.shoppingApp.controller;

import com.example.shoppingApp.domain.entity.Product;
import com.example.shoppingApp.domain.response.AllProductResponse;
import com.example.shoppingApp.domain.response.ProductResponse;
import com.example.shoppingApp.domain.response.ResponseHandler;
import com.example.shoppingApp.service.ProductService;
import net.minidev.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import sun.jvm.hotspot.debugger.Page;

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
    public List<Product> getAllProduct(){
        return productService.getAll();
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
    public ProductResponse getProductById(@RequestParam int product_id){
        Product product = productService.getById(product_id);
        return ProductResponse.builder()
                .message("Returning product with id: " + product_id)
                .product(product)
                .build();
    }


    @PutMapping("/create")
    public AllProductResponse saveProduct(
            @RequestBody Product product){
        List<Product> products = productService.createProduct(product);

        return AllProductResponse.builder()
                .message("product saved, committing...")
                .products(products)
                .build();
    }

    @PutMapping("/update/{product_id}")
    public ProductResponse updateProduct(
            @PathVariable Integer product_id,
            @RequestBody Product product){
        Product addedProduct = productService.update(product_id, product);

        return ProductResponse.builder()
                .message("updated product with id: " + product_id)
                .product(addedProduct)
                .build();
    }




}
