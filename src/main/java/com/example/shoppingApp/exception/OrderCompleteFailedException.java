package com.example.shoppingApp.exception;

public class OrderCompleteFailedException extends RuntimeException{

    public OrderCompleteFailedException(String message){
        super(String.format(message));

    }
}
