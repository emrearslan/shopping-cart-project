package com.ea.shop.persistence.dto.builder;

import com.ea.shop.persistence.dto.CouponDTO;
import com.ea.shop.persistence.entity.DiscountType;

import java.math.BigDecimal;

public class CouponDTOBuilder {

    private Long id;
    private String title;
    private BigDecimal minPurchaseAmount;
    private BigDecimal discountAmount;
    private DiscountType discountType = DiscountType.RATE;

    public CouponDTO doBuild() {
        CouponDTO couponDTO = new CouponDTO();
        couponDTO.setId(id);
        couponDTO.setTitle(title);
        couponDTO.setMinPurchaseAmount(minPurchaseAmount);
        couponDTO.setDiscountAmount(discountAmount);
        couponDTO.setDiscountType(discountType);
        return couponDTO;
    }

    public CouponDTOBuilder id(Long id) {
        this.id = id;
        return this;
    }

    public CouponDTOBuilder title(String title) {
        this.title = title;
        return this;
    }

    public CouponDTOBuilder minPurchaseAmount(BigDecimal minPurchaseAmount) {
        this.minPurchaseAmount = minPurchaseAmount;
        return this;
    }

    public CouponDTOBuilder discountAmount(BigDecimal discountAmount) {
        this.discountAmount = discountAmount;
        return this;
    }

    public CouponDTOBuilder discountType(DiscountType discountType) {
        this.discountType = discountType;
        return this;
    }
}
