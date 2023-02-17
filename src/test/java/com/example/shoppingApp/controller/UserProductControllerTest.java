package com.example.shoppingApp.controller;

import com.example.shoppingApp.domain.entity.Product;
import com.example.shoppingApp.service.ProductService;
import com.google.gson.Gson;
import lombok.extern.slf4j.Slf4j;
import net.minidev.json.JSONObject;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.context.SpringBootTest;
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

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

//@WebMvcTest(controllers = AdminProductController.class)
@SpringBootTest()
@Slf4j
public class UserProductControllerTest {
    private MockMvc mockMvc;


    @Mock
    private ProductService productService;

    @InjectMocks
    private UserProductController productController;


    @BeforeEach
    void init() {

        MockitoAnnotations.initMocks(this);

        mockMvc = MockMvcBuilders.standaloneSetup(productController).build();
    }

    @Test
    void getAllProduct() throws Exception {
        Mockito.when(productService.getAllAsUser()).thenReturn(new ArrayList<>());

        MockHttpServletRequestBuilder request =
                MockMvcRequestBuilders
                        .get("/user/product/all")
                        .contentType(MediaType.APPLICATION_JSON);

        ResultActions perform = mockMvc.perform(request);

        perform.andExpect(MockMvcResultMatchers.status().isOk());

        MvcResult mvcResult = perform.andReturn();
        MockHttpServletResponse response = mvcResult.getResponse();

        log.info("response: " + response.getContentAsString());
        assertEquals("{\"data\":[],\"message\":\"returning all in-stock products without pagination\",\"status\":200}",
                response.getContentAsString());

    }

    @Test
    void getAllProductPaginated() throws Exception {
        Mockito.when(productService.getPaginatedProductAsUser(1,1)).thenReturn(new JSONObject());

        MultiValueMap<String, String> params = new LinkedMultiValueMap<>();
        params.add("page", "1");
        params.add("size", "1");

        MockHttpServletRequestBuilder request =
                MockMvcRequestBuilders
                        .get("/user/product/all")
                        .params(params)
                        .contentType(MediaType.APPLICATION_JSON);

        ResultActions perform = mockMvc.perform(request);

        perform.andExpect(MockMvcResultMatchers.status().isOk());

        MvcResult mvcResult = perform.andReturn();
        MockHttpServletResponse response = mvcResult.getResponse();

        log.info("response: " + response.getContentAsString());
        assertEquals("{\"data\":null,\"message\":\"returning all in-stock products with pagination\",\"status\":200}",
                response.getContentAsString());

    }

    @Test
    void getProductById() throws Exception {
        Mockito.when(productService.getByIdAsUser(1)).thenReturn(new Product());

        MockHttpServletRequestBuilder request =
                MockMvcRequestBuilders
                        .get("/user/product/product_id/{product_id}", 1)
                        .contentType(MediaType.APPLICATION_JSON);

        ResultActions perform = mockMvc.perform(request);

        perform.andExpect(MockMvcResultMatchers.status().isOk());

        MvcResult mvcResult = perform.andReturn();
        MockHttpServletResponse response = mvcResult.getResponse();

        log.info("response: " + response.getContentAsString());
        assertEquals("{\"data\":null,\"message\":\"Returning product (id:1) detail\",\"status\":200}",
                response.getContentAsString());

    }



}
