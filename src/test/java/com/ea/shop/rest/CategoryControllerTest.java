package com.ea.shop.rest;

import com.ea.shop.application.ShoppingCartApplication;
import com.ea.shop.persistence.dto.CategoryDTO;
import com.ea.shop.persistence.dto.builder.CategoryDTOBuilder;
import com.ea.shop.persistence.service.CategoryService;
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

import java.util.Arrays;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest(classes = ShoppingCartApplication.class)
public class CategoryControllerTest {

    @InjectMocks
    private CategoryController categoryController;

    @Mock
    private CategoryService categoryService;

    private CategoryDTO categoryDTO;
    private MockMvc mockMvc;

    @BeforeEach
    public void setup() {
        categoryDTO = new CategoryDTOBuilder().id(10L).title("testCategory").doBuild();

        mockMvc = MockMvcBuilders.standaloneSetup(categoryController)
                .setCustomArgumentResolvers(new PageableHandlerMethodArgumentResolver())
                .setViewResolvers((viewName, locale) -> new MappingJackson2JsonView()).build();
    }

    @Test
    public void shouldFindCampaign() throws Exception {
        Page<CategoryDTO> campaignDTOPage = new PageImpl<>(Arrays.asList(categoryDTO));
        Mockito.when(categoryService.findCategory(Mockito.any(), Mockito.any())).thenReturn(campaignDTOPage);

        MvcResult result = mockMvc.perform(get("/categories"))
                .andExpect(status().isOk()).andReturn();
        MockHttpServletResponse response = result.getResponse();

        Assertions.assertEquals(response.getStatus(), 200);
        Assertions.assertTrue(response.getContentAsString().contains("testCategory"));
    }

    @Test
    public void shouldFindCampaignById() throws Exception {
        Mockito.when(categoryService.findCategoryById(Mockito.any())).thenReturn(categoryDTO);

        MvcResult result = mockMvc.perform(get("/categories/{id}", 10L))
                .andExpect(status().isOk()).andReturn();
        MockHttpServletResponse response = result.getResponse();

        Assertions.assertEquals(response.getStatus(), 200);
        Assertions.assertTrue(response.getContentAsString().contains("testCategory"));
    }

    @Test
    public void shouldSave() throws Exception {
        Mockito.when(categoryService.save(Mockito.any())).thenReturn(categoryDTO);

        MvcResult result = mockMvc.perform(post("/categories/save").content("{}")
                .contentType(MediaType.APPLICATION_JSON_UTF8_VALUE)).andExpect(status().isOk()).andReturn();
        MockHttpServletResponse response = result.getResponse();

        Assertions.assertEquals(response.getStatus(), 200);
        Assertions.assertTrue(response.getContentAsString().contains("testCategory"));
    }

}