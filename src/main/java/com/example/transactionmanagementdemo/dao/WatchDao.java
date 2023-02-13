package com.example.transactionmanagementdemo.dao;


import com.example.transactionmanagementdemo.domain.entity.Product;
import com.example.transactionmanagementdemo.domain.entity.User;
import com.example.transactionmanagementdemo.domain.entity.Watch;
import com.example.transactionmanagementdemo.exception.UserGetFailedException;
import com.example.transactionmanagementdemo.exception.UserSaveFailedException;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.springframework.stereotype.Repository;


@Repository
public class WatchDao extends GenericDao<Watch> {

    public WatchDao() {
        entityClass = Watch.class;
    }


    public Watch createWatch(int user_id, int product_id) {

        Session session = sessionFactory.openSession();
        Transaction transaction = session.beginTransaction();
        User user = session.load(User.class, user_id);
        Product product = session.load(Product.class, product_id);

        Watch watch = new Watch();
        watch.setUser(user);
        watch.setProduct(product);

        session.saveOrUpdate(watch);
        transaction.commit();
        session.close();
        return watch;
    }
}
