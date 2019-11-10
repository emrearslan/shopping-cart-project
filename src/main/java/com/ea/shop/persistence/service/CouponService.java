package com.ea.shop.persistence.service;

import com.ea.shop.persistence.dto.CouponDTO;
import com.ea.shop.persistence.entity.Coupon;
import com.ea.shop.persistence.mapper.CouponMapper;
import com.ea.shop.persistence.repository.CouponRepository;
import com.ea.shop.persistence.validator.CouponValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class CouponService {

    @Autowired
    private CouponRepository couponRepository;

    @Autowired
    private CouponMapper couponMapper;

    @Autowired
    private CouponValidator couponValidator;

    public CouponDTO findCouponById(Long id) {
        Coupon coupon = couponRepository.findById(id).orElse(null);
        return couponMapper.toDto(coupon);
    }

    public Page<CouponDTO> findCoupon(CouponDTO couponDTO, Pageable pageable) {
        Page<Coupon> couponDTOPage = couponRepository.findCouponsPageable(couponDTO, pageable);
        return couponDTOPage.map(couponMapper.toDtoPage());
    }

    @Transactional
    public CouponDTO save(CouponDTO couponDTO) {
        couponValidator.saveCouponValidator(couponDTO);

        Coupon coupon = couponMapper.toEntity(couponDTO);
        Coupon result = couponRepository.save(coupon);

        return couponMapper.toDto(result);
    }

}
