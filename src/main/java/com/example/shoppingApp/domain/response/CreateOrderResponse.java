package com.example.shoppingApp.domain.response;

import com.example.shoppingApp.domain.entity.Order;
import lombok.*;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class CreateOrderResponse {
    private String message;
    private List<Order> orders;
}
