package com.ea.shop.persistence.repository;

import com.ea.shop.persistence.entity.CartItem;
import com.ea.shop.persistence.entity.ShoppingCart;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Set;

@Repository
public interface CartItemRepository extends CrudRepository<CartItem, Long> {

    @Query("select ci from CartItem ci where ci.product.category.id = :categoryId")
    Set<CartItem> findByCategoryId(@Param("categoryId") Long categoryId);

    @Query("select count(distinct ci.product.category) from CartItem ci where ci.shoppingCart = :shoppingCart")
    Integer findNumberOfDistinctCategory(@Param("shoppingCart") ShoppingCart shoppingCart);


}
