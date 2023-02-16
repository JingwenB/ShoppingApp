package com.example.shoppingApp.exception;

public class SaveFailedException extends RuntimeException{


    public SaveFailedException(String message){
        super(String.format(message));

    }
}
