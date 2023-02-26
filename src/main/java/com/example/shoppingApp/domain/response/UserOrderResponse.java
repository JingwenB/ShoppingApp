package com.example.shoppingApp.domain.response;

import com.example.shoppingApp.domain.common.ResponseStatus;
import com.example.shoppingApp.domain.entity.OrderItem;
import com.example.shoppingApp.domain.entity.User;
import lombok.*;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor

public class UserOrderResponse {
    private ResponseStatus serviceStatus;
    private OrderResponse orderResponse;
    private UserResponse userResponse;
}