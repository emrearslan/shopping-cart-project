package com.ea.shop.persistence.helper;

import com.ea.shop.application.ShoppingCartApplication;
import com.ea.shop.persistence.entity.Campaign;
import com.ea.shop.persistence.entity.CartItem;
import com.ea.shop.persistence.entity.DiscountType;
import com.ea.shop.persistence.entity.builder.CampaignBuilder;
import com.ea.shop.persistence.entity.builder.CartItemBuilder;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.springframework.boot.test.context.SpringBootTest;

import java.math.BigDecimal;

@SpringBootTest(classes = ShoppingCartApplication.class)
public class CartItemHelperTest {

    @InjectMocks
    private CartItemHelper cartItemHelper;

    private CartItem cartItem;
    private Campaign campaign;

    @BeforeEach
    public void setup() {
        cartItem = new CartItemBuilder().id(10L).quantity(4).unitPrice(new BigDecimal("100"))
                .totalPrice(new BigDecimal("400")).campaignDiscount(BigDecimal.ZERO)
                .finalPrice(new BigDecimal("400")).doBuild();

        campaign = new CampaignBuilder().id(10L).title("campaign").itemLimit(3)
                .discountAmount(new BigDecimal("50")).discountType(DiscountType.RATE).doBuild();
    }

    @Test
    public void shouldCalculateCampaignDiscountForRate() {
        cartItemHelper.calculateCampaignDiscount(cartItem, campaign);

        Assertions.assertEquals(cartItem.getUnitPrice(), new BigDecimal("100"));
        Assertions.assertEquals(cartItem.getTotalPrice(), new BigDecimal("400"));
        Assertions.assertEquals(cartItem.getCampaignDiscount(), new BigDecimal("200"));
        Assertions.assertEquals(cartItem.getFinalPrice(), new BigDecimal("200"));
    }

    @Test
    public void shouldCalculateCampaignDiscountForAmount() {
        campaign.setDiscountType(DiscountType.AMOUNT);
        cartItemHelper.calculateCampaignDiscount(cartItem, campaign);

        Assertions.assertEquals(cartItem.getUnitPrice(), new BigDecimal("100"));
        Assertions.assertEquals(cartItem.getTotalPrice(), new BigDecimal("400"));
        Assertions.assertEquals(cartItem.getCampaignDiscount(), new BigDecimal("200"));
        Assertions.assertEquals(cartItem.getFinalPrice(), new BigDecimal("200"));
    }

    @Test
    public void shouldCalculateFinalPrice() {
        cartItem.setCampaignDiscount(new BigDecimal("100"));
        cartItemHelper.calculateFinalPrice(cartItem);

        Assertions.assertEquals(cartItem.getFinalPrice(), new BigDecimal("300"));
    }

}