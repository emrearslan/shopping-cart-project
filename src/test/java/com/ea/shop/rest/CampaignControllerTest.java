package com.ea.shop.rest;

import com.ea.shop.application.ShoppingCartApplication;
import com.ea.shop.persistence.dto.CampaignDTO;
import com.ea.shop.persistence.dto.builder.CampaignDTOBuilder;
import com.ea.shop.persistence.entity.DiscountType;
import com.ea.shop.persistence.service.CampaignService;
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
public class CampaignControllerTest {

    @InjectMocks
    private CampaignController campaignController;

    @Mock
    private CampaignService campaignService;

    private CampaignDTO campaignDTO;
    private MockMvc mockMvc;

    @BeforeEach
    public void setup() {
        campaignDTO = new CampaignDTOBuilder().id(10L).title("testCampaign").itemLimit(3)
                .discountAmount(new BigDecimal("500")).discountType(DiscountType.RATE).doBuild();

        mockMvc = MockMvcBuilders.standaloneSetup(campaignController)
                .setCustomArgumentResolvers(new PageableHandlerMethodArgumentResolver())
                .setViewResolvers((viewName, locale) -> new MappingJackson2JsonView()).build();
    }

    @Test
    public void shouldFindCampaign() throws Exception {
        Page<CampaignDTO> campaignDTOPage = new PageImpl<>(Arrays.asList(campaignDTO));
        Mockito.when(campaignService.findCampaign(Mockito.any(), Mockito.any())).thenReturn(campaignDTOPage);

        MvcResult result = mockMvc.perform(get("/campaigns"))
                .andExpect(status().isOk()).andReturn();
        MockHttpServletResponse response = result.getResponse();

        Assertions.assertEquals(response.getStatus(), 200);
        Assertions.assertTrue(response.getContentAsString().contains("testCampaign"));
    }

    @Test
    public void shouldFindCampaignById() throws Exception {
        Mockito.when(campaignService.findCampaignById(Mockito.any())).thenReturn(campaignDTO);

        MvcResult result = mockMvc.perform(get("/campaigns/{id}", 10L))
                .andExpect(status().isOk()).andReturn();
        MockHttpServletResponse response = result.getResponse();

        Assertions.assertEquals(response.getStatus(), 200);
        Assertions.assertTrue(response.getContentAsString().contains("testCampaign"));
    }

    @Test
    public void shouldSave() throws Exception {
        Mockito.when(campaignService.save(Mockito.any())).thenReturn(campaignDTO);

        MvcResult result = mockMvc.perform(post("/campaigns/save").content("{}")
                .contentType(MediaType.APPLICATION_JSON_UTF8_VALUE)).andExpect(status().isOk()).andReturn();
        MockHttpServletResponse response = result.getResponse();

        Assertions.assertEquals(response.getStatus(), 200);
        Assertions.assertTrue(response.getContentAsString().contains("testCampaign"));
    }

}