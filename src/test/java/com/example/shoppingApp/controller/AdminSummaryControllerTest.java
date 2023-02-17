package com.example.shoppingApp.controller;

import com.example.shoppingApp.domain.entity.Order;
import com.example.shoppingApp.domain.entity.OrderItem;
import com.example.shoppingApp.domain.entity.Product;
import com.example.shoppingApp.domain.entity.User;
import com.example.shoppingApp.service.OrderService;
import com.example.shoppingApp.service.ProductService;
import com.example.shoppingApp.service.SummaryService;
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
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.assertEquals;

//@WebMvcTest(controllers = AdminProductController.class)
@SpringBootTest()
@Slf4j
public class AdminSummaryControllerTest {
//    @MockBean
//    private ProductService productService;
//    @MockBean
//    private JwtProvider jwtProvider;

//    @Autowired
    private MockMvc mockMvc;

    private  Product p1;
    private List<Product> productList;
    private List<Order> orderList;
    private List<User> userList;

    @Mock
    private ProductService productService;
    @Mock
    private SummaryService summaryService;

    @Mock
    private OrderService orderService;

    @InjectMocks
    private AdminSummaryController adminSummaryController;


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
        orderList = new ArrayList<>();
        userList = new ArrayList<>();
        MockitoAnnotations.initMocks(this);//这句话执行以后，service自动注入到controller中。

