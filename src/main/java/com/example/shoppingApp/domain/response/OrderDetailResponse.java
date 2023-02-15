package com.example.shoppingApp.domain.response;

import com.example.shoppingApp.domain.entity.Order;
import com.example.shoppingApp.domain.entity.OrderItem;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@Builder
//@JsonInclude(JsonInclude.Include.NON_NULL) // exclude null fields
public class OrderDetailResponse {
    private String message;
    Order order;
    List<OrderItem> orderItems;
}
