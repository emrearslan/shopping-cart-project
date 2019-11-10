package com.ea.shop.persistence.helper;

import com.ea.shop.application.ShoppingCartApplication;
import com.ea.shop.persistence.dto.DeliveryCostDTO;
import com.ea.shop.persistence.entity.CartItem;
import com.ea.shop.persistence.entity.ShoppingCart;
import com.ea.shop.persistence.entity.builder.CartItemBuilder;
import com.ea.shop.persistence.repository.CartItemRepository;
import com.ea.shop.util.MathUtil;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.boot.test.context.SpringBootTest;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.HashSet;

@SpringBootTest(classes = ShoppingCartApplication.class)
public class DeliveryHelperTest {

    @InjectMocks
    private DeliveryHelper deliveryHelper;

    @Mock
    private CartItemRepository cartItemRepository;

    @Test
    public void shouldCalculateDelivery() {
        ShoppingCart shoppingCart = new ShoppingCart();
        shoppingCart.setId(ShoppingCart.ACTIVE_USER);

        CartItem cartItem = new CartItemBuilder().id(20L)
                .quantity(1).campaignDiscount(new BigDecimal("500")).doBuild();
        shoppingCart.setCartItems(new HashSet<>(Arrays.asList(cartItem)));

        Mockito.when(cartItemRepository.findNumberOfDistinctCategory(shoppingCart)).thenReturn(2);

        DeliveryCostDTO deliveryCostDTO = new DeliveryCostDTO(new BigDecimal("2"), new BigDecimal("2"));
        deliveryHelper.calculateDelivery(shoppingCart, deliveryCostDTO);

        Assertions.assertTrue(MathUtil.isBigThanZero(shoppingCart.getDeliveryCost()));
    }


}