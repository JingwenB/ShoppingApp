package com.example.shoppingApp.domain.response;

import com.example.shoppingApp.domain.entity.Order;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
//@JsonInclude(JsonInclude.Include.NON_NULL) // exclude null fields
public class OrderResponse {
    private String message;
    Order order;
}
