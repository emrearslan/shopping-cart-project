package com.ea.shop.rest;

import com.ea.shop.persistence.dto.CouponDTO;
import com.ea.shop.persistence.service.CouponService;
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

@RequestMapping("/coupons")
@RestController
@Api(value = "couponController")
public class CouponController {

    @Autowired
    private CouponService couponService;

    @GetMapping(value = "", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    @ApiOperation(value = "Find Coupon by Search Criteria", response = Iterable.class)
    @ApiImplicitParams({
            @ApiImplicitParam(name = "page", dataType = "int", paramType = "query", value = "Results page you want to retrieve (0..N)"),
            @ApiImplicitParam(name = "size", dataType = "int", paramType = "query", value = "Number of records per page."),
            @ApiImplicitParam(name = "sort", allowMultiple = true, dataType = "string", paramType = "query", value = "Sorting criteria in the format: property(,asc|desc). "
                    + "Default sort order is ascending. " + "Multiple sort criteria are supported.")})
    ResponseEntity<Page<CouponDTO>> findCoupon(CouponDTO couponDTO, @ApiIgnore Pageable pageable) {
        Page<CouponDTO> output = couponService.findCoupon(couponDTO, pageable);
        return new ResponseEntity<>(output, HttpStatus.OK);
    }

    @GetMapping(value = "/{id}", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    @ApiOperation(value = "Find Coupon by Id", response = Iterable.class)
    ResponseEntity<CouponDTO> findCouponById(@PathVariable Long id) {
        CouponDTO output = couponService.findCouponById(id);
        return new ResponseEntity<>(output, HttpStatus.OK);
    }

    @PostMapping(value = "/save", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    @ApiOperation(value = "Save Coupon", response = Iterable.class)
    ResponseEntity<CouponDTO> save(@RequestBody CouponDTO couponDTO) {
        CouponDTO output = couponService.save(couponDTO);
        return new ResponseEntity<>(output, HttpStatus.OK);
    }

}
