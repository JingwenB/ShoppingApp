package com.example.transactionmanagementdemo.service;

import com.example.transactionmanagementdemo.dao.ProductDao;
import com.example.transactionmanagementdemo.dao.UserDao;
import com.example.transactionmanagementdemo.domain.entity.Product;
import com.example.transactionmanagementdemo.domain.entity.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;

@Service
public class ProductService {
    private final ProductDao productDao;

    @Autowired
    public ProductService(ProductDao productDao) {
        this.productDao = productDao;
    }

    @Transactional
    public void saveProduct(Product product) {
        productDao.add(product);
    }


    @Transactional
    public List<Product> getAll(){
        return productDao.getAll();
    }

    @Transactional
    public Product getById(int id){
        return (Product) productDao.getById(id);
    }

    @Transactional
    public Product getByIdAsUser(int id){
        Product p = (Product) productDao.getById(id);
        p.setStock_quantity(null);
        p.setWholesale_price(null);
        return p;
    }

    @Transactional
    public List<Product> getAllAsUser(){
        List<Product> products  = productDao.getAll();
        products.stream().filter(product -> product.getStock_quantity() > 0).forEach((product -> {
                    product.setStock_quantity(null);
                    product.setWholesale_price(null);
                }));
        return products;
    }




}
