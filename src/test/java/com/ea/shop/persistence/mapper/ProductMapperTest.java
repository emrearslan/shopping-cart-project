package com.ea.shop.persistence.mapper;

import com.ea.shop.application.ShoppingCartApplication;
import com.ea.shop.persistence.dto.ProductDTO;
import com.ea.shop.persistence.dto.builder.ProductDTOBuilder;
import com.ea.shop.persistence.entity.Category;
import com.ea.shop.persistence.entity.Product;
import com.ea.shop.persistence.entity.builder.CategoryBuilder;
import com.ea.shop.persistence.entity.builder.ProductBuilder;
import com.ea.shop.persistence.repository.CategoryRepository;
import com.ea.shop.persistence.repository.ProductRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.boot.test.context.SpringBootTest;

import java.math.BigDecimal;
import java.util.Optional;

@SpringBootTest(classes = ShoppingCartApplication.class)
public class ProductMapperTest {

    @InjectMocks
    private ProductMapper productMapper;

    @Mock
    private ProductRepository productRepository;

    @Mock
    private CategoryRepository categoryRepository;

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
    public void shouldToEntityReturnNull() {
        Product resultEntity = productMapper.toEntity(null);
        Assertions.assertNull(resultEntity);
    }

    @Test
    public void shouldToEntityHasId() {
        Mockito.when(productRepository.findById(Mockito.anyLong())).thenReturn(Optional.ofNullable(product));
        Mockito.when(categoryRepository.findById(Mockito.anyLong())).thenReturn(Optional.ofNullable(category));

        Product resultEntity = productMapper.toEntity(productDTO);

        Assertions.assertEquals(resultEntity.getId(), product.getId());
        Assertions.assertEquals(resultEntity.getCategory(), category);
        Assertions.assertEquals(resultEntity.getTitle(), product.getTitle());
        Assertions.assertEquals(resultEntity.getPrice(), product.getPrice());
    }

    @Test
    public void shouldToEntityNotFoundProduct() {
        Mockito.when(productRepository.findById(Mockito.anyLong())).thenReturn(Optional.ofNullable(null));
        Mockito.when(categoryRepository.findById(Mockito.anyLong())).thenReturn(Optional.ofNullable(category));

        Product resultEntity = productMapper.toEntity(productDTO);
        Assertions.assertNull(resultEntity.getId());
    }

    @Test
    public void shouldToEntityNewProduct() {
        productDTO.setId(null);
        Mockito.when(categoryRepository.findById(Mockito.anyLong())).thenReturn(Optional.ofNullable(category));

        Product resultEntity = productMapper.toEntity(productDTO);
        Assertions.assertNull(resultEntity.getId());
    }

    @Test
    public void shouldToEntityNullCategory() {
        Mockito.when(productRepository.findById(Mockito.anyLong())).thenReturn(Optional.ofNullable(product));
        Mockito.when(categoryRepository.findById(Mockito.anyLong())).thenReturn(Optional.ofNullable(null));

        Product resultEntity = productMapper.toEntity(productDTO);
        Assertions.assertNull(resultEntity.getCategory());
    }

    @Test
    public void shouldToDtoReturnNull() {
        ProductDTO resultDTO = productMapper.toDto(null);
        Assertions.assertNull(resultDTO);
    }

    @Test
    public void shouldToDto() {
        ProductDTO resultDTO = productMapper.toDto(product);

        Assertions.assertEquals(resultDTO.getId(), productDTO.getId());
        Assertions.assertEquals(resultDTO.getCategoryId(), productDTO.getCategoryId());
        Assertions.assertEquals(resultDTO.getTitle(), productDTO.getTitle());
        Assertions.assertEquals(resultDTO.getPrice(), productDTO.getPrice());
    }


}