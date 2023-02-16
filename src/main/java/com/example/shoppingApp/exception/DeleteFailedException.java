package com.example.shoppingApp.exception;

public class DeleteFailedException extends RuntimeException{


    public DeleteFailedException(String message){
        super(String.format(message));

    }
}
