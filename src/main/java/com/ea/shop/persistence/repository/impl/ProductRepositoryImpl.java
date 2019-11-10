package com.ea.shop.persistence.repository.impl;

import com.ea.shop.persistence.dto.ProductDTO;
import com.ea.shop.persistence.entity.Category;
import com.ea.shop.persistence.entity.Product;
import com.ea.shop.persistence.repository.ProductRepository;
import com.ea.shop.persistence.repository.ProductRepositoryCustom;
import com.ea.shop.util.StringUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;

import javax.persistence.criteria.Predicate;
import java.util.List;

public class ProductRepositoryImpl implements ProductRepositoryCustom {

    @Autowired
    private ProductRepository productRepository;

    @Override
    public List<Product> findProducts(ProductDTO productDTO) {
        return productRepository.findAll(createProductSpecification(productDTO));
    }

    @Override
    public Page<Product> findProductsPageable(ProductDTO productDTO, Pageable pageable) {
        return productRepository.findAll(createProductSpecification(productDTO), pageable);
    }

    private Specification<Product> createProductSpecification(ProductDTO productDTO) {
        return (Specification<Product>) (root, query, builder) -> {
            Predicate mainPredicate = builder.conjunction();

            if (productDTO.getId() != null) {
                Predicate predicate = builder.equal(root.get(Product._ID), productDTO.getId());
                mainPredicate = builder.and(mainPredicate, predicate);
            }

            if (StringUtil.isNotEmpty(productDTO.getTitle())) {
                Predicate predicate = builder.like(builder.lower(builder.upper(root.get(Product._TITLE))),
                        StringUtil.like(productDTO.getTitle()));
                mainPredicate = builder.and(mainPredicate, predicate);
            }

            if (productDTO.getPrice() != null) {
                Predicate predicate = builder.equal(root.get(Product._PRICE), productDTO.getPrice());
                mainPredicate = builder.and(mainPredicate, predicate);
            }

            if (productDTO.getCategoryId() != null) {
                Predicate predicate = builder.equal(root.get(Product._CATEGORY).get(Category._ID), productDTO.getCategoryId());
                mainPredicate = builder.and(mainPredicate, predicate);
            }

            return mainPredicate;
        };
    }
}
