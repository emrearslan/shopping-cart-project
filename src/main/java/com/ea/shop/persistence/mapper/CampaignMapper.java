package com.ea.shop.persistence.mapper;

import com.ea.shop.persistence.dto.CampaignDTO;
import com.ea.shop.persistence.entity.Campaign;
import com.ea.shop.persistence.mapper.base.BaseMapper;
import com.ea.shop.persistence.repository.CampaignRepository;
import com.ea.shop.persistence.repository.CategoryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class CampaignMapper extends BaseMapper<Campaign, CampaignDTO> {

    @Autowired
    private CampaignRepository campaignRepository;

    @Autowired
    private CategoryRepository categoryRepository;

    @Override
    public Campaign toEntity(CampaignDTO campaignDTO) {
        if (campaignDTO == null) return null;

        Campaign campaign = null;

        if (campaignDTO.getId() != null) {
            campaign = campaignRepository.findById(campaignDTO.getId()).orElse(null);
        }

        if (campaign == null) {
            campaign = new Campaign();
        }

        campaign.setTitle(campaign.getTitle());

        if(campaignDTO.getCategoryId() != null) {
            campaign.setCategory(categoryRepository.findById(campaignDTO.getCategoryId()).orElse(null));
        }

        campaign.setDiscountAmount(campaignDTO.getDiscountAmount());
        campaign.setItemLimit(campaignDTO.getItemLimit());
        campaign.setDiscountType(campaignDTO.getDiscountType());

        return campaign;
    }

    @Override
    public CampaignDTO toDto(Campaign campaign) {
        if(campaign == null) return null;

        CampaignDTO campaignDTO = new CampaignDTO();

        campaignDTO.setId(campaign.getId());
        campaignDTO.setTitle(campaign.getTitle());
        campaignDTO.setCategoryId(campaign.getCategory().getId());
        campaignDTO.setDiscountAmount(campaign.getDiscountAmount());
        campaignDTO.setItemLimit(campaign.getItemLimit());
        campaignDTO.setDiscountType(campaign.getDiscountType());

        return campaignDTO;
    }
}
