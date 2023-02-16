package com.example.shoppingApp.service;

import com.example.shoppingApp.dao.ProductDao;
import com.example.shoppingApp.domain.entity.Product;
import com.example.shoppingApp.domain.entity.User;
import com.example.shoppingApp.exception.InvalidProductUpdateInfoException;
import com.example.shoppingApp.exception.NotFoundException;
import com.example.shoppingApp.exception.RequestPageOverTotalPageException;
import net.minidev.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import sun.jvm.hotspot.debugger.Page;

import javax.transaction.Transactional;
import java.awt.print.Pageable;
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
        List<Product> products = productDao.getAll();
        List<Product> ret = products.stream().filter(product -> product.getStock_quantity() > 0)
                .map((product) ->{
                    product.setStock_quantity(null);
                    product.setWholesale_price(null);
                    return product;
                }).collect(Collectors.toList());
        return ret;
    }

    @Transactional
    public List<Product> createProduct(Product product)  {
        productDao.add(product);
        return getAll();
    }

    @Transactional
    public Product update(Integer id, Product product){
        if(product.getStock_quantity() < 0){
            throw new InvalidProductUpdateInfoException("stock price can not be negative");
        }
        // can update retail price < wholesale price
        productDao.update(id, product);
        return productDao.getById(id);
    }


    @Transactional
    public JSONObject getPaginatedProduct(int page, int size) {
        return findPaginated(productDao.getAll(), page, size);
    }

    @Transactional
    public JSONObject getPaginatedProductAsUser(int page, int size) {
        return findPaginated(getAllAsUser(), page, size);
    }

    public JSONObject findPaginated(List<Product> items, int page, int size) {
        int totalItems = items.size();
        int totalPages = (int) Math.floor((double)items.size() /(double)size);
        // 10/3 => 4 page, 0,1,2 | 3,4,5|6,7,8|9
        //               page 1
        if (page > totalPages){
             throw new RequestPageOverTotalPageException(
                    String.format("Request page: %d over the total page number: %d", page, totalPages)
             );
        }
        JSONObject ret  = new JSONObject();
        ret.put("totalPages", totalPages);
        ret.put("totalItems", totalItems);
        ret.put("currentPage", page);
        ret.put("pageSize", size);
        List<Product> currentItems = items.subList(
                size * (page - 1), Math.min(size* page, items.size()) );
        ret.put("currentItems", currentItems);
        return ret;
    }
}
