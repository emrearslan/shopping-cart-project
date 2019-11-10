package com.ea.shop.persistence.entity;

import com.ea.shop.util.MathUtil;

import javax.persistence.*;
import java.math.BigDecimal;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

@Entity
@Table(name = "SHOPPING_CART")
public class ShoppingCart {

    public static final String ACTIVE_USER = "active-user";

    @Id
    @Column(name = "ID")
    private String id = "active-user";

    @OneToMany(mappedBy = "shoppingCart", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private Set<CartItem> cartItems = new HashSet<>();

    @Column(name = "COUPON_DISCOUNT")
    private BigDecimal couponDiscount = BigDecimal.ZERO;

    @Column(name = "DELIVERY_COST")
    private BigDecimal deliveryCost = BigDecimal.ZERO;

    @Column(name = "TOTAL_FINAL_PRICE")
    private BigDecimal totalFinalPrice = BigDecimal.ZERO;

    @Column(name = "DISPLAY_PRICE")
    private BigDecimal displayPrice = BigDecimal.ZERO;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public Set<CartItem> getCartItems() {
        return cartItems;
    }

    public void setCartItems(Set<CartItem> cartItems) {
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

    public BigDecimal getTotalFinalPrice() {
        return totalFinalPrice;
    }

    public void setTotalFinalPrice(BigDecimal totalFinalPrice) {
        this.totalFinalPrice = totalFinalPrice;
    }

    public BigDecimal getDisplayPrice() {
        return displayPrice;
    }

    public void setDisplayPrice(BigDecimal displayPrice) {
        this.displayPrice = displayPrice;
    }

    public boolean isUsedCampaignDiscount() {
        return getCartItems().stream().anyMatch(p -> MathUtil.isBigThanZero(p.getCampaignDiscount()));
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;

        if (o == null || getClass() != o.getClass()) return false;
        ShoppingCart that = (ShoppingCart) o;

        return Objects.equals(id, that.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}
