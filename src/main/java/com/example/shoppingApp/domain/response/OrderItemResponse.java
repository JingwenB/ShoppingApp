package com.example.shoppingApp.domain.response;

import lombok.*;

import java.sql.Timestamp;
import java.util.List;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor

public class OrderItemResponse {
    private String itemName;
    private int quantity;
}