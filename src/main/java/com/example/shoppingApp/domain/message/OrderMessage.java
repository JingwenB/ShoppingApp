package com.example.shoppingApp.domain.message;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.io.Serializable;
import java.sql.Timestamp;
import java.util.List;

@Getter
@Setter
@Builder
@ToString
public class OrderMessage implements Serializable{


    private Double totalPrice;
    private Timestamp date;
    private List<ItemMessage> itemMessageList;
}
