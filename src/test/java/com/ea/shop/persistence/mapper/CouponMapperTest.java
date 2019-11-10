package com.ea.shop.persistence.mapper;

import com.ea.shop.application.ShoppingCartApplication;
import com.ea.shop.persistence.dto.CouponDTO;
import com.ea.shop.persistence.dto.builder.CouponDTOBuilder;
import com.ea.shop.persistence.entity.Coupon;
import com.ea.shop.persistence.entity.DiscountType;
import com.ea.shop.persistence.entity.builder.CouponBuilder;
import com.ea.shop.persistence.repository.CouponRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.boot.test.context.SpringBootTest;

import java.math.BigDecimal;
import java.util.Optional;

@SpringBootTest(classes = ShoppingCartApplication.class)
public class CouponMapperTest {

    @InjectMocks
    private CouponMapper couponMapper;

    @Mock
    private CouponRepository couponRepository;

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
    public void shouldToEntityReturnNull() {
        Coupon resultEntity = couponMapper.toEntity(null);
        Assertions.assertNull(resultEntity);
    }

    @Test
    public void shouldToEntityHasId() {
        Mockito.when(couponRepository.findById(Mockito.anyLong())).thenReturn(Optional.ofNullable(coupon));
        Coupon resultEntity = couponMapper.toEntity(couponDTO);

        Assertions.assertEquals(resultEntity.getId(), coupon.getId());
        Assertions.assertEquals(resultEntity.getTitle(), coupon.getTitle());
        Assertions.assertEquals(resultEntity.getMinPurchaseAmount(), coupon.getMinPurchaseAmount());
        Assertions.assertEquals(resultEntity.getDiscountAmount(), coupon.getDiscountAmount());
        Assertions.assertEquals(resultEntity.getDiscountType(), coupon.getDiscountType());
    }

    @Test
    public void shouldToEntityNotFoundCoupon() {
        Mockito.when(couponRepository.findById(Mockito.anyLong())).thenReturn(Optional.ofNullable(null));
        Coupon resultEntity = couponMapper.toEntity(couponDTO);
        Assertions.assertNull(resultEntity.getId());
    }

    @Test
    public void shouldToEntityNewCoupon() {
        couponDTO.setId(null);
        Coupon resultEntity = couponMapper.toEntity(couponDTO);
        Assertions.assertNull(resultEntity.getId());
    }

    @Test
    public void shouldToDtoReturnNull() {
        CouponDTO resultDTO = couponMapper.toDto(null);
        Assertions.assertNull(resultDTO);
    }

    @Test
    public void shouldToDto() {
        CouponDTO resultDTO = couponMapper.toDto(coupon);

        Assertions.assertEquals(resultDTO.getId(), couponDTO.getId());
        Assertions.assertEquals(resultDTO.getTitle(), couponDTO.getTitle());
        Assertions.assertEquals(resultDTO.getMinPurchaseAmount(), couponDTO.getMinPurchaseAmount());
        Assertions.assertEquals(resultDTO.getDiscountAmount(), couponDTO.getDiscountAmount());
        Assertions.assertEquals(resultDTO.getDiscountType(), couponDTO.getDiscountType());
    }

}