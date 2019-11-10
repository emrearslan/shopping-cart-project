package com.ea.shop.persistence.service;

import com.ea.shop.persistence.dto.CampaignDTO;
import com.ea.shop.persistence.entity.Campaign;
import com.ea.shop.persistence.mapper.CampaignMapper;
import com.ea.shop.persistence.repository.CampaignRepository;
import com.ea.shop.persistence.validator.CampaignValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class CampaignService {

    @Autowired
    private CampaignRepository campaignRepository;

    @Autowired
    private CampaignMapper campaignMapper;

    @Autowired
    private CampaignValidator campaignValidator;

    public CampaignDTO findCampaignById(Long id) {
        Campaign Campaign = campaignRepository.findById(id).orElse(null);
        return campaignMapper.toDto(Campaign);
    }

    public Page<CampaignDTO> findCampaign(CampaignDTO campaignDTO, Pageable pageable) {
        Page<Campaign> campaignPage = campaignRepository.findCampaignsPageable(campaignDTO, pageable);
        return campaignPage.map(campaignMapper.toDtoPage());
    }

    @Transactional
    public CampaignDTO save(CampaignDTO campaignDTO) {
        campaignValidator.saveCampaignValidator(campaignDTO);

        Campaign Campaign = campaignMapper.toEntity(campaignDTO);
        Campaign result = campaignRepository.save(Campaign);

        return campaignMapper.toDto(result);
    }

}
