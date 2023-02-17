package com.example.shoppingApp.controller;
import static org.springframework.security.test.web.servlet.setup.SecurityMockMvcConfigurers.*;

import com.example.shoppingApp.domain.entity.Product;
import com.example.shoppingApp.security.JwtProvider;
import com.example.shoppingApp.service.ProductService;
import com.google.gson.Gson;
import lombok.extern.slf4j.Slf4j;
import net.minidev.json.JSONObject;
import org.junit.Before;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.context.WebApplicationContext;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

//@WebMvcTest(controllers = AdminProductController.class)
@SpringBootTest()
@Slf4j
public class AdminProductControllerTest {
    private MockMvc mockMvc;

    private  Product p1;
    private List<Product> productList;

    @Mock
    private ProductService productService;

    @InjectMocks
    private AdminProductController productController;


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
        productList = new ArrayList<>();

        MockitoAnnotations.initMocks(this);//这句话执行以后，service自动注入到controller中。

        mockMvc = MockMvcBuilders.standaloneSetup(productController).build();
    }

    @Test
    void getAllProduct() throws Exception {
        Mockito.when(productService.getAll()).thenReturn(productList);

        MockHttpServletRequestBuilder request = MockMvcRequestBuilders.get("/admin/product/all")
                .contentType(MediaType.APPLICATION_JSON);

        ResultActions perform = mockMvc.perform(request);

        perform.andExpect(MockMvcResultMatchers.status().isOk());

        MvcResult mvcResult = perform.andReturn();
        MockHttpServletResponse response = mvcResult.getResponse();

        log.info("response: " + response.getContentAsString());
        assertEquals("{\"data\":[],\"message\":\"returning all products without pagination\",\"status\":200}",
                response.getContentAsString());

    }

    @Test
    void getAllProductPaginated() throws Exception {
        Mockito.when(productService.getPaginatedProduct(1,1)).thenReturn(new JSONObject());

        MultiValueMap<String, String> params = new LinkedMultiValueMap<>();
        params.add("page", "1");
        params.add("size", "1");

        MockHttpServletRequestBuilder request =
                MockMvcRequestBuilders
                        .get("/admin/product/all")
                        .params(params)
                        .contentType(MediaType.APPLICATION_JSON);

        ResultActions perform = mockMvc.perform(request);

        perform.andExpect(MockMvcResultMatchers.status().isOk());

        MvcResult mvcResult = perform.andReturn();
        MockHttpServletResponse response = mvcResult.getResponse();

        log.info("response: " + response.getContentAsString());
        assertEquals("{\"data\":null,\"message\":\"returning products with pagination\",\"status\":200}",
                response.getContentAsString());

    }

    @Test
    void getProductById() throws Exception {
        Mockito.when(productService.getById(1)).thenReturn(new Product());

        MultiValueMap<String, String> params = new LinkedMultiValueMap<>();
        params.add("product_id", "1");

        MockHttpServletRequestBuilder request =
                MockMvcRequestBuilders
                        .get("/admin/product/detail")
                        .params(params)
                        .contentType(MediaType.APPLICATION_JSON);

        ResultActions perform = mockMvc.perform(request);

        perform.andExpect(MockMvcResultMatchers.status().isOk());

        MvcResult mvcResult = perform.andReturn();
        MockHttpServletResponse response = mvcResult.getResponse();

        log.info("response: " + response.getContentAsString());
        assertEquals("{\"data\":null,\"message\":\"Returning product with id: 1\",\"status\":200}",
                response.getContentAsString());

    }

    @Test
    void saveProduct() throws Exception {
        Product p = new Product();
        Gson gson = new Gson();
        String expectedJson = gson.toJson(p);

        Mockito.when(productService.createProduct(Mockito.isA(Product.class))).thenReturn(new ArrayList<>());

        MockHttpServletRequestBuilder request =
                MockMvcRequestBuilders
                        .put("/admin/product/create")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(expectedJson);

        ResultActions perform = mockMvc.perform(request);

        perform.andExpect(MockMvcResultMatchers.status().isOk());

        MvcResult mvcResult = perform.andReturn();
        MockHttpServletResponse response = mvcResult.getResponse();

        log.info("response: " + response.getContentAsString());
        assertEquals("{\"data\":[],\"message\":\"New Product saved, retrieving all products\",\"status\":200}",
                response.getContentAsString());

    }

    @Test
    void updateProduct() throws Exception {
        Product p = new Product();
        Gson gson = new Gson();
        String expectedJson = gson.toJson(p);

        MockHttpServletRequestBuilder request =
                MockMvcRequestBuilders
                        .put("/admin/product/update/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(expectedJson);

        ResultActions perform = mockMvc.perform(request);

        perform.andExpect(MockMvcResultMatchers.status().isOk());

        MvcResult mvcResult = perform.andReturn();
        MockHttpServletResponse response = mvcResult.getResponse();

        log.info("response: " + response.getContentAsString());
        assertEquals("{\"data\":null,\"message\":\"Product updated, retrieving updated product\",\"status\":200}",
                response.getContentAsString());

    }



}
