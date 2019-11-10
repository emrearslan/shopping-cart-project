package com.ea.shop.persistence.mapper;

import com.ea.shop.persistence.dto.CategoryDTO;
import com.ea.shop.persistence.entity.Category;
import com.ea.shop.persistence.mapper.base.BaseMapper;
import com.ea.shop.persistence.repository.CategoryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class CategoryMapper extends BaseMapper<Category, CategoryDTO> {

    @Autowired
    private CategoryRepository categoryRepository;

    @Override
    public Category toEntity(CategoryDTO categoryDTO) {
        if (categoryDTO == null) return null;

        Category category = null;

        if (categoryDTO.getId() != null) {
            category = categoryRepository.findById(categoryDTO.getId()).orElse(null);
        }

        if (category == null) {
            category = new Category();
        }

        category.setTitle(categoryDTO.getTitle());

        if (categoryDTO.getParentId() != null) {
            category.setParent(categoryRepository.findById(categoryDTO.getParentId()).orElse(null));
        }

        return category;
    }

    @Override
    public CategoryDTO toDto(Category category) {
        if (category == null) return null;

        CategoryDTO categoryDTO = new CategoryDTO();

        categoryDTO.setId(category.getId());
        categoryDTO.setTitle(category.getTitle());
        categoryDTO.setParentId(category.getParent() != null ? category.getParent().getId() : null);

        return categoryDTO;
    }
}
