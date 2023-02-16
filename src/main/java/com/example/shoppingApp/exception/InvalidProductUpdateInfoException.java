package com.example.shoppingApp.exception;

public class InvalidProductUpdateInfoException extends RuntimeException{


    public InvalidProductUpdateInfoException(String message){
        super(String.format(message));

    }
}
