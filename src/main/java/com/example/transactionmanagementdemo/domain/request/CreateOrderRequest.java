package com.example.transactionmanagementdemo.domain.request;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.validator.constraints.UniqueElements;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotNull;

@Getter
@Setter
@Builder
public class CreateOrderRequest {
    private Integer product_id;
    private Integer quantity;
}

