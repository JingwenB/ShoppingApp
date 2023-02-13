package com.example.transactionmanagementdemo.domain.response;

import com.example.transactionmanagementdemo.domain.entity.Product;
import com.example.transactionmanagementdemo.domain.entity.User;
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
