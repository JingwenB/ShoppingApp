package com.example.shoppingApp.service;


import com.example.shoppingApp.dao.OrderItemDao;
import com.example.shoppingApp.domain.entity.OrderItem;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
public class OrderItemServiceTest {

    @Mock
    private OrderItemDao orderItemDao;

    @InjectMocks
    private OrderItemService orderItemService;

    @BeforeEach
     void init() {
    }


    @Test
    void test_getAll_success() {
        List<OrderItem> orderItems = new ArrayList<>();
        orderItems.add(OrderItem.builder().build());

        Mockito.when(orderItemDao.getAll()).thenReturn(orderItems);

        List<OrderItem> actual = orderItemService.getAll();
        assertEquals(1, actual.size());
        verify(orderItemDao).getAll();
    }


}
