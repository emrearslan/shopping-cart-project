package com.ea.shop.persistence.service;

import com.ea.shop.persistence.dto.ProductDTO;
import com.ea.shop.persistence.entity.Product;
import com.ea.shop.persistence.mapper.ProductMapper;
import com.ea.shop.persistence.repository.ProductRepository;
import com.ea.shop.persistence.validator.ProductValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class ProductService {

    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private ProductMapper productMapper;

    @Autowired
    private ProductValidator productValidator;

    public ProductDTO findProductById(Long id) {
        Product product = productRepository.findById(id).orElse(null);
        return productMapper.toDto(product);
    }

    public Page<ProductDTO> findProduct(ProductDTO productDTO, Pageable pageable) {
        Page<Product> productDTOPage = productRepository.findProductsPageable(productDTO, pageable);
        return productDTOPage.map(productMapper.toDtoPage());
    }

    @Transactional
    public ProductDTO save(ProductDTO productDTO) {
        productValidator.saveProductValidator(productDTO);

        Product product = productMapper.toEntity(productDTO);
        Product result = productRepository.save(product);

        return productMapper.toDto(result);
    }

}
