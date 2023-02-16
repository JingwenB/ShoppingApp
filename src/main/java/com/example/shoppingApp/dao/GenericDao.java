package com.example.shoppingApp.dao;


import com.example.shoppingApp.exception.DeleteFailedException;
import com.example.shoppingApp.exception.NotFoundException;
import com.example.shoppingApp.exception.SaveFailedException;
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

    public T getById(int id) throws NotFoundException {
        Session session = sessionFactory.openSession();
        Optional<T> object = null;
        try{
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
        if(!object.isPresent()){
            throw new NotFoundException(
                    String.format("Can not find object class: %s, with id: %d", entityClass, id));
        }
        return object.get();
    }

    public void add(T entity)  {
        Session session = sessionFactory.openSession();
        try{
            session.saveOrUpdate(entity);
        }
        catch(Exception e){
            e.printStackTrace();
            throw new SaveFailedException(
                    String.format("Save object class: %s,\n" +
                            "with entity: %s", entityClass, entity));
        }  finally {
            session.close();
        }
    }

    public void delete(T entity){
        Session session= sessionFactory.openSession();
        try{
            session.delete(entity);
        }
        catch (Exception e){
            e.printStackTrace();
            throw new DeleteFailedException(
                    String.format("Delete object class: %s,\n" +
                            "with entity: %s \n failed", entityClass, entity));
        }
        finally {
            session.close();
        }
    }

}
