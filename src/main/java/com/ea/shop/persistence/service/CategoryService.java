package com.ea.shop.persistence.service;

import com.ea.shop.persistence.dto.CategoryDTO;
import com.ea.shop.persistence.entity.Category;
import com.ea.shop.persistence.mapper.CategoryMapper;
import com.ea.shop.persistence.repository.CategoryRepository;
import com.ea.shop.persistence.validator.CategoryValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class CategoryService {

    @Autowired
    private CategoryRepository categoryRepository;

    @Autowired
    private CategoryMapper categoryMapper;

    @Autowired
    private CategoryValidator categoryValidator;

    public CategoryDTO findCategoryById(Long id) {
        Category category = categoryRepository.findById(id).orElse(null);
        return categoryMapper.toDto(category);
    }

    public Page<CategoryDTO> findCategory(CategoryDTO categoryDTO, Pageable pageable) {
        Page<Category> categoryDTOPage = categoryRepository.findCategoriesPageable(categoryDTO, pageable);
        return categoryDTOPage.map(categoryMapper.toDtoPage());
    }

    @Transactional
    public CategoryDTO save(CategoryDTO categoryDTO) {
        categoryValidator.saveCategoryValidator(categoryDTO);

        Category category = categoryMapper.toEntity(categoryDTO);
        Category result = categoryRepository.save(category);

        return categoryMapper.toDto(result);
    }

}
