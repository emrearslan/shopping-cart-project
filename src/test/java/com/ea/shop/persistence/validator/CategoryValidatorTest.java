package com.ea.shop.persistence.validator;

import com.ea.shop.application.ShoppingCartApplication;
import com.ea.shop.exception.BusinessException;
import com.ea.shop.message.MessageService;
import com.ea.shop.persistence.dto.CategoryDTO;
import com.ea.shop.persistence.dto.builder.CategoryDTOBuilder;
import com.ea.shop.persistence.validator.base.BaseValidator;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest(classes = ShoppingCartApplication.class)
public class CategoryValidatorTest {

    @InjectMocks
    private CategoryValidator categoryValidator;

    @Mock
    private BaseValidator baseValidator;

    @Mock
    private MessageService messageService;

    private CategoryDTO categoryDTO;

    @BeforeEach
    public void setup() {
        categoryDTO = new CategoryDTOBuilder().id(10L).title("cat1").doBuild();
    }

    @Test
    public void shouldSaveCategoryValidator() {
        categoryValidator.saveCategoryValidator(categoryDTO);
        Mockito.verify(baseValidator).validateNullCheck(categoryDTO);
    }

    @Test
    void shouldSaveCategoryValidatorTitleNull() {
        categoryDTO.setTitle(null);
        Mockito.when(messageService.getMessage(Mockito.any())).thenReturn("message");

        Assertions.assertThrows(BusinessException.class,
                () -> categoryValidator.saveCategoryValidator(categoryDTO));
        Mockito.verify(baseValidator).validateNullCheck(categoryDTO);
    }
}