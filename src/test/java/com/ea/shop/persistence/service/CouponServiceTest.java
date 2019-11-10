package com.ea.shop.persistence.service;

import com.ea.shop.application.ShoppingCartApplication;
import com.ea.shop.persistence.dto.CouponDTO;
import com.ea.shop.persistence.dto.builder.CouponDTOBuilder;
import com.ea.shop.persistence.entity.Coupon;
import com.ea.shop.persistence.entity.DiscountType;
import com.ea.shop.persistence.entity.builder.CouponBuilder;
import com.ea.shop.persistence.mapper.CouponMapper;
import com.ea.shop.persistence.repository.CouponRepository;
import com.ea.shop.persistence.validator.CouponValidator;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.Optional;
import java.util.function.Function;

@SpringBootTest(classes = ShoppingCartApplication.class)
public class CouponServiceTest {

    @InjectMocks
    private CouponService couponService;

    @Mock
    private CouponRepository couponRepository;

    @Mock
    private CouponMapper couponMapper;

    @Mock
    private CouponValidator couponValidator;

    private Coupon coupon;
    private CouponDTO couponDTO;

    @BeforeEach
    public void setup() {
        coupon = new CouponBuilder().id(10L).title("coupon").minPurchaseAmount(new BigDecimal("100"))
                .discountAmount(new BigDecimal("50")).discountType(DiscountType.RATE).doBuild();

        couponDTO = new CouponDTOBuilder().id(10L).title("coupon").minPurchaseAmount(new BigDecimal("100"))
                .discountAmount(new BigDecimal("50")).discountType(DiscountType.RATE).doBuild();
    }

    @Test
    public void shouldFindCouponById() {
        Mockito.when(couponRepository.findById(Mockito.anyLong())).thenReturn(Optional.ofNullable(coupon));
        Mockito.when(couponMapper.toDto(Mockito.any())).thenReturn(couponDTO);

        couponService.findCouponById(10L);
    }

    @Test
    public void shouldFindCoupon() {
        Page<Coupon> couponPage = new PageImpl<>(Arrays.asList(coupon));

        Mockito.when(couponRepository.findCouponsPageable(Mockito.any(), Mockito.any())).thenReturn(couponPage);
        Mockito.when(couponMapper.toDtoPage()).thenReturn(new Function<Coupon, CouponDTO>() {
            @Override
            public CouponDTO apply(Coupon coupon) {
                return couponDTO;
            }
        });

        couponService.findCoupon(couponDTO, null);
    }

    @Test
    public void shouldSave() {
        Mockito.when(couponMapper.toEntity(Mockito.any())).thenReturn(coupon);
        Mockito.when(couponRepository.save(Mockito.any())).thenReturn(coupon);
        Mockito.when(couponMapper.toDto(Mockito.any())).thenReturn(couponDTO);

        couponService.save(couponDTO);
        Mockito.verify(couponValidator).saveCouponValidator(Mockito.any());
    }

}