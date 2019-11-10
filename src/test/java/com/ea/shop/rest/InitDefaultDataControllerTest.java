package com.ea.shop.rest;

import com.ea.shop.application.ShoppingCartApplication;
import com.ea.shop.persistence.repository.*;
import com.ea.shop.persistence.service.ShoppingCartService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.web.PageableHandlerMethodArgumentResolver;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.servlet.view.json.MappingJackson2JsonView;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest(classes = ShoppingCartApplication.class)
public class InitDefaultDataControllerTest {
    
    @InjectMocks
    private InitDefaultDataController initDefaultDataController;

    @Mock
    private CategoryRepository categoryRepository;

    @Mock
    private ProductRepository productRepository;

    @Mock
    private CampaignRepository campaignRepository;

    @Mock
    private CouponRepository couponRepository;

    @Mock
    private ShoppingCartRepository shoppingCartRepository;

    @Mock
    private ShoppingCartService shoppingCartService;

    private MockMvc mockMvc;

    @BeforeEach
    public void setup() {
        mockMvc = MockMvcBuilders.standaloneSetup(initDefaultDataController)
                .setCustomArgumentResolvers(new PageableHandlerMethodArgumentResolver())
                .setViewResolvers((viewName, locale) -> new MappingJackson2JsonView()).build();
    }

    @Test
    public void shouldInitParamData() throws Exception {
        MvcResult result = mockMvc.perform(get("/initDefaultData/onlyParamData")
                .contentType(MediaType.APPLICATION_JSON_UTF8_VALUE)).andExpect(status().isOk()).andReturn();
        MockHttpServletResponse response = result.getResponse();

        Assertions.assertEquals(response.getStatus(), 200);
    }

    @Test
    public void shouldInitFullData() throws Exception {
        MvcResult result = mockMvc.perform(get("/initDefaultData/initFullData")
                .contentType(MediaType.APPLICATION_JSON_UTF8_VALUE)).andExpect(status().isOk()).andReturn();
        MockHttpServletResponse response = result.getResponse();

        Assertions.assertEquals(response.getStatus(), 200);
    }

}