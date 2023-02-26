package com.example.shoppingApp.domain.message;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.io.Serializable;

@Getter
@Setter
@Builder
@ToString
public class ItemMessage implements Serializable{
    private String itemName;
    private Integer quantity;
}
