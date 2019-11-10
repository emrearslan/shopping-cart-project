package com.ea.shop.rest;

import com.ea.shop.persistence.dto.ProductDTO;
import com.ea.shop.persistence.service.ProductService;
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

@RequestMapping("/products")
@RestController
@Api(value = "productController")
public class ProductController {

    @Autowired
    private ProductService productService;

    @GetMapping(value = "", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    @ApiOperation(value = "Find Product by Search Criteria", response = Iterable.class)
    @ApiImplicitParams({
            @ApiImplicitParam(name = "page", dataType = "int", paramType = "query", value = "Results page you want to retrieve (0..N)"),
            @ApiImplicitParam(name = "size", dataType = "int", paramType = "query", value = "Number of records per page."),
            @ApiImplicitParam(name = "sort", allowMultiple = true, dataType = "string", paramType = "query", value = "Sorting criteria in the format: property(,asc|desc). "
                    + "Default sort order is ascending. " + "Multiple sort criteria are supported.")})
    ResponseEntity<Page<ProductDTO>> findProduct(ProductDTO productDTO, @ApiIgnore Pageable pageable) {
        Page<ProductDTO> output = productService.findProduct(productDTO, pageable);
        return new ResponseEntity<>(output, HttpStatus.OK);
    }

    @GetMapping(value = "/{id}", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    @ApiOperation(value = "Find Product by Id", response = Iterable.class)
    ResponseEntity<ProductDTO> findProductById(@PathVariable Long id) {
        ProductDTO output = productService.findProductById(id);
        return new ResponseEntity<>(output, HttpStatus.OK);
    }

    @PostMapping(value = "/save", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    @ApiOperation(value = "Save Product", response = Iterable.class)
    ResponseEntity<ProductDTO> save(@RequestBody ProductDTO productDTO) {
        ProductDTO output = productService.save(productDTO);
        return new ResponseEntity<>(output, HttpStatus.OK);
    }

}
