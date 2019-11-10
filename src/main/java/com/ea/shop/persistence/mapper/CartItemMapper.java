package com.ea.shop.persistence.mapper;

import com.ea.shop.persistence.dto.CartItemDTO;
import com.ea.shop.persistence.entity.CartItem;
import com.ea.shop.persistence.mapper.base.BaseMapper;
import com.ea.shop.persistence.repository.CartItemRepository;
import com.ea.shop.persistence.repository.ProductRepository;
import com.ea.shop.persistence.repository.ShoppingCartRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class CartItemMapper extends BaseMapper<CartItem, CartItemDTO> {

    @Autowired
    private CartItemRepository cartItemRepository;

    @Autowired
    private ShoppingCartRepository shoppingCartRepository;

    @Autowired
    private ProductRepository productRepository;

    @Override
    public CartItem toEntity(CartItemDTO cartItemDTO) {
        if (cartItemDTO == null) return null;

        CartItem cartItem = null;

        if (cartItemDTO.getId() != null) {
            cartItem = cartItemRepository.findById(cartItemDTO.getId()).orElse(null);
        }

        if (cartItem == null) {
            cartItem = new CartItem();
        }

        if (cartItemDTO.getShoppingCartId() != null) {
            cartItem.setShoppingCart(shoppingCartRepository.findById(cartItemDTO.getShoppingCartId()).orElse(null));
        }

        if (cartItemDTO.getProductId() != null) {
            cartItem.setProduct(productRepository.findById(cartItemDTO.getProductId()).orElse(null));
        }

        cartItem.setQuantity(cartItemDTO.getQuantity());
        cartItem.setUnitPrice(cartItemDTO.getUnitPrice());
        cartItem.setTotalPrice(cartItemDTO.getTotalPrice());
        cartItem.setCampaignDiscount(cartItemDTO.getCampaignDiscount());
        cartItem.setFinalPrice(cartItemDTO.getFinalPrice());

        return cartItem;
    }

    @Override
    public CartItemDTO toDto(CartItem cartItem) {
        if (cartItem == null) return null;

        CartItemDTO cartItemDTO = new CartItemDTO();
        cartItemDTO.setId(cartItem.getId());
        cartItemDTO.setShoppingCartId(cartItem.getShoppingCart().getId());
        cartItemDTO.setProductId(cartItem.getProduct().getId());
        cartItemDTO.setQuantity(cartItem.getQuantity());
        cartItemDTO.setUnitPrice(cartItem.getUnitPrice());
        cartItemDTO.setTotalPrice(cartItem.getTotalPrice());
        cartItemDTO.setCampaignDiscount(cartItem.getCampaignDiscount());
        cartItemDTO.setFinalPrice(cartItem.getFinalPrice());

        return cartItemDTO;
    }
}
