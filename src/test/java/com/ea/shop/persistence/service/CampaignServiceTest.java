package com.ea.shop.persistence.service;

import com.ea.shop.application.ShoppingCartApplication;
import com.ea.shop.persistence.dto.CampaignDTO;
import com.ea.shop.persistence.dto.builder.CampaignDTOBuilder;
import com.ea.shop.persistence.entity.Campaign;
import com.ea.shop.persistence.entity.Category;
import com.ea.shop.persistence.entity.DiscountType;
import com.ea.shop.persistence.entity.builder.CampaignBuilder;
import com.ea.shop.persistence.entity.builder.CategoryBuilder;
import com.ea.shop.persistence.mapper.CampaignMapper;
import com.ea.shop.persistence.repository.CampaignRepository;
import com.ea.shop.persistence.validator.CampaignValidator;
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
public class CampaignServiceTest {

    @InjectMocks
    private CampaignService campaignService;

    @Mock
    private CampaignRepository campaignRepository;

    @Mock
    private CampaignMapper campaignMapper;

    @Mock
    private CampaignValidator campaignValidator;

    private Campaign campaign;
    private CampaignDTO campaignDTO;
    private Category category;

    @BeforeEach
    public void setup() {
        category = new CategoryBuilder().id(1L).title("cat1").doBuild();

        campaign = new CampaignBuilder().id(10L).title("campaign").category(category).itemLimit(3)
                .discountAmount(new BigDecimal("500")).discountType(DiscountType.RATE).doBuild();
        campaignDTO = new CampaignDTOBuilder().id(10L).title("campaign").category(category.getId()).itemLimit(3)
                .discountAmount(new BigDecimal("500")).discountType(DiscountType.RATE).doBuild();
    }

    @Test
    public void shouldFindCampaignById() {
        Mockito.when(campaignRepository.findById(Mockito.anyLong())).thenReturn(Optional.ofNullable(campaign));
        Mockito.when(campaignMapper.toDto(Mockito.any())).thenReturn(campaignDTO);

        campaignService.findCampaignById(10L);
    }

    @Test
    public void shouldFindCampaign() {
        Page<Campaign> campaignPage = new PageImpl<>(Arrays.asList(campaign));

        Mockito.when(campaignRepository.findCampaignsPageable(Mockito.any(), Mockito.any())).thenReturn(campaignPage);
        Mockito.when(campaignMapper.toDtoPage()).thenReturn(new Function<Campaign, CampaignDTO>() {
            @Override
            public CampaignDTO apply(Campaign campaign) {
                return campaignDTO;
            }
        });

        campaignService.findCampaign(campaignDTO, null);
    }

    @Test
    public void shouldSave() {
        Mockito.when(campaignMapper.toEntity(Mockito.any())).thenReturn(campaign);
        Mockito.when(campaignRepository.save(Mockito.any())).thenReturn(campaign);
        Mockito.when(campaignMapper.toDto(Mockito.any())).thenReturn(campaignDTO);

        campaignService.save(campaignDTO);
        Mockito.verify(campaignValidator).saveCampaignValidator(Mockito.any());
    }

}