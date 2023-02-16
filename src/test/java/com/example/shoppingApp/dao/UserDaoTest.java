package com.example.shoppingApp.dao;

import com.example.shoppingApp.domain.entity.Product;
import com.example.shoppingApp.domain.entity.User;
import com.example.shoppingApp.exception.NotFoundException;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.transaction.annotation.Transactional;


import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@ActiveProfiles(value = "test") // uses application-test.properties
public class UserDaoTest {

    @Autowired
    UserDao userDao;

    @Test
    @Transactional // include to avoid HibernateSystemException
    void test_getById_success() {
        User expected = User.builder()
                .id(1)
                .username("anna")
                .email("anna@mail.com")
                .build();
        userDao.add(expected);
        userDao.getAll();
        assertEquals(userDao.getById(1).getUsername(), "anna");
    }

    @Test
    @Transactional
    void test_getById_fail() {
        assertThrows(NotFoundException.class, () -> userDao.getById(-1));
    }

    @Test
    @Transactional
    void test_addProduct_() {
        try{
            userDao.deleteProduct(1,2);
        } catch (Exception e) {
//            e.printStackTrace();
        }
        User user = User.builder()
                .id(1)
                .username("anna")
                .email("anna@mail.com")
                .build();

        userDao.add(user);
        userDao.addToWatchList(1,2);
//
//        assertTrue(user.getProducts().stream().map(Product::getId)
//                .collect(Collectors.toList()).contains(2));
    }

    @Test
    @Transactional
    void test_addWatchList_fail() {
        assertThrows(NotFoundException.class, () ->  userDao.addToWatchList(-1,-1));
    }

    @Test
    @Transactional
    void test_deleteWatchList_fail() {
        assertThrows(NotFoundException.class, () ->  userDao.addToWatchList(-1,-1));
    }

    @Test
    @Transactional
    void test_addWatchList_fail_2() {
        assertThrows(NotFoundException.class, () ->  userDao.addToWatchList(1,-1));
    }

    @Test
    @Transactional
    void test_deleteWatchList_fail_2() {
        assertThrows(NotFoundException.class, () ->  userDao.addToWatchList(1,-1));
    }

}
