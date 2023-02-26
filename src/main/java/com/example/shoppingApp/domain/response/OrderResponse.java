package com.example.shoppingApp.domain.response;

import com.example.shoppingApp.domain.common.ResponseStatus;
import com.example.shoppingApp.domain.entity.OrderItem;
import com.example.shoppingApp.domain.entity.User;
import lombok.*;

import java.sql.Timestamp;
import java.util.List;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor

public class OrderResponse {
    private int orderId;
    private Timestamp time;
    private Double totalPrice;
    private List<OrderItemResponse> orderItemResponseList;
}