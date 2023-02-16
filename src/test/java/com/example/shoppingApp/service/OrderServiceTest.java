package com.example.shoppingApp.service;


import com.example.shoppingApp.dao.OrderDao;
import com.example.shoppingApp.domain.entity.Order;
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
public class OrderServiceTest {
    private Order o1;

    @Mock
    private OrderDao orderDao;

    @InjectMocks
    private OrderService orderService;

    @BeforeEach
     void init() {
        o1 = Order.builder()
                .build();

    }


    @Test
    void test_getAll_success() {
        List<Order> orders = new ArrayList<>();
        orders.add(o1);

        Mockito.when(orderDao.getAll()).thenReturn(orders);
        assertEquals(orders, orderService.getAll());
    }

    @Test
    void test_getById_success() {

        Mockito.when(orderDao.getById(1)).thenReturn(o1);
        assertEquals(o1, orderService.getById(1));
    }
    @Test
    void test_getByUserId_success() {
        orderService.getByUserId(1);
        verify(orderDao, times(1)).getAll();
    }

    @Test
    void test_complete_success() {

        orderService.completeOrder(1);
        verify(orderDao, times(1)).completeOrder(1);
    }
    @Test
    void test_cancel_success() {
        orderService.cancelOrder(1);
        verify(orderDao, times(1)).cancelOrder(1);
    }

    @Test
    void test_create_success() {
        orderService.createOrder(any(),any());
        verify(orderDao, times(1)).createOrder(any(), any());
    }

    @Test
    void test_getPaginatedWatchlist() {
        List<Order> orders = new ArrayList<>();
        orders.add(o1);
        Mockito.when(orderDao.getAll()).thenReturn(orders);

        JSONObject ret1 =  orderService.getPaginatedOrder(1,1);
        assertEquals(1, ret1.get("totalPages"));
        assertEquals(1, ret1.get("totalItems"));
        assertEquals(1, ret1.get("currentPage"));
        assertEquals(1, ret1.get("pageSize"));
    }

    @Test
    void test_findPaginated_success() {
        List<Order> orders = new ArrayList<>();
        orders.add(o1);
        orders.add(o1);
        orders.add(o1);
        orders.add(o1);
        JSONObject ret =  orderService.findPaginated(orders, 1, 2);

        assertEquals(2, ret.get("totalPages"));
        assertEquals(4, ret.get("totalItems"));
        assertEquals(1, ret.get("currentPage"));
        assertEquals(2, ret.get("pageSize"));
    }

    @Test
    void test_findPaginated_fail() {
        assertThrows(RequestPageOverTotalPageException.class,
                ()-> orderService.findPaginated(new ArrayList<>(), 2, 1));
    }
}
