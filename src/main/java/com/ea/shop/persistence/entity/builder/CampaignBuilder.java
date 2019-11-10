package com.ea.shop.persistence.entity.builder;

import com.ea.shop.persistence.entity.Campaign;
import com.ea.shop.persistence.entity.Category;
import com.ea.shop.persistence.entity.DiscountType;

import javax.persistence.*;
import java.math.BigDecimal;

public class CampaignBuilder {

    private Long id;
    private String title;
    private Category category;
    private BigDecimal discountAmount;
    private Integer itemLimit;
    private DiscountType discountType;

    public Campaign doBuild() {
        Campaign campaign = new Campaign();
        campaign.setId(id);
        campaign.setTitle(title);
        campaign.setCategory(category);
        campaign.setDiscountAmount(discountAmount);
        campaign.setItemLimit(itemLimit);
        campaign.setDiscountType(discountType);
        return campaign;
    }

    public CampaignBuilder id(Long id) {
        this.id = id;
        return this;
    }

    public CampaignBuilder title(String title) {
        this.title = title;
        return this;
    }

    public CampaignBuilder category(Category category) {
        this.category = category;
        return this;
    }

    public CampaignBuilder discountAmount(BigDecimal discountAmount) {
        this.discountAmount = discountAmount;
        return this;
    }

    public CampaignBuilder itemLimit(Integer itemLimit) {
        this.itemLimit = itemLimit;
        return this;
    }

    public CampaignBuilder discountType(DiscountType discountType) {
        this.discountType = discountType;
        return this;
    }

}
