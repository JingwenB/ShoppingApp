package com.example.shoppingApp.domain.request;

import lombok.*;

@Getter
@Setter
@Builder
//@NoArgsConstructor
@AllArgsConstructor
public class CreateOrderRequest {
    private Integer product_id;
    private Integer quantity;
}

