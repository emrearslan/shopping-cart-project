package com.ea.shop.persistence.helper;

import com.ea.shop.application.ShoppingCartApplication;
import com.ea.shop.persistence.entity.CartItem;
import com.ea.shop.persistence.entity.Coupon;
import com.ea.shop.persistence.entity.DiscountType;
import com.ea.shop.persistence.entity.ShoppingCart;
import com.ea.shop.persistence.entity.builder.CartItemBuilder;
import com.ea.shop.persistence.entity.builder.CouponBuilder;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.springframework.boot.test.context.SpringBootTest;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.HashSet;

@SpringBootTest(classes = ShoppingCartApplication.class)
public class ShoppingCartHelperTest {

    @InjectMocks
    private ShoppingCartHelper shoppingCartHelper;

    @BeforeEach
    public void setup() {
    }

    @Test
    public void shouldCalculateDisplayPrice() {
        ShoppingCart shoppingCart = new ShoppingCart();
        shoppingCart.setId(ShoppingCart.ACTIVE_USER);

        CartItem cartItem = new CartItemBuilder().id(10L).quantity(4).unitPrice(new BigDecimal("100"))
                .totalPrice(new BigDecimal("400")).campaignDiscount(BigDecimal.ZERO)
                .finalPrice(new BigDecimal("400")).doBuild();
        shoppingCart.setCartItems(new HashSet<>(Arrays.asList(cartItem)));
        shoppingCart.setCouponDiscount(new BigDecimal("50"));

        shoppingCartHelper.calculateDisplayPrice(shoppingCart);
        Assertions.assertEquals(shoppingCart.getDisplayPrice(), new BigDecimal("350"));
    }

    @Test
    public void shouldCalculateCouponDiscountForRate() {
        ShoppingCart shoppingCart = new ShoppingCart();
        shoppingCart.setId(ShoppingCart.ACTIVE_USER);
        shoppingCart.setTotalFinalPrice(new BigDecimal("400"));

        Coupon coupon = new CouponBuilder().id(10L).title("coupon").minPurchaseAmount(new BigDecimal("100"))
                .discountAmount(new BigDecimal("50")).discountType(DiscountType.RATE).doBuild();

        shoppingCartHelper.calculateCouponDiscount(shoppingCart, coupon);
        Assertions.assertEquals(shoppingCart.getCouponDiscount(), new BigDecimal("200"));
    }

    @Test
    public void shouldCalculateCouponDiscountForAmount() {
        ShoppingCart shoppingCart = new ShoppingCart();
        shoppingCart.setId(ShoppingCart.ACTIVE_USER);
        shoppingCart.setTotalFinalPrice(new BigDecimal("400"));

        Coupon coupon = new CouponBuilder().id(10L).title("coupon").minPurchaseAmount(new BigDecimal("100"))
                .discountAmount(new BigDecimal("50")).discountType(DiscountType.AMOUNT).doBuild();

        shoppingCartHelper.calculateCouponDiscount(shoppingCart, coupon);
        Assertions.assertEquals(shoppingCart.getCouponDiscount(), new BigDecimal("50"));
    }

}