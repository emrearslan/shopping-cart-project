package com.ea.shop.persistence.entity.builder;

import com.ea.shop.persistence.entity.CartItem;
import com.ea.shop.persistence.entity.Product;
import com.ea.shop.persistence.entity.ShoppingCart;

import java.math.BigDecimal;

public class CartItemBuilder {

    private Long id;
    private ShoppingCart shoppingCart;
    private Product product;
    private int quantity;
    private BigDecimal unitPrice = BigDecimal.ZERO;
    private BigDecimal totalPrice = BigDecimal.ZERO;
    private BigDecimal campaignDiscount = BigDecimal.ZERO;
    private BigDecimal finalPrice = BigDecimal.ZERO;

    public CartItem doBuild() {
        CartItem cartItem = new CartItem();
        cartItem.setId(id);
        cartItem.setShoppingCart(shoppingCart);
        cartItem.setProduct(product);
        cartItem.setQuantity(quantity);
        cartItem.setUnitPrice(unitPrice);
        cartItem.setTotalPrice(totalPrice);
        cartItem.setCampaignDiscount(campaignDiscount);
        cartItem.setFinalPrice(finalPrice);
        return cartItem;
    }

    public CartItemBuilder id(Long id) {
        this.id = id;
        return this;
    }

    public CartItemBuilder shoppingCart(ShoppingCart shoppingCart) {
        this.shoppingCart = shoppingCart;
        return this;
    }

    public CartItemBuilder product(Product product) {
        this.product = product;
        return this;
    }

    public CartItemBuilder quantity(int quantity) {
        this.quantity = quantity;
        return this;
    }

    public CartItemBuilder unitPrice(BigDecimal unitPrice) {
        this.unitPrice = unitPrice;
        return this;
    }

    public CartItemBuilder totalPrice(BigDecimal totalPrice) {
        this.totalPrice = totalPrice;
        return this;
    }

    public CartItemBuilder campaignDiscount(BigDecimal campaignDiscount) {
        this.campaignDiscount = campaignDiscount;
        return this;
    }

    public CartItemBuilder finalPrice(BigDecimal finalPrice) {
        this.finalPrice = finalPrice;
        return this;
    }

}
