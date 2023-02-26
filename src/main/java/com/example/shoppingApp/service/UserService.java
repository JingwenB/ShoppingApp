package com.example.shoppingApp.service;

import com.example.shoppingApp.dao.UserDao;
import com.example.shoppingApp.domain.entity.Product;
import com.example.shoppingApp.domain.entity.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@Service
public class UserService {
    private  UserDao userDao;

    @Autowired
    public UserService(UserDao userDao) {
        this.userDao = userDao;
    }


    @Transactional
    public List<User> getAll(){
        List<User> users = userDao.getAll();
        return users;
    }

    @Transactional
    public User getById(int id)  {
         User user = (User) userDao.getById(id);
        return user;
    }

    @Transactional
    public int getIdByUsername(String name)  {
        List<User> users = userDao.getAll();
        return users.stream().filter(u -> Objects.equals(u.getUsername(), name)).findFirst().orElse(null).getId();
    }
//
//    @Transactional
//    public void saveUser(User user) {
//        userDao.add(user);
//    }
//
//    @Transactional
//    public void deleteUser(int id)  {
//        User user = getById(id);
//        userDao.delete(user);
//    }
//
//    public List<Product> getWatchListItemByUserId(Integer user_id) {
//        User u = userDao.getById(user_id);
//        return u.getProducts()
//                .stream()
//                .filter((product -> product.getStock_quantity() > 0)).collect(Collectors.toList());
//    }

}
