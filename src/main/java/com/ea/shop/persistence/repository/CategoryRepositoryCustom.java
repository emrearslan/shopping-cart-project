package com.ea.shop.persistence.repository;

import com.ea.shop.persistence.dto.CategoryDTO;
import com.ea.shop.persistence.entity.Category;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface CategoryRepositoryCustom {

    List<Category> findCategories(CategoryDTO categoryDTO);

    Page<Category> findCategoriesPageable(CategoryDTO categoryDTO, Pageable pageable);

}
