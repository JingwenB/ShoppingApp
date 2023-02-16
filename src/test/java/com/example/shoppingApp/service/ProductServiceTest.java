package com.example.shoppingApp.service;


import com.example.shoppingApp.dao.ProductDao;
import com.example.shoppingApp.dao.UserDao;
import com.example.shoppingApp.domain.entity.Product;
import com.example.shoppingApp.domain.entity.User;
import com.example.shoppingApp.exception.InvalidProductUpdateInfoException;
import com.example.shoppingApp.exception.NotFoundException;
import com.example.shoppingApp.exception.RequestPageOverTotalPageException;
import net.minidev.json.JSONObject;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
public class ProductServiceTest {
    private  Product p1;
    private  User u1;

    @Mock
    private ProductDao productDao;
    @Mock
    private UserDao userDao;

    @InjectMocks
    private ProductService productService;

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
    void test_getAll_success() {

        List<Product> products = new ArrayList<>();
        products.add(p1);
        Mockito.when(productDao.getAll()).thenReturn(products);
        assertEquals(products, productService.getAll());
    }

    @Test
    void test_getById_success() {

        List<Product> products = new ArrayList<>();
        products.add(p1);

        Mockito.when(productDao.getById(1)).thenReturn(p1);
        assertEquals(p1, productService.getById(1));
    }

    @Test
    void test_getById_fail() {
        Mockito.when(productDao.getById(-1)).thenThrow(NotFoundException.class);
        assertThrows(NotFoundException.class, ()-> productService.getById(-1));
    }

    @Test
    void test_getByIdAsUser_success() {
        Mockito.when(productDao.getById(1)).thenReturn(p1);

        assertNull(productService.getByIdAsUser(1).getStock_quantity());
        assertNull(productService.getByIdAsUser(1).getWholesale_price());
    }

    @Test
    void test_getAllAsUser_success() {
        List<Product> products = new ArrayList<>();
        products.add(p1);

        Mockito.when(productDao.getAll()).thenReturn(products);

        List<Product> actual = productService.getAllAsUser();

        assertNull(actual.get(0).getStock_quantity());
        assertNull(actual.get(0).getWholesale_price());
    }

    @Test
    void test_create_success() {
        List<Product> products = new ArrayList<>();
        products.add(p1);
        Mockito.when(productDao.getAll()).thenReturn(products);
        assertEquals(products, productService.createProduct(p1));
    }
    @Test
    void test_update_success() {
        Mockito.when(productDao.getById(1)).thenReturn(p1);
        assertEquals(p1, productService.update(1, p1));
    }
    @Test
    void test_update_fail() {
        Product p2 = Product.builder()
                .stock_quantity(-1)
                .build();
        assertThrows(InvalidProductUpdateInfoException.class, ()-> productService.update(1, p2));
    }

    @Test
    void test_getPaginatedProduct() {
        List<Product> products = new ArrayList<>();
        products.add(p1);
        products.add(p1);
        products.add(p1);
        products.add(p1);


        Mockito.when(productDao.getAll()).thenReturn(products);
//        Mockito.when(productService.getAllAsUser()).thenReturn(products);
        JSONObject ret1 =  productService.getPaginatedProduct(1, 2);
        assertEquals(2, ret1.get("totalPages"));
        assertEquals(4, ret1.get("totalItems"));
        assertEquals(1, ret1.get("currentPage"));
        assertEquals(2, ret1.get("pageSize"));
    }

    @Test
    void test_findPaginated_success() {
        List<Product> products = new ArrayList<>();
        products.add(p1);
        products.add(p1);
        products.add(p1);
        products.add(p1);
        JSONObject ret =  productService.findPaginated(products, 1, 2);

        assertEquals(2, ret.get("totalPages"));
        assertEquals(4, ret.get("totalItems"));
        assertEquals(1, ret.get("currentPage"));
        assertEquals(2, ret.get("pageSize"));
    }

    @Test
    void test_findPaginated_fail() {
        assertThrows(RequestPageOverTotalPageException.class,
                ()-> productService.findPaginated(new ArrayList<>(), 2, 1));
    }
}
