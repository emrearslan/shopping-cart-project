package com.ea.shop.persistence.dto.builder;

import com.ea.shop.persistence.dto.CampaignDTO;
import com.ea.shop.persistence.entity.DiscountType;

import java.math.BigDecimal;

public class CampaignDTOBuilder {

    private Long id;
    private String title;
    private Long categoryId;
    private BigDecimal discountAmount = BigDecimal.ZERO;
    private Integer itemLimit = 1;
    private DiscountType discountType = DiscountType.RATE;

    public CampaignDTO doBuild() {
        CampaignDTO campaignDTO = new CampaignDTO();
        campaignDTO.setId(id);
        campaignDTO.setTitle(title);
        campaignDTO.setCategoryId(categoryId);
        campaignDTO.setDiscountAmount(discountAmount);
        campaignDTO.setItemLimit(itemLimit);
        campaignDTO.setDiscountType(discountType);
        return campaignDTO;
    }

    public CampaignDTOBuilder id(Long id) {
        this.id = id;
        return this;
    }

    public CampaignDTOBuilder title(String title) {
        this.title = title;
        return this;
    }

    public CampaignDTOBuilder category(Long categoryId) {
        this.categoryId = categoryId;
        return this;
    }

    public CampaignDTOBuilder discountAmount(BigDecimal discountAmount) {
        this.discountAmount = discountAmount;
        return this;
    }

    public CampaignDTOBuilder itemLimit(Integer itemLimit) {
        this.itemLimit = itemLimit;
        return this;
    }

    public CampaignDTOBuilder discountType(DiscountType discountType) {
        this.discountType = discountType;
        return this;
    }

}
