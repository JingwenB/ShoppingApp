package com.example.transactionmanagementdemo.service;

import com.example.transactionmanagementdemo.dao.UserDao;
import com.example.transactionmanagementdemo.dao.WatchDao;
import com.example.transactionmanagementdemo.domain.entity.User;
import com.example.transactionmanagementdemo.domain.entity.Watch;
import com.example.transactionmanagementdemo.exception.UserGetFailedException;
import com.example.transactionmanagementdemo.exception.UserSaveFailedException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;

@Service
public class WatchService {
    private WatchDao watchDao;

    @Autowired
    public WatchService(WatchDao watchDao) {
        this.watchDao = watchDao;
    }


    @Transactional
    public List<Watch> getAll(){
        List<Watch> watches = watchDao.getAll();
        return watches;
    }


}
