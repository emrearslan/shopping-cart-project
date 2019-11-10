package com.ea.shop.persistence.service;

import com.ea.shop.application.ShoppingCartApplication;
import com.ea.shop.persistence.dto.CategoryDTO;
import com.ea.shop.persistence.dto.builder.CategoryDTOBuilder;
import com.ea.shop.persistence.entity.Category;
import com.ea.shop.persistence.entity.builder.CategoryBuilder;
import com.ea.shop.persistence.mapper.CategoryMapper;
import com.ea.shop.persistence.repository.CategoryRepository;
import com.ea.shop.persistence.validator.CategoryValidator;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;

import java.util.Arrays;
import java.util.Optional;
import java.util.function.Function;

@SpringBootTest(classes = ShoppingCartApplication.class)
public class CategoryServiceTest {

    @InjectMocks
    private CategoryService categoryService;

    @Mock
    private CategoryRepository categoryRepository;

    @Mock
    private CategoryMapper categoryMapper;

    @Mock
    private CategoryValidator categoryValidator;

    private Category category;
    private CategoryDTO categoryDTO;

    @BeforeEach
    public void setup() {
        category = new CategoryBuilder().id(10L).title("cat1").doBuild();
        categoryDTO = new CategoryDTOBuilder().id(10L).title("cat1").doBuild();
    }

    @Test
    public void shouldFindCategoryById() {
        Mockito.when(categoryRepository.findById(Mockito.anyLong())).thenReturn(Optional.ofNullable(category));
        Mockito.when(categoryMapper.toDto(Mockito.any())).thenReturn(categoryDTO);

        categoryService.findCategoryById(10L);
    }

    @Test
    public void shouldFindCategory() {
        Page<Category> categoryPage = new PageImpl<>(Arrays.asList(category));

        Mockito.when(categoryRepository.findCategoriesPageable(Mockito.any(), Mockito.any())).thenReturn(categoryPage);
        Mockito.when(categoryMapper.toDtoPage()).thenReturn(new Function<Category, CategoryDTO>() {
            @Override
            public CategoryDTO apply(Category category) {
                return categoryDTO;
            }
        });

        categoryService.findCategory(categoryDTO, null);
    }

    @Test
    public void shouldSave() {
        Mockito.when(categoryMapper.toEntity(Mockito.any())).thenReturn(category);
        Mockito.when(categoryRepository.save(Mockito.any())).thenReturn(category);
        Mockito.when(categoryMapper.toDto(Mockito.any())).thenReturn(categoryDTO);

        categoryService.save(categoryDTO);
        Mockito.verify(categoryValidator).saveCategoryValidator(Mockito.any());
    }

}