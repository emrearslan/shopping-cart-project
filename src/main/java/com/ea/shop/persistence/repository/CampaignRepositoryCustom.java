package com.ea.shop.persistence.repository;

import com.ea.shop.persistence.dto.CampaignDTO;
import com.ea.shop.persistence.entity.Campaign;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface CampaignRepositoryCustom {

    List<Campaign> findCampaigns(CampaignDTO campaignDTO);

    Page<Campaign> findCampaignsPageable(CampaignDTO campaignDTO, Pageable pageable);
}
