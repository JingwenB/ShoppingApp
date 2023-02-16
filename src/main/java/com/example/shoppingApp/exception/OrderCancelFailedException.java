package com.example.shoppingApp.exception;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class OrderCancelFailedException extends RuntimeException{
//    private String currentStatus;
//    private String currentStatus;
    public OrderCancelFailedException(String message){
        super(String.format(message));
    }
}
