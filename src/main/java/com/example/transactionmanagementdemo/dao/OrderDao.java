package com.example.transactionmanagementdemo.dao;


import com.example.transactionmanagementdemo.domain.entity.Order;
import com.example.transactionmanagementdemo.domain.entity.Product;
import com.example.transactionmanagementdemo.domain.entity.User;
import com.example.transactionmanagementdemo.exception.UserGetFailedException;
import com.example.transactionmanagementdemo.exception.UserSaveFailedException;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.springframework.stereotype.Repository;

import java.util.Objects;


@Repository
public class OrderDao extends GenericDao<Order> {

    public OrderDao() {
        entityClass = Order.class;
    }


    public void completeOrder(int id) {
        Session session = sessionFactory.openSession();
        Transaction tx = session.beginTransaction();
        Order order = session.load(Order.class, id);

       if (Objects.equals(order.getOrder_status(), "processing")){
           order.setOrder_status("completed");
       } else {
//           throw new OrderUpdateFailedException();
       }
        System.out.println("Order after update: "+ order);
        tx.commit();
        session.close();
    }

    public void cancelOrder(int id) {
        Session session = sessionFactory.openSession();
        Transaction tx = session.beginTransaction();
        Order order = session.load(Order.class, id);

        if (!Objects.equals(order.getOrder_status(), "canceled") &&
                !Objects.equals(order.getOrder_status(), "completed")){
            order.setOrder_status("canceled");
        }
        System.out.println("Order after update: "+ order);
        tx.commit();
        session.close();
    }
}
