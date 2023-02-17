package com.example.shoppingApp.service;


import com.example.shoppingApp.dao.OrderDao;
import com.example.shoppingApp.dao.UserDao;
import com.example.shoppingApp.domain.entity.Order;
import com.example.shoppingApp.domain.entity.User;
import com.example.shoppingApp.exception.RequestPageOverTotalPageException;
import net.minidev.json.JSONObject;
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
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
public class UserServiceTest {

    @Mock
    private UserDao userDao;

    @InjectMocks
    private UserService userService;


    @Test
    void test_getAll() {

        Mockito.when(userDao.getAll()).thenReturn(new ArrayList<>());
        assertEquals(0, userService.getAll().size());
    }

    @Test
    void test_getById_success() {

        Mockito.when(userDao.getById(1)).thenReturn(User.builder().id(1).build());
        assertEquals(1, userService.getById(1).getId());
    }

}
