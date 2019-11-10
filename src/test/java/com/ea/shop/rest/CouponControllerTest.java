package com.ea.shop.rest;

import com.ea.shop.application.ShoppingCartApplication;
import com.ea.shop.persistence.dto.CouponDTO;
import com.ea.shop.persistence.dto.builder.CouponDTOBuilder;
import com.ea.shop.persistence.entity.DiscountType;
import com.ea.shop.persistence.service.CouponService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.web.PageableHandlerMethodArgumentResolver;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.servlet.view.json.MappingJackson2JsonView;

import java.math.BigDecimal;
import java.util.Arrays;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest(classes = ShoppingCartApplication.class)
public class CouponControllerTest {

    @InjectMocks
    private CouponController couponController;

    @Mock
    private CouponService couponService;

    private CouponDTO couponDTO;
    private MockMvc mockMvc;

    @BeforeEach
    public void setup() {
        couponDTO = new CouponDTOBuilder().id(10L).title("testCoupon").minPurchaseAmount(new BigDecimal("100"))
                .discountAmount(new BigDecimal("50")).discountType(DiscountType.RATE).doBuild();

        mockMvc = MockMvcBuilders.standaloneSetup(couponController)
                .setCustomArgumentResolvers(new PageableHandlerMethodArgumentResolver())
                .setViewResolvers((viewName, locale) -> new MappingJackson2JsonView()).build();
    }

    @Test
    public void shouldFindCoupon() throws Exception {
        Page<CouponDTO> couponDTOPage = new PageImpl<>(Arrays.asList(couponDTO));
        Mockito.when(couponService.findCoupon(Mockito.any(), Mockito.any())).thenReturn(couponDTOPage);

        MvcResult result = mockMvc.perform(get("/coupons"))
                .andExpect(status().isOk()).andReturn();
        MockHttpServletResponse response = result.getResponse();

        Assertions.assertEquals(response.getStatus(), 200);
        Assertions.assertTrue(response.getContentAsString().contains("testCoupon"));
    }

    @Test
    public void shouldFindCouponById() throws Exception {
        Mockito.when(couponService.findCouponById(Mockito.any())).thenReturn(couponDTO);

        MvcResult result = mockMvc.perform(get("/coupons/{id}", 10L))
                .andExpect(status().isOk()).andReturn();
        MockHttpServletResponse response = result.getResponse();

        Assertions.assertEquals(response.getStatus(), 200);
        Assertions.assertTrue(response.getContentAsString().contains("testCoupon"));
    }

    @Test
    public void shouldSave() throws Exception {
        Mockito.when(couponService.save(Mockito.any())).thenReturn(couponDTO);

        MvcResult result = mockMvc.perform(post("/coupons/save").content("{}")
                .contentType(MediaType.APPLICATION_JSON_UTF8_VALUE)).andExpect(status().isOk()).andReturn();
        MockHttpServletResponse response = result.getResponse();

        Assertions.assertEquals(response.getStatus(), 200);
        Assertions.assertTrue(response.getContentAsString().contains("testCoupon"));
    }

}