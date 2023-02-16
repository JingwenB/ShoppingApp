package com.example.shoppingApp.service;

import com.example.shoppingApp.dao.UserDao;
import com.example.shoppingApp.domain.entity.Order;
import com.example.shoppingApp.domain.entity.Product;
import com.example.shoppingApp.domain.entity.User;
import com.example.shoppingApp.exception.RequestPageOverTotalPageException;
import net.minidev.json.JSONObject;
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
                .filter((product -> product.getStock_quantity() > 0))
                .map((product) ->{
                    product.setStock_quantity(null);
                    product.setWholesale_price(null);
                    return product;
                })
                .collect(Collectors.toList());

    }
    @Transactional
    public JSONObject getWatchListItemByUserIdPaginated(Integer user_id, int page, int size) {
        List<Product> products =  getWatchListItemByUserId(user_id);
        return findPaginated(products, page, size);
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
