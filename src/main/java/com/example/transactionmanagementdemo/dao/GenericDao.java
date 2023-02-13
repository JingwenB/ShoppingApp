package com.example.transactionmanagementdemo.dao;


import com.example.transactionmanagementdemo.domain.entity.Product;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import java.util.List;
import java.util.Optional;

@Repository
public abstract class GenericDao<T> {

    @Autowired
    SessionFactory sessionFactory;
    public  Class<T> entityClass;


    public List<T> getAll(){
        Session session = sessionFactory.openSession();
        List<T> list = null;
        try{
            session = sessionFactory.openSession();
            CriteriaBuilder cb = session.getCriteriaBuilder();
            CriteriaQuery<T> cq = cb.createQuery(entityClass);
            Root<T> root = cq.from(entityClass);
            cq.select(root);
            list = session.createQuery(cq).getResultList();
        }catch (Exception e){
            e.printStackTrace();
        } finally {
            session.close();
        }
        return (list.isEmpty()) ? null : list;
    }

    public T getById(int id){
        Session session = sessionFactory.openSession();
        Optional<T> object = null;
        try{
            session = sessionFactory.openSession();
            CriteriaBuilder cb = session.getCriteriaBuilder();
            CriteriaQuery<T> cq = cb.createQuery(entityClass);
            Root<T> root = cq.from(entityClass);
            Predicate predicate = cb.equal(root.get("id"), id);
            cq.select(root).where(predicate);
            object = session.createQuery(cq).uniqueResultOptional();
        }
        catch (Exception e){
            e.printStackTrace();
        } finally {
            session.close();
        }
        return (object.isPresent())? object.get() : null;
    }




    public void deleteById(T entity){
        Session session;
        try{
            session = sessionFactory.getCurrentSession();
            session.delete(entity);
        }
        catch (Exception e){
            e.printStackTrace();
        }
    }


}
