package com.example.transactionmanagementdemo.service;

import com.example.transactionmanagementdemo.dao.UserDao;
import com.example.transactionmanagementdemo.domain.entity.Product;
import com.example.transactionmanagementdemo.domain.entity.User;
import com.example.transactionmanagementdemo.exception.UserGetFailedException;
import com.example.transactionmanagementdemo.exception.UserSaveFailedException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class WatchListService {
    private  UserDao userDao;

    @Autowired
    public WatchListService(UserDao userDao) {
        this.userDao = userDao;
    }


    @Transactional
    public List<Product> getWatchListItemByUserId(Integer user_id) {
        User u = userDao.getById(user_id);
        return u.getProducts()
                .stream()
                .filter((product -> product.getStock_quantity() > 0)).collect(Collectors.toList());
    }

    @Transactional
    public List<Product> deleteProductFromUserWatchList(Integer user_id, Integer product_id) {
        userDao.deleteProduct(user_id, product_id);
        return getWatchListItemByUserId(user_id);
    }

    @Transactional
    public List<Product> addToWatchList(Integer user_id, Integer product_id) {
        userDao.addToWatchList(user_id, product_id);

        return getWatchListItemByUserId(user_id);

    }


}
