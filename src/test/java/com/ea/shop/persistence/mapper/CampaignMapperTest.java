package com.ea.shop.persistence.mapper;

import com.ea.shop.application.ShoppingCartApplication;
import com.ea.shop.persistence.dto.CampaignDTO;
import com.ea.shop.persistence.dto.builder.CampaignDTOBuilder;
import com.ea.shop.persistence.entity.Campaign;
import com.ea.shop.persistence.entity.Category;
import com.ea.shop.persistence.entity.DiscountType;
import com.ea.shop.persistence.entity.builder.CampaignBuilder;
import com.ea.shop.persistence.entity.builder.CategoryBuilder;
import com.ea.shop.persistence.repository.CampaignRepository;
import com.ea.shop.persistence.repository.CategoryRepository;
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
public class CampaignMapperTest {

    @InjectMocks
    private CampaignMapper campaignMapper;

    @Mock
    private CampaignRepository campaignRepository;

    @Mock
    private CategoryRepository categoryRepository;

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
    public void shouldToEntityReturnNull() {
        Campaign resultEntity = campaignMapper.toEntity(null);
        Assertions.assertNull(resultEntity);
    }

    @Test
    public void shouldToEntityHasId() {
        Mockito.when(campaignRepository.findById(Mockito.anyLong())).thenReturn(Optional.ofNullable(campaign));
        Mockito.when(categoryRepository.findById(Mockito.anyLong())).thenReturn(Optional.ofNullable(category));

        Campaign resultEntity = campaignMapper.toEntity(campaignDTO);

        Assertions.assertEquals(resultEntity.getId(), campaign.getId());
        Assertions.assertEquals(resultEntity.getCategory(), campaign.getCategory());
        Assertions.assertEquals(resultEntity.getItemLimit(), campaign.getItemLimit());
        Assertions.assertEquals(resultEntity.getDiscountAmount(), campaign.getDiscountAmount());
        Assertions.assertEquals(resultEntity.getDiscountType(), campaign.getDiscountType());
    }

    @Test
    public void shouldToEntityNotFoundCampaign() {
        Mockito.when(campaignRepository.findById(Mockito.anyLong())).thenReturn(Optional.ofNullable(null));
        Mockito.when(categoryRepository.findById(Mockito.anyLong())).thenReturn(Optional.ofNullable(category));

        Campaign resultEntity = campaignMapper.toEntity(campaignDTO);
        Assertions.assertNull(resultEntity.getId());
    }

    @Test
    public void shouldToEntityNewCampaign() {
        campaignDTO.setId(null);
        Mockito.when(categoryRepository.findById(Mockito.anyLong())).thenReturn(Optional.ofNullable(category));

        Campaign resultEntity = campaignMapper.toEntity(campaignDTO);
        Assertions.assertNull(resultEntity.getId());
    }

    @Test
    public void shouldToEntityNullCategory() {
        Mockito.when(campaignRepository.findById(Mockito.anyLong())).thenReturn(Optional.ofNullable(campaign));
        Mockito.when(categoryRepository.findById(Mockito.anyLong())).thenReturn(Optional.ofNullable(null));

        Campaign resultEntity = campaignMapper.toEntity(campaignDTO);
        Assertions.assertNull(resultEntity.getCategory());
    }

    @Test
    public void shouldToDtoReturnNull() {
        CampaignDTO resultDTO = campaignMapper.toDto(null);
        Assertions.assertNull(resultDTO);
    }

    @Test
    public void shouldToDto() {
        CampaignDTO resultDTO = campaignMapper.toDto(campaign);

        Assertions.assertEquals(resultDTO.getId(), campaignDTO.getId());
        Assertions.assertEquals(resultDTO.getCategoryId(), campaignDTO.getCategoryId());
        Assertions.assertEquals(resultDTO.getItemLimit(), campaignDTO.getItemLimit());
        Assertions.assertEquals(resultDTO.getDiscountAmount(), campaignDTO.getDiscountAmount());
        Assertions.assertEquals(resultDTO.getDiscountType(), campaignDTO.getDiscountType());
    }

}