package com.ea.shop.persistence.service;

import com.ea.shop.application.ShoppingCartApplication;
import com.ea.shop.persistence.dto.ProductDTO;
import com.ea.shop.persistence.dto.builder.ProductDTOBuilder;
import com.ea.shop.persistence.entity.Category;
import com.ea.shop.persistence.entity.Product;
import com.ea.shop.persistence.entity.builder.CategoryBuilder;
import com.ea.shop.persistence.entity.builder.ProductBuilder;
import com.ea.shop.persistence.mapper.ProductMapper;
import com.ea.shop.persistence.repository.ProductRepository;
import com.ea.shop.persistence.validator.ProductValidator;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.Optional;
import java.util.function.Function;

@SpringBootTest(classes = ShoppingCartApplication.class)
public class ProductServiceTest {

    @InjectMocks
    private ProductService productService;

    @Mock
    private ProductRepository productRepository;

    @Mock
    private ProductMapper productMapper;

    @Mock
    private ProductValidator productValidator;

    private Product product;
    private ProductDTO productDTO;
    private Category category;

    @BeforeEach
    public void setup() {
        category = new CategoryBuilder().id(1L).title("cat").doBuild();

        product = new ProductBuilder().id(10L).title("product").category(category)
                .price(new BigDecimal("500")).doBuild();

        productDTO = new ProductDTOBuilder().id(10L).title("product").categoryId(category.getId())
                .price(new BigDecimal("500")).doBuild();
    }

    @Test
    public void shouldFindProductById() {
        Mockito.when(productRepository.findById(Mockito.anyLong())).thenReturn(Optional.ofNullable(product));
        Mockito.when(productMapper.toDto(Mockito.any())).thenReturn(productDTO);

        productService.findProductById(10L);
    }

    @Test
    public void shouldFindProduct() {
        Page<Product> productPage = new PageImpl<>(Arrays.asList(product));

        Mockito.when(productRepository.findProductsPageable(Mockito.any(), Mockito.any())).thenReturn(productPage);
        Mockito.when(productMapper.toDtoPage()).thenReturn(new Function<Product, ProductDTO>() {
            @Override
            public ProductDTO apply(Product product) {
                return productDTO;
            }
        });

        productService.findProduct(productDTO, null);
    }

    @Test
    public void shouldSave() {
        Mockito.when(productMapper.toEntity(Mockito.any())).thenReturn(product);
        Mockito.when(productRepository.save(Mockito.any())).thenReturn(product);
        Mockito.when(productMapper.toDto(Mockito.any())).thenReturn(productDTO);

        productService.save(productDTO);
        Mockito.verify(productValidator).saveProductValidator(Mockito.any());
    }

}