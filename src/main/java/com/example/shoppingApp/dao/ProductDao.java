package com.example.shoppingApp.dao;


import com.example.shoppingApp.domain.entity.Product;
import com.example.shoppingApp.exception.InvalidProductUpdateInfoException;
import com.example.shoppingApp.exception.NotFoundException;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.springframework.stereotype.Repository;


@Repository
public class ProductDao extends GenericDao<Product> {


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

    public void update(Integer id, Product entity){
        Session session = sessionFactory.openSession();
        Transaction tx = session.beginTransaction();
        Product product_db = session.get(Product.class, id);
        if(product_db == null){
            throw new NotFoundException(
                    String.format("Can not find object class: %s, with id: %d", Product.class, id));
        }

        if(entity.getDescription() != null){
            product_db.setDescription( entity.getDescription());
        }
        if(entity.getWholesale_price() != null){
            product_db.setWholesale_price(entity.getWholesale_price());
        }
        if(entity.getRetail_price() != null){
            product_db.setWholesale_price(entity.getRetail_price());
        }
        if(entity.getStock_quantity() != null){
            product_db.setStock_quantity( entity.getStock_quantity());
        }

        System.out.println("Product after update: "+ entity);
        tx.commit();
        session.close();
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
