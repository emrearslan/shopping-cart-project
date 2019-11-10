package com.ea.shop.persistence.mapper;

import com.ea.shop.persistence.dto.ShoppingCartDTO;
import com.ea.shop.persistence.entity.ShoppingCart;
import com.ea.shop.persistence.mapper.base.BaseMapper;
import com.ea.shop.persistence.repository.ShoppingCartRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;

@Component
public class ShoppingCartMapper extends BaseMapper<ShoppingCart, ShoppingCartDTO> {

    @Autowired
    private ShoppingCartRepository shoppingCartRepository;

    @Autowired
    private CartItemMapper cartItemMapper;

    @Override
    public ShoppingCart toEntity(ShoppingCartDTO shoppingCartDTO) {
        if (shoppingCartDTO == null) return null;

        ShoppingCart shoppingCart = shoppingCartRepository.findActiveShoppingCart();

        if (!CollectionUtils.isEmpty(shoppingCartDTO.getCartItems())) {
            shoppingCart.setCartItems(cartItemMapper.toEntitySet(shoppingCartDTO.getCartItems()));
        }

        shoppingCart.setCouponDiscount(shoppingCartDTO.getCouponDiscount());
        shoppingCart.setDeliveryCost(shoppingCartDTO.getDeliveryCost());
        shoppingCart.setTotalFinalPrice(shoppingCartDTO.getTotalFinalPrice());
        shoppingCart.setDisplayPrice(shoppingCartDTO.getDisplayPrice());

        return shoppingCart;
    }

    @Override
    public ShoppingCartDTO toDto(ShoppingCart shoppingCart) {
        if (shoppingCart == null) return null;

        ShoppingCartDTO shoppingCartDTO = new ShoppingCartDTO();

        if (!CollectionUtils.isEmpty(shoppingCart.getCartItems())) {
            shoppingCartDTO.setCartItems(cartItemMapper.toDtoSet(shoppingCart.getCartItems()));
        }

        shoppingCartDTO.setCouponDiscount(shoppingCart.getCouponDiscount());
        shoppingCartDTO.setDeliveryCost(shoppingCart.getDeliveryCost());
        shoppingCartDTO.setTotalFinalPrice(shoppingCart.getTotalFinalPrice());
        shoppingCartDTO.setDisplayPrice(shoppingCart.getDisplayPrice());

        return shoppingCartDTO;
    }
}
