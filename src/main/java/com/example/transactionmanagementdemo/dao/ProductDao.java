package com.example.transactionmanagementdemo.dao;


import com.example.transactionmanagementdemo.domain.entity.Product;
import com.example.transactionmanagementdemo.domain.entity.User;
import org.hibernate.Session;
import org.springframework.stereotype.Repository;


@Repository
public class ProductDao extends GenericDao {


    public ProductDao() {
        entityClass = Product.class;
    }


    public void add(Product entity){
        Session session = sessionFactory.openSession();
        try{
            session = sessionFactory.openSession();
            session.saveOrUpdate(entity);
        }
        catch(Exception e){
            e.printStackTrace();
        } finally {
            session.close();
        }

    }

    public void delete(Product entity){
        Session session = sessionFactory.openSession();
        try{
            session = sessionFactory.openSession();
            session.delete(entity);
        }
        catch (Exception e){
            e.printStackTrace();
        } finally {
            session.close();
        }
    }

}
