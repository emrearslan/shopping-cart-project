package com.ea.shop.persistence.helper;

import com.ea.shop.persistence.dto.DeliveryCostDTO;
import com.ea.shop.persistence.entity.ShoppingCart;
import com.ea.shop.persistence.repository.CartItemRepository;
import com.ea.shop.util.DeliveryUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;

@Component
public class DeliveryHelper {

    @Autowired
    private CartItemRepository cartItemRepository;

    public void calculateDelivery(ShoppingCart shoppingCart, DeliveryCostDTO deliveryCostDTO) {
        int numberOfDeliveries = getNumberOfDeliveries(shoppingCart);
        int numberOfProducts = getNumberOfProducts(shoppingCart);

        DeliveryUtil deliveryUtil = new DeliveryUtil(deliveryCostDTO.getCostPerDelivery(), deliveryCostDTO.getCostPerProduct());
        BigDecimal deliveryCost = deliveryUtil.calculateFor(numberOfDeliveries, numberOfProducts);

        shoppingCart.setDeliveryCost(deliveryCost);
    }

    private int getNumberOfDeliveries(ShoppingCart shoppingCart) {
        return cartItemRepository.findNumberOfDistinctCategory(shoppingCart);
    }

    private int getNumberOfProducts(ShoppingCart shoppingCart) {
        return shoppingCart.getCartItems().size();
    }

}
