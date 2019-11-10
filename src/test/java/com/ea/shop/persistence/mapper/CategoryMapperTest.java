package com.ea.shop.persistence.mapper;

import com.ea.shop.application.ShoppingCartApplication;
import com.ea.shop.persistence.dto.CategoryDTO;
import com.ea.shop.persistence.dto.builder.CategoryDTOBuilder;
import com.ea.shop.persistence.entity.Category;
import com.ea.shop.persistence.entity.builder.CategoryBuilder;
import com.ea.shop.persistence.repository.CategoryRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Optional;

@SpringBootTest(classes = ShoppingCartApplication.class)
public class CategoryMapperTest {

    @InjectMocks
    private CategoryMapper categoryMapper;

    @Mock
    private CategoryRepository categoryRepository;

    private Category category;
    private CategoryDTO categoryDTO;

    @BeforeEach
    public void setup() {
        category = new CategoryBuilder().id(10L).title("cat1").doBuild();
        categoryDTO = new CategoryDTOBuilder().id(10L).title("cat1").doBuild();
    }

    @Test
    public void shouldToEntityReturnNull() {
        Category resultEntity = categoryMapper.toEntity(null);
        Assertions.assertNull(resultEntity);
    }

    @Test
    public void shouldToEntityHasId() {
        Mockito.when(categoryRepository.findById(Mockito.anyLong())).thenReturn(Optional.ofNullable(category));

        Category resultEntity = categoryMapper.toEntity(categoryDTO);
        Assertions.assertEquals(resultEntity.getId(), category.getId());
        Assertions.assertEquals(resultEntity.getTitle(), category.getTitle());
        Assertions.assertEquals(resultEntity.getParent(), category.getParent());
    }

    @Test
    public void shouldToEntityNotFoundCategory() {
        Mockito.when(categoryRepository.findById(Mockito.anyLong())).thenReturn(Optional.ofNullable(null));

        Category resultEntity = categoryMapper.toEntity(categoryDTO);
        Assertions.assertNull(resultEntity.getId());
    }

    @Test
    public void shouldToEntityNotNewCategory() {
        categoryDTO.setId(null);

        Category resultEntity = categoryMapper.toEntity(categoryDTO);
        Assertions.assertNull(resultEntity.getId());
    }

    @Test
    public void shouldToEntityHasParent() {
        Category parent = new CategoryBuilder().id(15L).title("parentCat").doBuild();
        categoryDTO.setParentId(parent.getId());

        Mockito.when(categoryRepository.findById(category.getId())).thenReturn(Optional.ofNullable(category));
        Mockito.when(categoryRepository.findById(parent.getId())).thenReturn(Optional.ofNullable(parent));

        Category resultEntity = categoryMapper.toEntity(categoryDTO);
        Assertions.assertNotNull(resultEntity.getParent());
    }

    @Test
    public void shouldToDtoReturnNull() {
        CategoryDTO resultDTO = categoryMapper.toDto(null);
        Assertions.assertNull(resultDTO);
    }

    @Test
    public void shouldToDto() {
        CategoryDTO resultDTO = categoryMapper.toDto(category);

        Assertions.assertEquals(resultDTO.getId(), categoryDTO.getId());
        Assertions.assertEquals(resultDTO.getTitle(), categoryDTO.getTitle());
        Assertions.assertEquals(resultDTO.getParentId(), categoryDTO.getParentId());
    }

    @Test
    public void shouldToDtoHasParent() {
        Category parent = new CategoryBuilder().id(15L).title("parentCat").doBuild();
        category.setParent(parent);

        CategoryDTO resultDTO = categoryMapper.toDto(category);

        Assertions.assertEquals(resultDTO.getId(), categoryDTO.getId());
        Assertions.assertEquals(resultDTO.getTitle(), categoryDTO.getTitle());
        Assertions.assertEquals(resultDTO.getParentId(), parent.getId());
    }


}