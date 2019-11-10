package com.ea.shop.persistence.dto;

import java.math.BigDecimal;

public class DeliveryCostDTO {

    private BigDecimal costPerDelivery;
    private BigDecimal costPerProduct;

    public DeliveryCostDTO() {

    }

    public DeliveryCostDTO(BigDecimal costPerDelivery, BigDecimal costPerProduct) {
        this.costPerDelivery = costPerDelivery;
        this.costPerProduct = costPerProduct;
    }

    public BigDecimal getCostPerDelivery() {
        return costPerDelivery;
    }

    public void setCostPerDelivery(BigDecimal costPerDelivery) {
        this.costPerDelivery = costPerDelivery;
    }

    public BigDecimal getCostPerProduct() {
        return costPerProduct;
    }

    public void setCostPerProduct(BigDecimal costPerProduct) {
        this.costPerProduct = costPerProduct;
    }
}
