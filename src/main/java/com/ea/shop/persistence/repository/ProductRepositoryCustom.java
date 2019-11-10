package com.ea.shop.persistence.repository;

import com.ea.shop.persistence.dto.ProductDTO;
import com.ea.shop.persistence.entity.Product;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface ProductRepositoryCustom {

    List<Product> findProducts(ProductDTO productDTO);

    Page<Product> findProductsPageable(ProductDTO productDTO, Pageable pageable);

}
