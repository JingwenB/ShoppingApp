package com.example.transactionmanagementdemo.dao;


import com.example.transactionmanagementdemo.domain.entity.Product;
import com.example.transactionmanagementdemo.domain.entity.User;
import com.example.transactionmanagementdemo.exception.UserGetFailedException;
import com.example.transactionmanagementdemo.exception.UserSaveFailedException;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;


@Repository
public class UserDao extends GenericDao<User> {

    public UserDao() {
        entityClass = User.class;
    }


    public void deleteProduct(Integer user_id, Integer product_id) {
        Session session = sessionFactory.openSession();
        Transaction tx = session.beginTransaction();
        User user = session.load(User.class, user_id);
        Product product = session.load(Product.class, product_id);
        user.getProducts().remove(product);
        product.getUser().remove(user);
        session.saveOrUpdate(product);
        session.saveOrUpdate(user);
        tx.commit();
        session.close();
    }

    public void addToWatchList(Integer user_id, Integer product_id) {
        Session session = sessionFactory.openSession();
        Transaction tx = session.beginTransaction();
        User user = session.load(User.class, user_id);
        Product product = session.load(Product.class, product_id);

        product.getUser().add(user);
//        user.getProducts().add(product); 可以不用加

        session.persist(product);
        tx.commit();
        session.close();

    }

}
