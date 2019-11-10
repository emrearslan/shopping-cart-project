package com.ea.shop.persistence.mapper;

import com.ea.shop.persistence.dto.CouponDTO;
import com.ea.shop.persistence.entity.Coupon;
import com.ea.shop.persistence.mapper.base.BaseMapper;
import com.ea.shop.persistence.repository.CouponRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class CouponMapper extends BaseMapper<Coupon, CouponDTO> {

    @Autowired
    private CouponRepository couponRepository;

    @Override
    public Coupon toEntity(CouponDTO couponDTO) {
        if (couponDTO == null) return null;

        Coupon coupon = null;

        if (couponDTO.getId() != null) {
            coupon = couponRepository.findById(couponDTO.getId()).orElse(null);
        }

        if (coupon == null) coupon = new Coupon();

        coupon.setTitle(couponDTO.getTitle());
        coupon.setMinPurchaseAmount(couponDTO.getMinPurchaseAmount());
        coupon.setDiscountAmount(couponDTO.getDiscountAmount());
        coupon.setDiscountType(couponDTO.getDiscountType());

        return coupon;
    }

    @Override
    public CouponDTO toDto(Coupon coupon) {
        if (coupon == null) return null;

        CouponDTO couponDTO = new CouponDTO();
        couponDTO.setId(coupon.getId());
        couponDTO.setTitle(coupon.getTitle());
        couponDTO.setMinPurchaseAmount(coupon.getMinPurchaseAmount());
        couponDTO.setDiscountAmount(coupon.getDiscountAmount());
        couponDTO.setDiscountType(coupon.getDiscountType());

        return couponDTO;
    }
}
