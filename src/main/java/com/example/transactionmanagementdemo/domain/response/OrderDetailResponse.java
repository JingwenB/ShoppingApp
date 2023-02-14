package com.example.transactionmanagementdemo.domain.response;

import com.example.transactionmanagementdemo.domain.entity.Order;
import com.example.transactionmanagementdemo.domain.entity.OrderItem;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.util.List;
import java.util.Set;

@Getter
@Setter
@Builder
//@JsonInclude(JsonInclude.Include.NON_NULL) // exclude null fields
public class OrderDetailResponse {
    private String message;
    Order order;
    List<OrderItem> orderItems;
}
