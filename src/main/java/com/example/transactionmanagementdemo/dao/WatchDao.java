package com.example.transactionmanagementdemo.dao;


import com.example.transactionmanagementdemo.domain.entity.User;
import com.example.transactionmanagementdemo.domain.entity.Watch;
import com.example.transactionmanagementdemo.exception.UserGetFailedException;
import com.example.transactionmanagementdemo.exception.UserSaveFailedException;
import org.hibernate.Session;
import org.springframework.stereotype.Repository;


@Repository
public class WatchDao extends GenericDao {

    public WatchDao() {
        entityClass = Watch.class;
    }


}
