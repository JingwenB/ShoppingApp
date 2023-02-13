package com.example.transactionmanagementdemo.service;

import com.example.transactionmanagementdemo.dao.UserDao;
import com.example.transactionmanagementdemo.dao.WatchDao;
import com.example.transactionmanagementdemo.domain.entity.Order;
import com.example.transactionmanagementdemo.domain.entity.Product;
import com.example.transactionmanagementdemo.domain.entity.User;
import com.example.transactionmanagementdemo.domain.entity.Watch;
import com.example.transactionmanagementdemo.exception.UserGetFailedException;
import com.example.transactionmanagementdemo.exception.UserSaveFailedException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;
import java.util.stream.Collectors;

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

    @Transactional
    public Watch createWatch(int user_id, int product_id)  {
       return watchDao.createWatch(user_id, product_id);
    }


    // get watch list by user, only show quantity > 0
    @Transactional
    public List<Watch> getByUserId(int id) {
        List<Watch> watches = watchDao.getAll();
        return watches.stream().filter(
                (watch)->  (watch.getUser().getId() == id) &&
                        (watch.getProduct().getStock_quantity() > 0)).collect(Collectors.toList());
    }


}
