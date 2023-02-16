package com.example.shoppingApp.dao;

import com.example.shoppingApp.domain.entity.Product;
import com.example.shoppingApp.exception.NotFoundException;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.transaction.annotation.Transactional;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@ActiveProfiles(value = "test") // uses application-test.properties
public class ProductDaoTest {

    @Autowired
    ProductDao productDao;

    @Test
    @Transactional // include to avoid HibernateSystemException
    void test_getById_success() {
        Product expected  = Product.builder()
                .id(1)
                .name("iPhone 11")
                .description("iPhone 11 512GB")
                .stock_quantity(10)
                .wholesale_price(30.0)
                .retail_price(20.0)
                .build();

        productDao.add(expected);

        assertEquals(productDao.getById(1).getName(), "iPhone 11");
    }

    @Test
    @Transactional
    void test_getById_fail() {
        assertThrows(NotFoundException.class, () ->  productDao.getById(-1));
    }

    @Test
    @Transactional
    void test_update_success() {
        Product p1 =Product.builder()
                .id(1)
                .name("iPhone11")
                .description("iPhone 11 512GB")
                .stock_quantity(10)
                .wholesale_price(30.0)
                .retail_price(20.0)
                .build();

        Product productToUpdate = Product.builder()
                .description("new description")
                .stock_quantity(10)
                .wholesale_price(20.0)
                .retail_price(30.0)
                .build();

        productDao.update(1, productToUpdate);
        assertEquals(productDao.getById(1).getDescription() , productToUpdate.getDescription());
        assertEquals(productDao.getById(1).getStock_quantity() , productToUpdate.getStock_quantity());
//        assertEquals(productDao.getById(1).getWholesale_price() , productToUpdate.getWholesale_price());
//        assertEquals(productDao.getById(1).getRetail_price() , productToUpdate.getRetail_price());
        productDao.update(1, p1);
    }

    @Test
    @Transactional
    void test_update_fail() {
        assertThrows(NotFoundException.class, () ->  productDao.update(-1,Product.builder().build()));
    }

}
