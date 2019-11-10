package com.ea.shop.persistence.dto.builder;

import com.ea.shop.persistence.dto.CategoryDTO;

public class CategoryDTOBuilder {

    private Long id;
    private String title;
    private Long parentId;

    public CategoryDTO doBuild() {
        CategoryDTO categoryDTO = new CategoryDTO();
        categoryDTO.setId(id);
        categoryDTO.setTitle(title);
        categoryDTO.setParentId(parentId);
        return categoryDTO;
    }

    public CategoryDTOBuilder id(Long id) {
        this.id = id;
        return this;
    }

    public CategoryDTOBuilder title(String title) {
        this.title = title;
        return this;
    }

    public CategoryDTOBuilder parent(Long parentId) {
        this.parentId = parentId;
        return this;
    }
}
