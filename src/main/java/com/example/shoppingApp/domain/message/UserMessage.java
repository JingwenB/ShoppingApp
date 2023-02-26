package com.example.shoppingApp.domain.message;

import com.example.shoppingApp.domain.entity.User;
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
public class UserMessage implements Serializable{
    private String userName;
    private String userEmail;
    private List<OrderMessage> orderMessageList;
}
