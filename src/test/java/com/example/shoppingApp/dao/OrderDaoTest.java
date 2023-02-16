package com.example.shoppingApp.dao;

import com.example.shoppingApp.domain.entity.Product;
import com.example.shoppingApp.domain.entity.User;
import com.example.shoppingApp.exception.NotFoundException;
import com.example.shoppingApp.exception.OrderCancelFailedException;
import com.example.shoppingApp.exception.OrderCompleteFailedException;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.transaction.annotation.Transactional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

@SpringBootTest
@ActiveProfiles(value = "test") // uses application-test.properties
public class OrderDaoTest {

    @Autowired
    OrderDao orderDao;

    @Test
    @Transactional // include to avoid HibernateSystemException
    void test_complete_success() {
//        User expected = User.builder()
//                .id(1)
//                .username("anna")
//                .email("anna@mail.com")
//                .build();
//        userDao.add(expected);
//        userDao.getAll();
//        assertEquals(userDao.getById(1).getUsername(), "anna");
    }


    @Test
    @Transactional
    void test_complete_fail() {
        assertThrows(NotFoundException.class, () ->  orderDao.completeOrder(100));
    }

    @Test
    @Transactional
    void test_cancel_fail() {
        assertThrows(NotFoundException.class, () ->  orderDao.cancelOrder(100));
    }

    @Test
    @Transactional
    void test_cancel_fail_2() {
        assertThrows(OrderCancelFailedException.class, () ->  orderDao.cancelOrder(1));
    }

    @Test
    @Transactional
    void test_complete_fail_2() {
        assertThrows(OrderCompleteFailedException.class, () ->  orderDao.completeOrder(1));
    }
}
