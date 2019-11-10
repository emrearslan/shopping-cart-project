package com.ea.shop.persistence.validator;

import com.ea.shop.application.ShoppingCartApplication;
import com.ea.shop.exception.BusinessException;
import com.ea.shop.message.MessageService;
import com.ea.shop.persistence.dto.CouponDTO;
import com.ea.shop.persistence.dto.builder.CouponDTOBuilder;
import com.ea.shop.persistence.entity.DiscountType;
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
public class CouponValidatorTest {

    @InjectMocks
    private CouponValidator couponValidator;

    @Mock
    private BaseValidator baseValidator;

    @Mock
    private MessageService messageService;

    private CouponDTO couponDTO;

    @BeforeEach
    public void setup() {
        couponDTO = new CouponDTOBuilder().id(10L).title("coupon").minPurchaseAmount(new BigDecimal("100"))
                .discountAmount(new BigDecimal("50")).discountType(DiscountType.RATE).doBuild();
    }

    @Test
    public void shouldSaveCouponValidator() {
        couponValidator.saveCouponValidator(couponDTO);
        Mockito.verify(baseValidator).validateNullCheck(couponDTO);
    }

    @Test
    void shouldSaveCouponValidatorTitleNull() {
        couponDTO.setTitle(null);
        Mockito.when(messageService.getMessage(Mockito.any())).thenReturn("message");

        Assertions.assertThrows(BusinessException.class,
                () -> couponValidator.saveCouponValidator(couponDTO));
        Mockito.verify(baseValidator).validateNullCheck(couponDTO);
    }

    @Test
    void shouldSaveCouponValidatorMinPurchaseAmountNull() {
        couponDTO.setMinPurchaseAmount(null);
        Mockito.when(messageService.getMessage(Mockito.any())).thenReturn("message");

        Assertions.assertThrows(BusinessException.class,
                () -> couponValidator.saveCouponValidator(couponDTO));
        Mockito.verify(baseValidator).validateNullCheck(couponDTO);
    }

    @Test
    void shouldSaveCouponValidatorDiscountAmountNull() {
        couponDTO.setDiscountAmount(null);
        Mockito.when(messageService.getMessage(Mockito.any())).thenReturn("message");

        Assertions.assertThrows(BusinessException.class,
                () -> couponValidator.saveCouponValidator(couponDTO));
        Mockito.verify(baseValidator).validateNullCheck(couponDTO);
    }

    @Test
    void shouldSaveCouponValidatorDiscountTypeNull() {
        couponDTO.setDiscountType(null);
        Mockito.when(messageService.getMessage(Mockito.any())).thenReturn("message");

        Assertions.assertThrows(BusinessException.class,
                () -> couponValidator.saveCouponValidator(couponDTO));
        Mockito.verify(baseValidator).validateNullCheck(couponDTO);
    }

}