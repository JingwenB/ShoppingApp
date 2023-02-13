package com.example.transactionmanagementdemo.service;

import com.example.transactionmanagementdemo.dao.UserDao;
import com.example.transactionmanagementdemo.domain.entity.User;
import com.example.transactionmanagementdemo.exception.UserGetFailedException;
import com.example.transactionmanagementdemo.exception.UserSaveFailedException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;

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
    public User getById(int id) throws UserGetFailedException{
         User user = (User) userDao.getById(id);
         if(user == null){
             throw new UserGetFailedException("can't find user with id: " + id);
         }
        return user;
    }

    @Transactional
    public void saveUser(User user) throws UserSaveFailedException {
        userDao.add(user);
    }

    @Transactional
    public void deleteUser(int id) throws UserGetFailedException {
        User user = getById(id);
        userDao.delete(user);
    }

}
