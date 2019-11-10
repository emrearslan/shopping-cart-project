package com.ea.shop.persistence.mapper;

import com.ea.shop.persistence.dto.ProductDTO;
import com.ea.shop.persistence.entity.Product;
import com.ea.shop.persistence.mapper.base.BaseMapper;
import com.ea.shop.persistence.repository.CategoryRepository;
import com.ea.shop.persistence.repository.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class ProductMapper extends BaseMapper<Product, ProductDTO> {

    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private CategoryRepository categoryRepository;

    @Override
    public Product toEntity(ProductDTO productDTO) {
        if (productDTO == null) return null;

        Product product = null;

        if (productDTO.getId() != null) {
            product = productRepository.findById(productDTO.getId()).orElse(null);
        }

        if (product == null) {
            product = new Product();
        }

        product.setTitle(productDTO.getTitle());
        product.setPrice(productDTO.getPrice());

        if (productDTO.getCategoryId() != null) {
            product.setCategory(categoryRepository.findById(productDTO.getCategoryId()).orElse(null));
        }

        return product;
    }

    @Override
    public ProductDTO toDto(Product product) {
        if (product == null) return null;

        ProductDTO productDTO = new ProductDTO();

        productDTO.setId(product.getId());
        productDTO.setTitle(product.getTitle());
        productDTO.setPrice(product.getPrice());
        productDTO.setCategoryId(product.getCategory().getId());

        return productDTO;
    }
}
