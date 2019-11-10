package com.ea.shop.persistence.dto;

public class CartItemRefDTO {

    private Long productId;
    private int quantity = 1;

    public CartItemRefDTO() {

    }

    public CartItemRefDTO(Long productId, int quantity) {
        this.productId = productId;
        this.quantity = quantity;
    }

    public Long getProductId() {
        return productId;
    }

    public void setProductId(Long productId) {
        this.productId = productId;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }
}
