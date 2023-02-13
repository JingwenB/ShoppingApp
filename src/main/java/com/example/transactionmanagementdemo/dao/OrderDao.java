package com.example.transactionmanagementdemo.dao;


import com.example.transactionmanagementdemo.domain.entity.Order;
import com.example.transactionmanagementdemo.domain.entity.User;
import com.example.transactionmanagementdemo.exception.UserGetFailedException;
import com.example.transactionmanagementdemo.exception.UserSaveFailedException;
import org.hibernate.Session;
import org.springframework.stereotype.Repository;


@Repository
public class OrderDao extends GenericDao {

    public OrderDao() {
        entityClass = Order.class;
    }

    public void add(Order entity) throws UserSaveFailedException {
        Session session;
        try{
            session = sessionFactory.getCurrentSession();
            session.saveOrUpdate(entity);
        }
        catch(Exception e){
            throw new UserSaveFailedException("can't save this user: " +e.getMessage());
        }
    }



}
