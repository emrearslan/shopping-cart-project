package com.ea.shop.persistence.dto;

import javax.xml.bind.annotation.XmlTransient;
import java.math.BigDecimal;
import java.util.HashSet;
import java.util.Set;

public class ShoppingCartDTO {

    @XmlTransient
    private String id = "active-user";

    private Set<CartItemDTO> cartItems = new HashSet<>();
    private BigDecimal couponDiscount = BigDecimal.ZERO;
    private BigDecimal deliveryCost = BigDecimal.ZERO;
    private BigDecimal totalFinalPrice = BigDecimal.ZERO;
    private BigDecimal displayPrice = BigDecimal.ZERO;

    public String getId() {
        return id;
    }

    public Set<CartItemDTO> getCartItems() {
        return cartItems;
    }

    public void setCartItems(Set<CartItemDTO> cartItems) {
        this.cartItems = cartItems;
    }

    public BigDecimal getCouponDiscount() {
        return couponDiscount;
    }

    public void setCouponDiscount(BigDecimal couponDiscount) {
        this.couponDiscount = couponDiscount;
    }

    public BigDecimal getDeliveryCost() {
        return deliveryCost;
    }

    public void setDeliveryCost(BigDecimal deliveryCost) {
        this.deliveryCost = deliveryCost;
    }

    public BigDecimal getDisplayPrice() {
        return displayPrice;
    }

    public BigDecimal getTotalFinalPrice() {
        return totalFinalPrice;
    }

    public void setTotalFinalPrice(BigDecimal totalFinalPrice) {
        this.totalFinalPrice = totalFinalPrice;
    }

    public void setDisplayPrice(BigDecimal displayPrice) {
        this.displayPrice = displayPrice;
    }
}
