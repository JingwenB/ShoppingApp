package com.example.transactionmanagementdemo.domain.response;

import com.example.transactionmanagementdemo.domain.entity.Order;
import com.example.transactionmanagementdemo.domain.entity.User;
import lombok.*;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class AllOrderResponse {
    private String message;
    private List<Order> orders;
}
