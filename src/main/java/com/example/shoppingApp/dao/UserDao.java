package com.example.shoppingApp.dao;


import com.example.shoppingApp.domain.entity.Product;
import com.example.shoppingApp.domain.entity.User;
import com.example.shoppingApp.exception.DuplicatedProductInWatcchList;
import com.example.shoppingApp.exception.NotFoundException;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.springframework.stereotype.Repository;

import java.util.stream.Collectors;


@Repository
public class UserDao extends GenericDao<User> {

    public UserDao() {
        entityClass = User.class;
    }


    public void deleteProduct(Integer user_id, Integer product_id) {
        Session session = sessionFactory.openSession();
        Transaction tx = session.beginTransaction();
        User user = session.get(User.class, user_id);
        Product product = session.get(Product.class, product_id);

        if(user == null){
            throw new NotFoundException(
                    String.format("Can not find object class: %s, with id: %d", User.class, user_id));
        }

        if(product == null){
            throw new NotFoundException(
                    String.format("Can not find object class: %s, with id: %d", Product.class, user_id));
        }

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
        User user = session.get(User.class, user_id);
        Product product = session.get(Product.class, product_id);

        if(user == null){
            throw new NotFoundException(
                    String.format("Can not find object class: %s, with id: %d", User.class, user_id));
        }

        if(product == null){
            throw new NotFoundException(
                    String.format("Can not find object class: %s, with id: %d", Product.class, user_id));
        }
        if(user.getProducts()
                .stream()
                .map(Product::getId)
                .collect(Collectors.toList())
                .contains(product_id)){
            throw new DuplicatedProductInWatcchList("this item has been added into watchlist before");
        }

        product.getUser().add(user);
        user.getProducts().add(product);
        session.persist(product);
        session.persist(user);
        tx.commit();
        session.close();

    }

}
