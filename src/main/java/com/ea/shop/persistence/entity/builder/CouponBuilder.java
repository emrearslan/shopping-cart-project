package com.ea.shop.persistence.entity.builder;

import com.ea.shop.persistence.entity.Coupon;
import com.ea.shop.persistence.entity.DiscountType;

import java.math.BigDecimal;

public class CouponBuilder {

    private Long id;
    private String title;
    private BigDecimal minPurchaseAmount;
    private BigDecimal discountAmount;
    private DiscountType discountType;

    public Coupon doBuild() {
        Coupon coupon = new Coupon();
        coupon.setId(id);
        coupon.setTitle(title);
        coupon.setMinPurchaseAmount(minPurchaseAmount);
        coupon.setDiscountAmount(discountAmount);
        coupon.setDiscountType(discountType);
        return coupon;
    }

    public CouponBuilder id(Long id) {
        this.id = id;
        return this;
    }

    public CouponBuilder title(String title) {
        this.title = title;
        return this;
    }

    public CouponBuilder minPurchaseAmount(BigDecimal minPurchaseAmount) {
        this.minPurchaseAmount = minPurchaseAmount;
        return this;
    }

    public CouponBuilder discountAmount(BigDecimal discountAmount) {
        this.discountAmount = discountAmount;
        return this;
    }

    public CouponBuilder discountType(DiscountType discountType) {
        this.discountType = discountType;
        return this;
    }

}
