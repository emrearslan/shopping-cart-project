package com.ea.shop.persistence.repository.impl;

import com.ea.shop.persistence.dto.CampaignDTO;
import com.ea.shop.persistence.entity.Campaign;
import com.ea.shop.persistence.entity.Category;
import com.ea.shop.persistence.repository.CampaignRepository;
import com.ea.shop.persistence.repository.CampaignRepositoryCustom;
import com.ea.shop.util.StringUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;

import javax.persistence.criteria.Predicate;
import java.util.List;

public class CampaignRepositoryImpl implements CampaignRepositoryCustom {

    @Autowired
    private CampaignRepository campaignRepository;

    @Override
    public List<Campaign> findCampaigns(CampaignDTO campaignDTO) {
        return campaignRepository.findAll(createCampaignSpecification(campaignDTO));
    }

    @Override
    public Page<Campaign> findCampaignsPageable(CampaignDTO campaignDTO, Pageable pageable) {
        return campaignRepository.findAll(createCampaignSpecification(campaignDTO), pageable);
    }

    private Specification<Campaign> createCampaignSpecification(CampaignDTO campaignDTO) {
        return (Specification<Campaign>) (root, query, builder) -> {
            Predicate mainPredicate = builder.conjunction();

            if (campaignDTO.getId() != null) {
                Predicate predicate = builder.equal(root.get(Campaign._ID), campaignDTO.getId());
                mainPredicate = builder.and(mainPredicate, predicate);
            }

            if (StringUtil.isNotEmpty(campaignDTO.getTitle())) {
                Predicate predicate = builder.like(builder.lower(builder.upper(root.get(Campaign._TITLE))),
                        StringUtil.like(campaignDTO.getTitle()));
                mainPredicate = builder.and(mainPredicate, predicate);
            }

            if (campaignDTO.getCategoryId() != null) {
                Predicate predicate = builder.equal(root.get(Campaign._CATEGORY).get(Category._ID), campaignDTO.getCategoryId());
                mainPredicate = builder.and(mainPredicate, predicate);
            }

            if(campaignDTO.getDiscountAmount() != null) {
                Predicate predicate = builder.equal(root.get(Campaign._DISCOUNTAMOUNT), campaignDTO.getDiscountAmount());
                mainPredicate = builder.and(mainPredicate, predicate);
            }

            if(campaignDTO.getItemLimit() != null) {
                Predicate predicate = builder.equal(root.get(Campaign._ITEMLIMIT), campaignDTO.getItemLimit());
                mainPredicate = builder.and(mainPredicate, predicate);
            }

            if(campaignDTO.getDiscountType() != null) {
                Predicate predicate = builder.equal(root.get(Campaign._DISCOUNTTYPE), campaignDTO.getDiscountType());
                mainPredicate = builder.and(mainPredicate, predicate);
            }

            return mainPredicate;
        };
    }

}
