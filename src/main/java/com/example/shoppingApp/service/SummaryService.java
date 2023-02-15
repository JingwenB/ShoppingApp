package com.example.shoppingApp.service;

import com.example.shoppingApp.dao.OrderDao;
import com.example.shoppingApp.dao.ProductDao;
import com.example.shoppingApp.dao.UserDao;
import com.example.shoppingApp.domain.entity.Order;
import com.example.shoppingApp.domain.entity.OrderItem;
import com.example.shoppingApp.domain.entity.Product;
import com.example.shoppingApp.domain.entity.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.Comparator;
import java.util.List;
import java.util.Objects;
import java.util.concurrent.atomic.AtomicReference;
import java.util.stream.Collectors;

@Service
public class SummaryService {
    private final String completed = "completed";

    private ProductDao productDao;
    private UserDao userDao;
    private OrderDao orderDao;

    @Autowired
    public SummaryService(ProductDao productDao,
                          UserDao userDao, OrderDao orderDao) {
        this.productDao = productDao;
        this.userDao = userDao;
        this.orderDao = orderDao;
    }



    @Transactional
    public List<User> topKSpentUser(int k){
        List<User> users = userDao.getAll();
        users.forEach(user -> {
            AtomicReference<Double> moneySpent = new AtomicReference<>(0.0);
            user.getOrders().forEach(order -> {
                if(Objects.equals(order.getOrder_status(), "completed")){
                    moneySpent.updateAndGet(v -> v + order.getOrderItems()
                            .stream()
                            .mapToDouble(orderItem ->
                                    orderItem.getPurchased_price() * orderItem.getPurchased_quantity())
                            .sum());
                }
            });
            user.setMoneySpent(moneySpent.get());
        });
        users.sort((a, b) -> (int) (b.getMoneySpent() - a.getMoneySpent()));
        return users.stream().limit(k).collect(Collectors.toList());
    }


    @Transactional
    public List<Product> topKProfitedProduct(int k){
        List<Product> products = productDao.getAll();
        products.forEach(p -> p.setProfit(p.getRetail_price() - p.getWholesale_price()));
        products.sort((a, b) -> (int) (b.getProfit() - a.getProfit()));

        return products.stream().limit(k).collect(Collectors.toList());
    }

    @Transactional
    public List<Product> topKPurchasedProduct(int k){
        List<Product> products = productDao.getAll();
        products.forEach(p -> {
            int quantity = p.getOrderItems()
                    .stream()
                    .filter(orderItem ->
                            Objects.equals(orderItem.getOrder().getOrder_status(), "completed"))
                    .mapToInt(OrderItem::getPurchased_quantity).sum();
            p.setSold_quantity(quantity);
        });
        products.sort((a, b) -> (int) (b.getSold_quantity() - a.getSold_quantity()));

        return products.stream().limit(k).collect(Collectors.toList());
    }

    @Transactional
    public List<Product> topKPurchasedProductByUser(int user_id,
                                                    int k){
        List<Product> products = productDao.getAll();
        products.forEach(p -> {
            int quantity = p.getOrderItems()
                    .stream()
                    .filter(orderItem ->
                            Objects.equals(orderItem.getOrder().getOrder_status(), "completed")
                            && orderItem.getOrder().getUser().getId() == user_id
                    ).mapToInt(OrderItem::getPurchased_quantity).sum();
            p.setSold_quantity(quantity);
        });

        products.sort((a, b) -> (int) (b.getSold_quantity() - a.getSold_quantity()));

        return products.stream().limit(k).collect(Collectors.toList());
    }

    @Transactional
    public int totalQuantitySoldSofar(){
        List<Product> products = productDao.getAll();
        products.forEach(p -> {
            int quantity = p.getOrderItems()
                    .stream()
                    .filter(orderItem ->
                            Objects.equals(orderItem.getOrder().getOrder_status(), "completed"))
                    .mapToInt(OrderItem::getPurchased_quantity).sum();
            p.setSold_quantity(quantity);
        });

        return products.stream().mapToInt(Product::getSold_quantity).sum();
    }


    @Transactional
    public List<Product> mostKRecentPurchaseByUser( int user_id, int k){

        List<Order> orders = orderDao.getAll()
                .stream()
                .filter(order -> order.getUser().getId() == user_id && Objects.equals(order.getOrder_status(), "completed"))
                .sorted((a,b)-> b.getDate_placed().compareTo(a.getDate_placed()))
                .collect(Collectors.toList());

        List<Product> products = orders
                .stream()
                .flatMap(order -> order.getOrderItems().stream().map(OrderItem::getProduct).sorted(Comparator.comparingInt(Product::getId)))

                .distinct()
                .limit(k)
                .collect(Collectors.toList());

        return products;
    }






}
