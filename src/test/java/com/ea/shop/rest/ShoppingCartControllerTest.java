package com.ea.shop.rest;

import com.ea.shop.application.ShoppingCartApplication;
import com.ea.shop.persistence.dto.CartItemDTO;
import com.ea.shop.persistence.dto.ShoppingCartDTO;
import com.ea.shop.persistence.dto.builder.CartItemDTOBuilder;
import com.ea.shop.persistence.service.ShoppingCartService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.web.PageableHandlerMethodArgumentResolver;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.servlet.view.json.MappingJackson2JsonView;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.HashSet;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest(classes = ShoppingCartApplication.class)
public class ShoppingCartControllerTest {

    @InjectMocks
    private ShoppingCartController shoppingCartController;

    @Mock
    private ShoppingCartService shoppingCartService;

    private MockMvc mockMvc;
    private ShoppingCartDTO shoppingCartDTO;

    @BeforeEach
    public void setup() {
        CartItemDTO cartItemDTO = new CartItemDTOBuilder().id(10L).productId(15L)
                .totalPrice(new BigDecimal("500")).doBuild();
        shoppingCartDTO = new ShoppingCartDTO();
        shoppingCartDTO.setCartItems(new HashSet<>(Arrays.asList(cartItemDTO)));

        mockMvc = MockMvcBuilders.standaloneSetup(shoppingCartController)
                .setCustomArgumentResolvers(new PageableHandlerMethodArgumentResolver())
                .setViewResolvers((viewName, locale) -> new MappingJackson2JsonView()).build();
    }

    @Test
    public void shouldPrint() throws Exception {
        Mockito.when(shoppingCartService.print()).thenReturn(shoppingCartDTO);

        MvcResult result = mockMvc.perform(get("/shoppingCart/print")
                .contentType(MediaType.APPLICATION_JSON_UTF8_VALUE)).andExpect(status().isOk()).andReturn();
        MockHttpServletResponse response = result.getResponse();

        Assertions.assertEquals(response.getStatus(), 200);
        Assertions.assertTrue(response.getContentAsString().contains("500"));
    }

    @Test
    public void shouldAddItem() throws Exception {
        Mockito.when(shoppingCartService.addItem(Mockito.any())).thenReturn(shoppingCartDTO);

        MvcResult result = mockMvc.perform(post("/shoppingCart/addItem").content("{}")
                .contentType(MediaType.APPLICATION_JSON_UTF8_VALUE)).andExpect(status().isOk()).andReturn();
        MockHttpServletResponse response = result.getResponse();

        Assertions.assertEquals(response.getStatus(), 200);
        Assertions.assertTrue(response.getContentAsString().contains("500"));
    }

    @Test
    public void shouldRemoveItem() throws Exception {
        Mockito.when(shoppingCartService.removeItem(Mockito.any())).thenReturn(shoppingCartDTO);

        MvcResult result = mockMvc.perform(post("/shoppingCart/removeItem").content("{}")
                .contentType(MediaType.APPLICATION_JSON_UTF8_VALUE)).andExpect(status().isOk()).andReturn();
        MockHttpServletResponse response = result.getResponse();

        Assertions.assertEquals(response.getStatus(), 200);
        Assertions.assertTrue(response.getContentAsString().contains("500"));
    }

    @Test
    public void shouldApplyCampaign() throws Exception {
        Mockito.when(shoppingCartService.applyCampaign(Mockito.any())).thenReturn(shoppingCartDTO);

        MvcResult result = mockMvc.perform(post("/shoppingCart/applyCampaign").content("{}")
                .contentType(MediaType.APPLICATION_JSON_UTF8_VALUE)).andExpect(status().isOk()).andReturn();
        MockHttpServletResponse response = result.getResponse();

        Assertions.assertEquals(response.getStatus(), 200);
        Assertions.assertTrue(response.getContentAsString().contains("500"));
    }

    @Test
    public void shouldApplyCoupon() throws Exception {
        Mockito.when(shoppingCartService.applyCoupon(Mockito.any())).thenReturn(shoppingCartDTO);

        MvcResult result = mockMvc.perform(post("/shoppingCart/applyCoupon").content("{}")
                .contentType(MediaType.APPLICATION_JSON_UTF8_VALUE)).andExpect(status().isOk()).andReturn();
        MockHttpServletResponse response = result.getResponse();

        Assertions.assertEquals(response.getStatus(), 200);
        Assertions.assertTrue(response.getContentAsString().contains("500"));
    }

    @Test
    public void shouldApplyDelivery() throws Exception {
        Mockito.when(shoppingCartService.applyDelivery(Mockito.any())).thenReturn(shoppingCartDTO);

        MvcResult result = mockMvc.perform(post("/shoppingCart/applyDelivery").content("{}")
                .contentType(MediaType.APPLICATION_JSON_UTF8_VALUE)).andExpect(status().isOk()).andReturn();
        MockHttpServletResponse response = result.getResponse();

        Assertions.assertEquals(response.getStatus(), 200);
        Assertions.assertTrue(response.getContentAsString().contains("500"));
    }

}