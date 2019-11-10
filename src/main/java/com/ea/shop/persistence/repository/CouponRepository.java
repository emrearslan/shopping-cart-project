package com.ea.shop.persistence.repository;

import com.ea.shop.persistence.entity.Coupon;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.repository.CrudRepository;

public interface CouponRepository extends CrudRepository<Coupon, Long>,
        CouponRepositoryCustom, JpaSpecificationExecutor<Coupon> {
}
