package com.example.transactionmanagementdemo.domain.response;

import com.example.transactionmanagementdemo.domain.entity.Product;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
//@JsonInclude(JsonInclude.Include.NON_NULL) // exclude null fields
public class UserProductResponse {
    private String message;
    Product product;
}