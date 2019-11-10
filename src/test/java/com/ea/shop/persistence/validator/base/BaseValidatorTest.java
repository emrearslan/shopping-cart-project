package com.ea.shop.persistence.validator.base;

import com.ea.shop.application.ShoppingCartApplication;
import com.ea.shop.exception.BusinessException;
import com.ea.shop.message.MessageService;
import com.ea.shop.persistence.dto.base.SimpleLongDTO;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest(classes = ShoppingCartApplication.class)
public class BaseValidatorTest {

    @InjectMocks
    private BaseValidator baseValidator;

    @Mock
    private MessageService messageService;

    @Test
    public void shouldValidateNullCheck() {
        baseValidator.validateNullCheck(new SimpleLongDTO());
    }

    @Test
    public void shouldValidateNullCheckNullObject() {
        Mockito.when(messageService.getMessage(Mockito.any())).thenReturn("message");
        Assertions.assertThrows(BusinessException.class,
                () -> baseValidator.validateNullCheck(null));
    }

}