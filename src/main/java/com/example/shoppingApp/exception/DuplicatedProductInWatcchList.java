package com.example.shoppingApp.exception;

public class DuplicatedProductInWatcchList extends RuntimeException{

    public DuplicatedProductInWatcchList(String message){
        super(String.format(message));

    }
}
