package com.ea.shop.persistence.validator;

import com.ea.shop.application.ShoppingCartApplication;
import com.ea.shop.exception.BusinessException;
import com.ea.shop.message.MessageService;
import com.ea.shop.persistence.dto.CampaignDTO;
import com.ea.shop.persistence.dto.builder.CampaignDTOBuilder;
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
public class CampaignValidatorTest {

    @InjectMocks
    private CampaignValidator campaignValidator;

    @Mock
    private BaseValidator baseValidator;

    @Mock
    private MessageService messageService;

    private CampaignDTO campaignDTO;

    @BeforeEach
    public void setup() {
        campaignDTO = new CampaignDTOBuilder().id(10L).title("campaign").category(1L).itemLimit(3)
                .discountAmount(new BigDecimal("500")).discountType(DiscountType.RATE).doBuild();
    }

    @Test
    public void shouldSaveCampaignValidator() {
        campaignValidator.saveCampaignValidator(campaignDTO);
        Mockito.verify(baseValidator).validateNullCheck(campaignDTO);
    }

    @Test
    void shouldSaveCampaignValidatorTitleNull() {
        campaignDTO.setTitle(null);
        Mockito.when(messageService.getMessage(Mockito.any())).thenReturn("message");

        Assertions.assertThrows(BusinessException.class,
                () -> campaignValidator.saveCampaignValidator(campaignDTO));
        Mockito.verify(baseValidator).validateNullCheck(campaignDTO);
    }

    @Test
    void shouldSaveCampaignValidatorCategoryNull() {
        campaignDTO.setCategoryId(null);
        Mockito.when(messageService.getMessage(Mockito.any())).thenReturn("message");

        Assertions.assertThrows(BusinessException.class,
                () -> campaignValidator.saveCampaignValidator(campaignDTO));
        Mockito.verify(baseValidator).validateNullCheck(campaignDTO);
    }

    @Test
    void shouldSaveCampaignValidatorDiscountAmountNull() {
        campaignDTO.setDiscountAmount(null);
        Mockito.when(messageService.getMessage(Mockito.any())).thenReturn("message");

        Assertions.assertThrows(BusinessException.class,
                () -> campaignValidator.saveCampaignValidator(campaignDTO));
        Mockito.verify(baseValidator).validateNullCheck(campaignDTO);
    }

    @Test
    void shouldSaveCampaignValidatorItemLimitNull() {
        campaignDTO.setItemLimit(null);
        Mockito.when(messageService.getMessage(Mockito.any())).thenReturn("message");

        Assertions.assertThrows(BusinessException.class,
                () -> campaignValidator.saveCampaignValidator(campaignDTO));
        Mockito.verify(baseValidator).validateNullCheck(campaignDTO);
    }

    @Test
    void shouldSaveCampaignValidatorDiscountTypeNull() {
        campaignDTO.setDiscountType(null);
        Mockito.when(messageService.getMessage(Mockito.any())).thenReturn("message");

        Assertions.assertThrows(BusinessException.class,
                () -> campaignValidator.saveCampaignValidator(campaignDTO));
        Mockito.verify(baseValidator).validateNullCheck(campaignDTO);
    }

}