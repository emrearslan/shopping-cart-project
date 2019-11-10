package com.ea.shop.persistence.dto.builder;

import com.ea.shop.persistence.dto.CartItemDTO;

import java.math.BigDecimal;

public class CartItemDTOBuilder {

    private Long id;
    private String shoppingCartId;
    private Long productId;
    private int quantity = 1;
    private BigDecimal unitPrice = BigDecimal.ZERO;
    private BigDecimal totalPrice = BigDecimal.ZERO;
    private BigDecimal campaignDiscount = BigDecimal.ZERO;
    private BigDecimal finalPrice = BigDecimal.ZERO;

    public CartItemDTO doBuild() {
        CartItemDTO cartItemDTO = new CartItemDTO();
        cartItemDTO.setId(id);
        cartItemDTO.setShoppingCartId(shoppingCartId);
        cartItemDTO.setProductId(productId);
        cartItemDTO.setQuantity(quantity);
        cartItemDTO.setUnitPrice(unitPrice);
        cartItemDTO.setTotalPrice(totalPrice);
        cartItemDTO.setCampaignDiscount(campaignDiscount);
        cartItemDTO.setFinalPrice(finalPrice);
        return cartItemDTO;
    }

    public CartItemDTOBuilder id(Long id) {
        this.id = id;
        return this;
    }

    public CartItemDTOBuilder shoppingCartId(String shoppingCartId) {
        this.shoppingCartId = shoppingCartId;
        return this;
    }

    public CartItemDTOBuilder productId(Long productId) {
        this.productId = productId;
        return this;
    }

    public CartItemDTOBuilder quantity(int quantity) {
        this.quantity = quantity;
        return this;
    }

    public CartItemDTOBuilder unitPrice(BigDecimal unitPrice) {
        this.unitPrice = unitPrice;
        return this;
    }

    public CartItemDTOBuilder totalPrice(BigDecimal totalPrice) {
        this.totalPrice = totalPrice;
        return this;
    }

    public CartItemDTOBuilder campaignDiscount(BigDecimal campaignDiscount) {
        this.campaignDiscount = campaignDiscount;
        return this;
    }

    public CartItemDTOBuilder finalPrice(BigDecimal finalPrice) {
        this.finalPrice = finalPrice;
        return this;
    }

}
