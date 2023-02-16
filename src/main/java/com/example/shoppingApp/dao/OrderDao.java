package com.example.shoppingApp.dao;


import com.example.shoppingApp.domain.entity.Order;
import com.example.shoppingApp.domain.entity.OrderItem;
import com.example.shoppingApp.domain.entity.Product;
import com.example.shoppingApp.domain.entity.User;
import com.example.shoppingApp.domain.request.CreateOrderRequest;
import com.example.shoppingApp.exception.NotEnoughInventoryException;
import com.example.shoppingApp.exception.NotFoundException;
import com.example.shoppingApp.exception.OrderCancelFailedException;
import com.example.shoppingApp.exception.OrderCompleteFailedException;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.springframework.stereotype.Repository;

import java.sql.Timestamp;
import java.util.*;


@Repository
public class OrderDao extends GenericDao<Order> {

    public OrderDao() {
        entityClass = Order.class;
    }


    public void completeOrder(int id) {
        Session session = sessionFactory.openSession();
        Transaction tx = session.beginTransaction();
        Order order = session.get(Order.class, id);
        if(order == null){
            throw new NotFoundException(
                    String.format("Can not find object class: %s, with id: %d", Order.class, id));
        }

        if (Objects.equals(order.getOrder_status(), "processing")) {
            order.setOrder_status("completed");
        } else {
           throw new OrderCompleteFailedException(
                   String.format("Current order with id: %d, can not be complete" +
                           ", the current order status : %s is not PROCESSING", id,
                           order.getOrder_status().toUpperCase())
           );
        }
        System.out.println("Order after update: " + order);
        tx.commit();
        session.close();
    }

    public void cancelOrder(int id) {
        Session session = sessionFactory.openSession();
        Transaction tx = session.beginTransaction();
        Order order = session.get(Order.class, id);
        if(order == null){
            throw new NotFoundException(
                    String.format("Can not find object class: %s, with id: %d", Order.class, id));
        }

        if (!Objects.equals(order.getOrder_status(), "canceled") &&
                !Objects.equals(order.getOrder_status(), "completed")) {
            order.setOrder_status("canceled");
            order.getOrderItems().forEach((orderItem) -> {
                int purchased_quantity = orderItem.getPurchased_quantity();
                Product p = orderItem.getProduct();
                int curr_quantity = p.getStock_quantity();
                p.setStock_quantity(curr_quantity + purchased_quantity);
                session.saveOrUpdate(p);
            });
        } else{
            throw new OrderCancelFailedException(
                    String.format("Current order with id: %d, can not be cancelled, " +
                                    " the current order status : %s", id,
                            order.getOrder_status().toUpperCase())
            );

        }
        tx.commit();
        session.close();
    }

    public void createOrder(List<CreateOrderRequest> createOrderRequest,
                            Integer user_id) throws NotEnoughInventoryException{
        Session session = sessionFactory.openSession();
        Transaction tx = session.beginTransaction();


        User user = session.get(User.class, user_id);
        if(user == null){
            throw new NotFoundException(
                    String.format("Can not find object class: %d, with id: %d", User.class, user_id));
        }

        Order order = new Order();
        order.setOrder_status("processing");
        order.setDate_placed(new Timestamp(System.currentTimeMillis()));
        order.setUser(user);

        session.saveOrUpdate(order);
        Set<OrderItem> orderItems = new HashSet<>();

        // add item and product
        createOrderRequest.stream().forEach((request) -> {
            Product product = session.load(Product.class, request.getProduct_id());

            if (product.getStock_quantity() < request.getQuantity()) {
                throw new NotEnoughInventoryException("product quantity not enough for (productID: " +
                        request.getProduct_id() + ")" + " with purchase quantity: " + request.getQuantity());
            }
            OrderItem orderItem = OrderItem.builder()
                    .product(product)
                    .purchased_price(product.getRetail_price())
                    .purchased_quantity(request.getQuantity())
                    .order(order)
                    .wholesale_price(product.getWholesale_price())
                    .build();

            product.getOrderItems().add(orderItem);
            product.setStock_quantity(product.getStock_quantity() - request.getQuantity());
            orderItems.add(orderItem);
            session.saveOrUpdate(product);
        });
        order.setOrderItems(orderItems);
        session.saveOrUpdate(order);

        user.getOrders().add(order);
        session.saveOrUpdate(user);
        tx.commit();
        session.close();

    }


}
