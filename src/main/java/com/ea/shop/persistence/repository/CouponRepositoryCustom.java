package com.ea.shop.persistence.repository;

import com.ea.shop.persistence.dto.CouponDTO;
import com.ea.shop.persistence.entity.Coupon;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface CouponRepositoryCustom {

    List<Coupon> findCoupons(CouponDTO couponDTO);

    Page<Coupon> findCouponsPageable(CouponDTO couponDTO, Pageable pageable);

}
