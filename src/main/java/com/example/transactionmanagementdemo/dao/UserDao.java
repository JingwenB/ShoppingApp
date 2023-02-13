package com.example.transactionmanagementdemo.dao;


import com.example.transactionmanagementdemo.domain.entity.Product;
import com.example.transactionmanagementdemo.domain.entity.User;
import com.example.transactionmanagementdemo.exception.UserGetFailedException;
import com.example.transactionmanagementdemo.exception.UserSaveFailedException;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;


@Repository
public class UserDao extends GenericDao {

    public UserDao() {
        entityClass = User.class;
    }

    public void add(User entity) throws UserSaveFailedException {
        Session session = sessionFactory.openSession();
        try{
            session = sessionFactory.getCurrentSession();
            session.saveOrUpdate(entity);
        }
        catch(Exception e){
            throw new UserSaveFailedException("can't save this user: " +e.getMessage());
        }  finally {
            session.close();
        }
    }

    public void delete(User entity) throws UserGetFailedException {
        Session session = sessionFactory.openSession();
        try{
            session = sessionFactory.getCurrentSession();
            session.delete(entity);
        }
        catch (Exception e){
            throw new UserSaveFailedException("can't delete this user: " +e.getMessage());
        }  finally {
            session.close();
        }
    }

}
