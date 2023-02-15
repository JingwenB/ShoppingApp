package com.example.shoppingApp.domain.request;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class CreateOrderRequest {
    private Integer product_id;
    private Integer quantity;
}

