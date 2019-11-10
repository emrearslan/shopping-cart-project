package com.ea.shop.persistence.helper;

import com.ea.shop.persistence.entity.Coupon;
import com.ea.shop.persistence.entity.DiscountType;
import com.ea.shop.persistence.entity.ShoppingCart;
import com.ea.shop.util.MathUtil;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;

@Component
public class ShoppingCartHelper {

    public void calculateDisplayPrice(ShoppingCart shoppingCart) {
        BigDecimal totalFinalPrice = shoppingCart.getCartItems().stream()
                .map(p -> p.getFinalPrice()).reduce(BigDecimal.ZERO, BigDecimal::add);

        BigDecimal displayPrice = totalFinalPrice;
        if (shoppingCart.getCouponDiscount() != null) {
            displayPrice = displayPrice.subtract(shoppingCart.getCouponDiscount());
        }
        if (shoppingCart.getDeliveryCost() != null) {
            displayPrice = displayPrice.add(shoppingCart.getDeliveryCost());
        }

        shoppingCart.setTotalFinalPrice(totalFinalPrice);
        shoppingCart.setDisplayPrice(displayPrice);
    }

    public void calculateCouponDiscount(ShoppingCart shoppingCart, Coupon coupon) {
        DiscountType discountType = coupon.getDiscountType();
        BigDecimal couponDiscount = BigDecimal.ZERO;

        switch (discountType) {
            case RATE:
                couponDiscount = MathUtil.percentage(shoppingCart.getTotalFinalPrice(), coupon.getDiscountAmount());
                break;
            case AMOUNT:
                couponDiscount = coupon.getDiscountAmount();
                break;
        }

        shoppingCart.setCouponDiscount(couponDiscount);
    }
}
