package com.ea.shop.persistence.entity.builder;

import com.ea.shop.persistence.entity.Category;
import com.ea.shop.persistence.entity.Product;

import java.math.BigDecimal;

public class ProductBuilder {

    private Long id;
    private String title;
    private BigDecimal price;
    private Category category;

    public Product doBuild() {
        Product product = new Product();
        product.setId(id);
        product.setTitle(title);
        product.setPrice(price);
        product.setCategory(category);
        return product;
    }

    public ProductBuilder id(Long id) {
        this.id = id;
        return this;
    }

    public ProductBuilder title(String title) {
        this.title = title;
        return this;
    }

    public ProductBuilder price(BigDecimal price) {
        this.price = price;
        return this;
    }

    public ProductBuilder category(Category category) {
        this.category = category;
        return this;
    }

}
