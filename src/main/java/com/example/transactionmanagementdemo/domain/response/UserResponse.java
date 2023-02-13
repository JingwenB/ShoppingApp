package com.example.transactionmanagementdemo.domain.response;

import com.example.transactionmanagementdemo.domain.common.ResponseStatus;
import com.example.transactionmanagementdemo.domain.entity.User;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
//@JsonInclude(JsonInclude.Include.NON_NULL) // exclude null fields
public class UserResponse {
    String message;
    User user;
}
