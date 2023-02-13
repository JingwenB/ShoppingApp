package com.example.transactionmanagementdemo.domain.response;

import com.example.transactionmanagementdemo.domain.entity.Product;
import com.example.transactionmanagementdemo.domain.entity.Watch;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
//@JsonInclude(JsonInclude.Include.NON_NULL) // exclude null fields
public class WatchResponse {
    private String message;
    Watch watch;
}
