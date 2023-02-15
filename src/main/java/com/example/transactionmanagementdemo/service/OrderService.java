package com.example.transactionmanagementdemo.service;

import com.example.transactionmanagementdemo.dao.OrderDao;
import com.example.transactionmanagementdemo.dao.OrderItemDao;
import com.example.transactionmanagementdemo.dao.ProductDao;
import com.example.transactionmanagementdemo.domain.entity.Order;
import com.example.transactionmanagementdemo.domain.entity.OrderItem;
import com.example.transactionmanagementdemo.domain.entity.Product;
import com.example.transactionmanagementdemo.domain.request.CreateOrderRequest;
import com.example.transactionmanagementdemo.exception.NotEnoughInventoryException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class OrderService {
    private OrderDao orderDao;
    private ProductDao productDao;
    private OrderItemDao orderItemDao;

    @Autowired
    public OrderService(OrderDao orderDao,
                        ProductDao productDao,
                        OrderItemDao orderItemDao) {
        this.orderDao = orderDao;
        this.productDao = productDao;
        this.orderItemDao = orderItemDao;
    }


    @Transactional
    public List<Order> getAll(){
        return orderDao.getAll();
    }

    @Transactional
    public Order getById(int id){
        return orderDao.getById(id);
    }
    @Transactional
    public List<Order> getByUserId(int id){
        List<Order> orders =  orderDao.getAll();

        return orders.stream().filter(
                (order)->order.getUser().getId() == id).collect(Collectors.toList());

    }

    @Transactional
    public Order completeOrder(int id) {
        orderDao.completeOrder(id);
        return getById(id);
    }

    @Transactional
    public Order cancelOrder(int id) {
        orderDao.cancelOrder(id);
        return getById(id);
    }

    @Transactional
    public void createOrder(List<CreateOrderRequest> createOrderRequest,
                            Integer user_id) throws NotEnoughInventoryException {


        orderDao.createOrder(createOrderRequest, user_id) ;
    }


}
