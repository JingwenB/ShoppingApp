package com.example.shoppingApp.service;

import com.example.shoppingApp.dao.OrderDao;
import com.example.shoppingApp.dao.OrderItemDao;
import com.example.shoppingApp.dao.ProductDao;
import com.example.shoppingApp.domain.entity.Order;
import com.example.shoppingApp.domain.entity.Product;
import com.example.shoppingApp.domain.request.CreateOrderRequest;
import com.example.shoppingApp.exception.NotEnoughInventoryException;
import net.minidev.json.JSONObject;
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

    @Transactional
    public JSONObject getPaginatedOrder(int page, int size) {
        return findPaginated(getAll(), page, size);
    }


    public JSONObject findPaginated(List<Order> items, int page, int size) {
        int totalItems = items.size();
        int totalPages = (int) Math.floor((double)items.size() /(double)size);
        // 10/3 => 4 page, 0,1,2 | 3,4,5|6,7,8|9
        //               page 1
        if (page > totalPages){
            // throw error
        }
        JSONObject ret  = new JSONObject();
        ret.put("totalPages", totalPages);
        ret.put("totalItems", totalItems);
        ret.put("currentPage", page);
        ret.put("pageSize", size);
        List<Order> currentItems = items.subList(
                size * (page - 1), Math.min(size* page, items.size()) );
        ret.put("currentItems", currentItems);
        return ret;
    }

}
