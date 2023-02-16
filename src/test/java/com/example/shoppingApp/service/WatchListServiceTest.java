package com.example.shoppingApp.service;


import com.example.shoppingApp.dao.ProductDao;
import com.example.shoppingApp.dao.UserDao;
import com.example.shoppingApp.domain.entity.Product;
import com.example.shoppingApp.domain.entity.User;
import com.example.shoppingApp.exception.InvalidProductUpdateInfoException;
import com.example.shoppingApp.exception.NotFoundException;
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

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
public class WatchListServiceTest {
    private  Product p1;
    private  User u1;

    @Mock
    private ProductDao productDao;
    @Mock
    private UserDao userDao;

    @InjectMocks
    private WatchListService watchListService;

    @BeforeEach
     void init() {
        p1 = Product.builder()
                .id(1)
                .name("iPhone 11")
                .description("iPhone 11 512GB")
                .stock_quantity(10)
                .wholesale_price(30.0)
                .retail_price(20.0)

                .build();

        u1 = User.builder()
                .id(1)
                .username("anna")
                .email("anna@mail.com")
                .build();
    }


    @Test
    void test_getWatchListItemByUserId_success() {
        List<Product> products = new ArrayList<>();
        products.add(p1);

        List<User> users = new ArrayList<>();
        users.add(u1);
        u1.setProducts(products);

        Mockito.when(userDao.getById(1)).thenReturn(u1);

        assertEquals(1, watchListService.getWatchListItemByUserId(1).size());
    }

    @Test
    void test_addToWatchList_success() {
        List<Product> products = new ArrayList<>();
        products.add(p1);
        List<User> users = new ArrayList<>();
        users.add(u1);
        u1.setProducts(products);
        Mockito.when(userDao.getById(1)).thenReturn(u1);

        watchListService.addToWatchList(1, 1);
        verify(userDao).addToWatchList(any(),any());
    }

    @Test
    void test_deleteToWatchList_success() {
        List<Product> products = new ArrayList<>();
        products.add(p1);
        List<User> users = new ArrayList<>();
        users.add(u1);
        u1.setProducts(products);
        Mockito.when(userDao.getById(1)).thenReturn(u1);

        watchListService.deleteProductFromUserWatchList(1, 1);
        verify(userDao).deleteProduct(any(),any());
    }

    @Test
    void test_getPaginatedWatchlist() {
        List<Product> products = new ArrayList<>();
        products.add(p1);
        u1.setProducts(products);
        Mockito.when(userDao.getById(1)).thenReturn(u1);

        JSONObject ret1 =  watchListService.getWatchListItemByUserIdPaginated(1,1, 1);
        assertEquals(1, ret1.get("totalPages"));
        assertEquals(1, ret1.get("totalItems"));
        assertEquals(1, ret1.get("currentPage"));
        assertEquals(1, ret1.get("pageSize"));
    }

    @Test
    void test_findPaginated_success() {
        List<Product> products = new ArrayList<>();
        products.add(p1);
        products.add(p1);
        products.add(p1);
        products.add(p1);
        JSONObject ret =  watchListService.findPaginated(products, 1, 2);

        assertEquals(2, ret.get("totalPages"));
        assertEquals(4, ret.get("totalItems"));
        assertEquals(1, ret.get("currentPage"));
        assertEquals(2, ret.get("pageSize"));
    }

    @Test
    void test_findPaginated_fail() {
        assertThrows(RequestPageOverTotalPageException.class,
                ()-> watchListService.findPaginated(new ArrayList<>(), 2, 1));
    }
}
