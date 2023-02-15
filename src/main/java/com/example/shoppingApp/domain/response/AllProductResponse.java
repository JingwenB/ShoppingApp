package com.example.shoppingApp.domain.response;

import com.example.shoppingApp.domain.entity.Product;
import lombok.*;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class AllProductResponse {
    private String message;
    private List<Product> products;
}
