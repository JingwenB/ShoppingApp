package com.example.transactionmanagementdemo.service;

import com.example.transactionmanagementdemo.dao.ProductDao;
import com.example.transactionmanagementdemo.domain.entity.Product;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class ProductService {
    private final ProductDao productDao;

    @Autowired
    public ProductService(ProductDao productDao) {
        this.productDao = productDao;
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
        List<Product> products = productDao.getAll().stream().filter(product -> product.getStock_quantity() > 0)
                .map((product) ->{
                    product.setStock_quantity(null);
                    product.setWholesale_price(null);
                    return product;
                }).collect(Collectors.toList());
        return products;
    }

    @Transactional
    public List<Product> createProduct(Product product)  {
        productDao.add(product);
        return getAll();
    }

    @Transactional
    public Product update(Integer id, Product product) {
        productDao.update(id, product);

        return productDao.getById(id);
    }


}
