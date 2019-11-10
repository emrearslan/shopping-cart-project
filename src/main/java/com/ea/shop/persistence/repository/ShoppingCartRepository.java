package com.ea.shop.persistence.repository;

import com.ea.shop.persistence.entity.ShoppingCart;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ShoppingCartRepository extends CrudRepository<ShoppingCart, String> {

    @Query("select sc from ShoppingCart sc where sc.id = '" + ShoppingCart.ACTIVE_USER + "'")
    ShoppingCart findActiveShoppingCart();

}
