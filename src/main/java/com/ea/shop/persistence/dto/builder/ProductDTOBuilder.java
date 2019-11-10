package com.ea.shop.persistence.dto.builder;

import com.ea.shop.persistence.dto.ProductDTO;

import java.math.BigDecimal;

public class ProductDTOBuilder {

    private Long id;
    private String title;
    private BigDecimal price;
    private Long categoryId;

    public ProductDTO doBuild() {
        ProductDTO productDTO = new ProductDTO();
        productDTO.setId(id);
        productDTO.setTitle(title);
        productDTO.setPrice(price);
        productDTO.setCategoryId(categoryId);
        return productDTO;
    }

    public ProductDTOBuilder id(Long id) {
        this.id = id;
        return this;
    }

    public ProductDTOBuilder title(String title) {
        this.title = title;
        return this;
    }

    public ProductDTOBuilder price(BigDecimal price) {
        this.price = price;
        return this;
    }

    public ProductDTOBuilder categoryId(Long categoryId) {
        this.categoryId = categoryId;
        return this;
    }
}
