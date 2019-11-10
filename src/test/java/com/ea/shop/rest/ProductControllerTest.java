package com.ea.shop.rest;

import com.ea.shop.application.ShoppingCartApplication;
import com.ea.shop.persistence.dto.ProductDTO;
import com.ea.shop.persistence.dto.builder.ProductDTOBuilder;
import com.ea.shop.persistence.entity.builder.ProductBuilder;
import com.ea.shop.persistence.service.ProductService;
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
public class ProductControllerTest {

    @InjectMocks
    private ProductController productController;

    @Mock
    private ProductService productService;

    private ProductDTO productDTO;
    private MockMvc mockMvc;

    @BeforeEach
    public void setup() {
        productDTO = new ProductDTOBuilder().id(10L).title("testProduct").categoryId(5L)
                .price(new BigDecimal("500")).doBuild();

        mockMvc = MockMvcBuilders.standaloneSetup(productController)
                .setCustomArgumentResolvers(new PageableHandlerMethodArgumentResolver())
                .setViewResolvers((viewName, locale) -> new MappingJackson2JsonView()).build();
    }

    @Test
    public void shouldFindProduct() throws Exception {
        Page<ProductDTO> productDTOPage = new PageImpl<>(Arrays.asList(productDTO));
        Mockito.when(productService.findProduct(Mockito.any(), Mockito.any())).thenReturn(productDTOPage);

        MvcResult result = mockMvc.perform(get("/products"))
                .andExpect(status().isOk()).andReturn();
        MockHttpServletResponse response = result.getResponse();

        Assertions.assertEquals(response.getStatus(), 200);
        Assertions.assertTrue(response.getContentAsString().contains("testProduct"));
    }

    @Test
    public void shouldFindProductById() throws Exception {
        Mockito.when(productService.findProductById(Mockito.any())).thenReturn(productDTO);

        MvcResult result = mockMvc.perform(get("/products/{id}", 10L))
                .andExpect(status().isOk()).andReturn();
        MockHttpServletResponse response = result.getResponse();

        Assertions.assertEquals(response.getStatus(), 200);
        Assertions.assertTrue(response.getContentAsString().contains("testProduct"));
    }

    @Test
    public void shouldSave() throws Exception {
        Mockito.when(productService.save(Mockito.any())).thenReturn(productDTO);

        MvcResult result = mockMvc.perform(post("/products/save").content("{}")
                .contentType(MediaType.APPLICATION_JSON_UTF8_VALUE)).andExpect(status().isOk()).andReturn();
        MockHttpServletResponse response = result.getResponse();

        Assertions.assertEquals(response.getStatus(), 200);
        Assertions.assertTrue(response.getContentAsString().contains("testProduct"));
    }

}