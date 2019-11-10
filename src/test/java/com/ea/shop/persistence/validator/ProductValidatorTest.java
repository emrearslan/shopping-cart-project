package com.ea.shop.persistence.validator;

import com.ea.shop.application.ShoppingCartApplication;
import com.ea.shop.exception.BusinessException;
import com.ea.shop.message.MessageService;
import com.ea.shop.persistence.dto.ProductDTO;
import com.ea.shop.persistence.dto.builder.ProductDTOBuilder;
import com.ea.shop.persistence.validator.base.BaseValidator;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.boot.test.context.SpringBootTest;

import java.math.BigDecimal;

@SpringBootTest(classes = ShoppingCartApplication.class)
public class ProductValidatorTest {

    @InjectMocks
    private ProductValidator productValidator;

    @Mock
    private BaseValidator baseValidator;

    @Mock
    private MessageService messageService;

    private ProductDTO productDTO;

    @BeforeEach
    public void setup() {
        productDTO = new ProductDTOBuilder().id(10L).title("product").categoryId(1L)
                .price(new BigDecimal("500")).doBuild();
    }

    @Test
    public void shouldSaveCouponValidator() {
        productValidator.saveProductValidator(productDTO);
        Mockito.verify(baseValidator).validateNullCheck(productDTO);
    }

    @Test
    void shouldSaveCouponValidatorTitleNull() {
        productDTO.setTitle(null);
        Mockito.when(messageService.getMessage(Mockito.any())).thenReturn("message");

        Assertions.assertThrows(BusinessException.class,
                () -> productValidator.saveProductValidator(productDTO));
        Mockito.verify(baseValidator).validateNullCheck(productDTO);
    }

    @Test
    void shouldSaveCouponValidatorPriceNull() {
        productDTO.setPrice(null);
        Mockito.when(messageService.getMessage(Mockito.any())).thenReturn("message");

        Assertions.assertThrows(BusinessException.class,
                () -> productValidator.saveProductValidator(productDTO));
        Mockito.verify(baseValidator).validateNullCheck(productDTO);
    }

    @Test
    void shouldSaveCouponValidatorCategoryIdNull() {
        productDTO.setCategoryId(null);
        Mockito.when(messageService.getMessage(Mockito.any())).thenReturn("message");

        Assertions.assertThrows(BusinessException.class,
                () -> productValidator.saveProductValidator(productDTO));
        Mockito.verify(baseValidator).validateNullCheck(productDTO);
    }

}