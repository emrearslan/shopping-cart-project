package com.ea.shop.persistence.repository.impl;

import com.ea.shop.persistence.dto.CategoryDTO;
import com.ea.shop.persistence.entity.Category;
import com.ea.shop.persistence.repository.CategoryRepository;
import com.ea.shop.persistence.repository.CategoryRepositoryCustom;
import com.ea.shop.util.StringUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;

import javax.persistence.criteria.Predicate;
import java.util.List;

public class CategoryRepositoryImpl implements CategoryRepositoryCustom {

    @Autowired
    private CategoryRepository categoryRepository;

    @Override
    public List<Category> findCategories(CategoryDTO categoryDTO) {
        return categoryRepository.findAll(createCategorySpecification(categoryDTO));
    }

    @Override
    public Page<Category> findCategoriesPageable(CategoryDTO categoryDTO, Pageable pageable) {
        return categoryRepository.findAll(createCategorySpecification(categoryDTO), pageable);
    }

    private Specification<Category> createCategorySpecification(CategoryDTO categoryDTO) {
        return (Specification<Category>) (root, query, builder) -> {
            Predicate mainPredicate = builder.conjunction();

            if (categoryDTO.getId() != null) {
                Predicate predicate = builder.equal(root.get(Category._ID), categoryDTO.getId());
                mainPredicate = builder.and(mainPredicate, predicate);
            }

            if (StringUtil.isNotEmpty(categoryDTO.getTitle())) {
                Predicate predicate = builder.like(builder.lower(builder.upper(root.get(Category._TITLE))),
                        StringUtil.like(categoryDTO.getTitle()));
                mainPredicate = builder.and(mainPredicate, predicate);
            }

            if (categoryDTO.getParentId() != null) {
                Predicate predicate = builder.equal(root.get(Category._PARENT).get(Category._ID), categoryDTO.getParentId());
                mainPredicate = builder.and(mainPredicate, predicate);
            }

            return mainPredicate;
        };
    }

}