        // (1)构建mvc环境
        mockMvc = MockMvcBuilders.standaloneSetup(adminSummaryController).build();
    }

    @Test
    void getAllOrder() throws Exception {
        Mockito.when(orderService.getAll()).thenReturn(orderList);

        MockHttpServletRequestBuilder request =
                MockMvcRequestBuilders
                        .get("/admin/summary/order/all")
                        .contentType(MediaType.APPLICATION_JSON);

        ResultActions perform = mockMvc.perform(request);

        perform.andExpect(MockMvcResultMatchers.status().isOk());

        MvcResult mvcResult = perform.andReturn();
        MockHttpServletResponse response = mvcResult.getResponse();

        log.info("response: " + response.getContentAsString());
        assertEquals(response.getContentAsString(),
                "{\"data\":{\"all orders\":[]},\"message\":\"get all orders for admin\",\"status\":200}");

    }

    @Test
    void getAllOrderPaginated() throws Exception {
        Mockito.when(orderService.getPaginatedOrder(1,1)).thenReturn(new JSONObject());
        MultiValueMap<String, String> params = new LinkedMultiValueMap<>();
        params.add("page", "1");
        params.add("size", "1");

        MockHttpServletRequestBuilder request =
                MockMvcRequestBuilders
                        .get("/admin/summary/order/all")
                        .params(params)
                        .contentType(MediaType.APPLICATION_JSON);

        ResultActions perform = mockMvc.perform(request);

        perform.andExpect(MockMvcResultMatchers.status().isOk());

        MvcResult mvcResult = perform.andReturn();
        MockHttpServletResponse response = mvcResult.getResponse();

        log.info("response: " + response.getContentAsString());
        assertEquals(
                "{\"data\":null,\"message\":\"returning all order with pagination\",\"status\":200}",response.getContentAsString());

    }

    @Test
    void getOrderByUserId() throws Exception {
        Mockito.when(orderService.getByUserId(1)).thenReturn(orderList);
        MultiValueMap<String, String> params = new LinkedMultiValueMap<>();
        params.add("user_id", "1");

        MockHttpServletRequestBuilder request =
                MockMvcRequestBuilders
                        .get("/admin/summary/order/user")
                        .params(params)
                        .contentType(MediaType.APPLICATION_JSON);

        ResultActions perform = mockMvc.perform(request);

        perform.andExpect(MockMvcResultMatchers.status().isOk());

        MvcResult mvcResult = perform.andReturn();
        MockHttpServletResponse response = mvcResult.getResponse();

        log.info("response: " + response.getContentAsString());
        assertEquals(
                "{\"data\":{\"orders\":[]},\"message\":\"get all orders for user: 1 for admin preview\",\"status\":200}",
                response.getContentAsString());

    }

    @Test
    void getByUserIdPaginated() throws Exception {
        Mockito.when(orderService.getByUserIdPaginated(1,1,1)).thenReturn(new JSONObject());
        MultiValueMap<String, String> params = new LinkedMultiValueMap<>();
        params.add("user_id", "1");
        params.add("page", "1");
        params.add("size", "1");

        MockHttpServletRequestBuilder request =
                MockMvcRequestBuilders
                        .get("/admin/summary/order/user")
                        .params(params)
                        .contentType(MediaType.APPLICATION_JSON);

        ResultActions perform = mockMvc.perform(request);

        perform.andExpect(MockMvcResultMatchers.status().isOk());

        MvcResult mvcResult = perform.andReturn();
        MockHttpServletResponse response = mvcResult.getResponse();

        log.info("response: " + response.getContentAsString());
        assertEquals(
                "{\"data\":null,\"message\":\"get all orders for user: 1 for admin preview\",\"status\":200}",
                response.getContentAsString());

    }




    @Test
    void getTopKPurchasedProduct() throws Exception {
        Mockito.when(summaryService.topKPurchasedProduct(2)).thenReturn(productList);

        MultiValueMap<String, String> params = new LinkedMultiValueMap<>();
        params.add("num", "2");

        MockHttpServletRequestBuilder request =
                MockMvcRequestBuilders
                        .get("/admin/summary/product/topSold")
                        .params(params)
                        .contentType(MediaType.APPLICATION_JSON);

        ResultActions perform = mockMvc.perform(request);

        perform.andExpect(MockMvcResultMatchers.status().isOk());

        MvcResult mvcResult = perform.andReturn();
        MockHttpServletResponse response = mvcResult.getResponse();

        log.info("response: " + response.getContentAsString());
        assertEquals(response.getContentAsString(),
                "{\"data\":{\"products\":[]},\"message\":\"return top 2 frequent purchased product: \",\"status\":200}");

    }

    @Test
    void getTopKProfitedProduct() throws Exception {
        Mockito.when(summaryService.topKProfitedProduct(2)).thenReturn(productList);

        MultiValueMap<String, String> params = new LinkedMultiValueMap<>();
        params.add("num", "2");

        MockHttpServletRequestBuilder request =
                MockMvcRequestBuilders
                        .get("/admin/summary/product/topProfit")
                        .params(params)
                        .contentType(MediaType.APPLICATION_JSON);

        ResultActions perform = mockMvc.perform(request);

        perform.andExpect(MockMvcResultMatchers.status().isOk());

        MvcResult mvcResult = perform.andReturn();
        MockHttpServletResponse response = mvcResult.getResponse();

        log.info("response: " + response.getContentAsString());
        assertEquals(
                "{\"data\":{\"products\":[]},\"message\":\"return top 2 most profits product: \",\"status\":200}",
                response.getContentAsString());

    }

    @Test
    void getTopKSpentUser() throws Exception {
        Mockito.when(summaryService.topKSpentUser(2)).thenReturn(userList);

        MultiValueMap<String, String> params = new LinkedMultiValueMap<>();
        params.add("num", "2");

        MockHttpServletRequestBuilder request =
                MockMvcRequestBuilders
                        .get("/admin/summary/user/topSpent")
                        .params(params)
                        .contentType(MediaType.APPLICATION_JSON);

        ResultActions perform = mockMvc.perform(request);

        perform.andExpect(MockMvcResultMatchers.status().isOk());

        MvcResult mvcResult = perform.andReturn();
        MockHttpServletResponse response = mvcResult.getResponse();

        log.info("response: " + response.getContentAsString());
        assertEquals(
                "{\"data\":{\"users\":[]},\"message\":\"getting top 2 money spent user\",\"status\":200}",
                response.getContentAsString());

    }

    @Test
    void getAllSuccessSoldItem() throws Exception {
        Mockito.when(summaryService.totalQuantitySoldSofar()).thenReturn(1);

        MockHttpServletRequestBuilder request =
                MockMvcRequestBuilders
                        .get("/admin/summary/product/count_all")
                        .contentType(MediaType.APPLICATION_JSON);

        ResultActions perform = mockMvc.perform(request);

        perform.andExpect(MockMvcResultMatchers.status().isOk());

        MvcResult mvcResult = perform.andReturn();
        MockHttpServletResponse response = mvcResult.getResponse();

        log.info("response: " + response.getContentAsString());
        assertEquals(
                "{\"data\":{\"overall_quantity\":0},\"message\":\"getting overall sold items quantity\",\"status\":200}",
                response.getContentAsString());

    }


}
