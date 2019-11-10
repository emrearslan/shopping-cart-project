package com.ea.shop.persistence.entity.builder;

import com.ea.shop.persistence.entity.Category;

public class CategoryBuilder {

    private Long id;
    private String title;
    private Category parent;

    public Category doBuild() {
        Category category = new Category();
        category.setId(id);
        category.setTitle(title);
        category.setParent(parent);
        return category;
    }

    public CategoryBuilder id(Long id) {
        this.id = id;
        return this;
    }

    public CategoryBuilder title(String title) {
        this.title = title;
        return this;
    }

    public CategoryBuilder parent(Category parent) {
        this.parent = parent;
        return this;
    }

}
