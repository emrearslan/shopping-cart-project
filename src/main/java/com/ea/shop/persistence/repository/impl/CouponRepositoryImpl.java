package com.ea.shop.persistence.repository.impl;

import com.ea.shop.persistence.dto.CouponDTO;
import com.ea.shop.persistence.entity.Coupon;
import com.ea.shop.persistence.repository.CouponRepository;
import com.ea.shop.persistence.repository.CouponRepositoryCustom;
import com.ea.shop.util.StringUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;

import javax.persistence.criteria.Predicate;
import java.util.List;

public class CouponRepositoryImpl implements CouponRepositoryCustom {

    @Autowired
    private CouponRepository couponRepository;

    @Override
    public List<Coupon> findCoupons(CouponDTO couponDTO) {
        return null;
    }

    @Override
    public Page<Coupon> findCouponsPageable(CouponDTO couponDTO, Pageable pageable) {
        return couponRepository.findAll(createCouponSpecification(couponDTO), pageable);
    }

    private Specification<Coupon> createCouponSpecification(CouponDTO couponDTO) {
        return (Specification<Coupon>) (root, query, builder) -> {
            Predicate mainPredicate = builder.conjunction();

            if (couponDTO.getId() != null) {
                Predicate predicate = builder.equal(root.get(Coupon._ID), couponDTO.getId());
                mainPredicate = builder.and(mainPredicate, predicate);
            }

            if (StringUtil.isNotEmpty(couponDTO.getTitle())) {
                Predicate predicate = builder.like(builder.lower(builder.upper(root.get(Coupon._TITLE))),
                        StringUtil.like(couponDTO.getTitle()));
                mainPredicate = builder.and(mainPredicate, predicate);
            }

            if(couponDTO.getMinPurchaseAmount() != null) {
                Predicate predicate = builder.equal(root.get(Coupon._MINPURCHASEAMOUNT), couponDTO.getMinPurchaseAmount());
                mainPredicate = builder.and(mainPredicate, predicate);
            }

            if(couponDTO.getDiscountAmount() != null) {
                Predicate predicate = builder.equal(root.get(Coupon._DISCOUNTAMOUNT), couponDTO.getDiscountAmount());
                mainPredicate = builder.and(mainPredicate, predicate);
            }

            if(couponDTO.getDiscountType() != null) {
                Predicate predicate = builder.equal(root.get(Coupon._DISCOUNTTYPE), couponDTO.getDiscountType());
                mainPredicate = builder.and(mainPredicate, predicate);
            }

            return mainPredicate;
        };
    }

}
