package com.ea.shop.persistence.repository;

import com.ea.shop.persistence.entity.Campaign;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CampaignRepository extends CrudRepository<Campaign, Long>,
        CampaignRepositoryCustom, JpaSpecificationExecutor<Campaign> {
}
