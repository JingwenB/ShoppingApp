package com.example.shoppingApp.service;


import com.example.shoppingApp.dao.OrderDao;
import com.example.shoppingApp.dao.ProductDao;
import com.example.shoppingApp.dao.UserDao;
import com.example.shoppingApp.domain.entity.Order;
import com.example.shoppingApp.domain.entity.Product;
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
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class SummaryServiceTest {
    private Order o1;
    private Product p1;

    @Mock
    private OrderDao orderDao;
    @Mock
    private ProductDao productDao;
    @Mock
    private UserDao userDao;

    @InjectMocks
    private SummaryService summaryService;

    @BeforeEach
     void init() {
        o1 = Order.builder().build();
        p1 = Product.builder()
                .retail_price(20.0)
                .wholesale_price(10.0)
                .build();

    }


    @Test
    void test_topKSpentUser_success() {
        summaryService.topKSpentUser(1);
        verify(userDao, times(1)).getAll();
    }

    @Test
    void test_topKProfitedProduct_success() {
        List<Product> products = new ArrayList<>();
        products.add(p1);
        when(productDao.getAll()).thenReturn(products);
        List<Product> ret = summaryService.topKProfitedProduct(1);

        verify(productDao, times(1)).getAll();
        assertEquals(ret, products);
    }

    @Test
    void test_topKPurchasedProduct_success() {

        List<Product> ret = summaryService.topKPurchasedProduct(1);
        verify(productDao, times(1)).getAll();
    }

    @Test
    void test_topKPurchasedProductByUser_success() {
        summaryService.topKPurchasedProductByUser(1, 1);
        verify(productDao, times(1)).getAll();
    }

    @Test
    void test_totalQuantitySoldSofar_success() {
        summaryService.totalQuantitySoldSofar();
        verify(productDao, times(1)).getAll();
    }

    @Test
    void test_mostKRecentPurchaseByUser_success() {
        summaryService.mostKRecentPurchaseByUser(1, 1);
        verify(orderDao, times(1)).getAll();
    }


}
