package com.ea.shop.rest;

import com.ea.shop.persistence.dto.CategoryDTO;
import com.ea.shop.persistence.service.CategoryService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import springfox.documentation.annotations.ApiIgnore;

@RequestMapping("/categories")
@RestController
@Api(value = "categoryController")
public class CategoryController {

    @Autowired
    private CategoryService categoryService;

    @GetMapping(value = "", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    @ApiOperation(value = "Find Category by Search Criteria", response = Iterable.class)
    @ApiImplicitParams({
            @ApiImplicitParam(name = "page", dataType = "int", paramType = "query", value = "Results page you want to retrieve (0..N)"),
            @ApiImplicitParam(name = "size", dataType = "int", paramType = "query", value = "Number of records per page."),
            @ApiImplicitParam(name = "sort", allowMultiple = true, dataType = "string", paramType = "query", value = "Sorting criteria in the format: property(,asc|desc). "
                    + "Default sort order is ascending. " + "Multiple sort criteria are supported.")})
    ResponseEntity<Page<CategoryDTO>> findCategory(CategoryDTO categoryDTO, @ApiIgnore Pageable pageable) {
        Page<CategoryDTO> output = categoryService.findCategory(categoryDTO, pageable);
        return new ResponseEntity<>(output, HttpStatus.OK);
    }

    @GetMapping(value = "/{id}", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    @ApiOperation(value = "Find Category by Id", response = Iterable.class)
    ResponseEntity<CategoryDTO> findCategoryById(@PathVariable Long id) {
        CategoryDTO output = categoryService.findCategoryById(id);
        return new ResponseEntity<>(output, HttpStatus.OK);
    }

    @PostMapping(value = "/save", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    @ApiOperation(value = "Save Category", response = Iterable.class)
    ResponseEntity<CategoryDTO> save(@RequestBody CategoryDTO categoryDTO) {
        CategoryDTO output = categoryService.save(categoryDTO);
        return new ResponseEntity<>(output, HttpStatus.OK);
    }

}
