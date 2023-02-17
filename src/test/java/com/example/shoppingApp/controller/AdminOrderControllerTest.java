package com.example.shoppingApp.controller;

import com.example.shoppingApp.domain.entity.Order;
import com.example.shoppingApp.domain.entity.Product;
import com.example.shoppingApp.service.OrderService;
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
public class AdminOrderControllerTest {
    private MockMvc mockMvc;

    @Mock
    private OrderService orderService;

    @InjectMocks
    private AdminOrderController adminOrderController;

    @BeforeEach
    void init() {
        MockitoAnnotations.initMocks(this);
        mockMvc = MockMvcBuilders.standaloneSetup(adminOrderController).build();
    }

    @Test
    void completeOrder() throws Exception {
        Mockito.when(orderService.completeOrder(1)).thenReturn(new Order());
        MockHttpServletRequestBuilder request = MockMvcRequestBuilders
                .put("/admin/order/complete/{order_id}", 1)
                .contentType(MediaType.APPLICATION_JSON);

        ResultActions perform = mockMvc.perform(request);

        perform.andExpect(MockMvcResultMatchers.status().isOk());

        MvcResult mvcResult = perform.andReturn();
        MockHttpServletResponse response = mvcResult.getResponse();

        log.info("response: " + response.getContentAsString());
        assertEquals("{\"data\":{\"order\":null},\"message\":\"Updated order Id:1 to complete\",\"status\":200}",
                response.getContentAsString());

    }

    @Test
    void cancelOrder() throws Exception {
        Mockito.when(orderService.cancelOrder(1)).thenReturn(new Order());

        MockHttpServletRequestBuilder request = MockMvcRequestBuilders
                .put("/admin/order/cancel/{order_id}" , 1)
                .contentType(MediaType.APPLICATION_JSON);

        ResultActions perform = mockMvc.perform(request);

        perform.andExpect(MockMvcResultMatchers.status().isOk());

        MvcResult mvcResult = perform.andReturn();
        MockHttpServletResponse response = mvcResult.getResponse();

        log.info("response: " + response.getContentAsString());
        assertEquals("{\"data\":{\"order\":null},\"message\":\"Updated order Id:1 to complete\",\"status\":200}",
                response.getContentAsString());

    }


}
