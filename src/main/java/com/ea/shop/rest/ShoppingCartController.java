package com.ea.shop.rest;

import com.ea.shop.persistence.dto.CartItemRefDTO;
import com.ea.shop.persistence.dto.DeliveryCostDTO;
import com.ea.shop.persistence.dto.ShoppingCartDTO;
import com.ea.shop.persistence.dto.base.SimpleLongDTO;
import com.ea.shop.persistence.service.ShoppingCartService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RequestMapping("/shoppingCart")
@RestController
@Api(value = "shoppingCartController")
public class ShoppingCartController {

    @Autowired
    private ShoppingCartService shoppingCartService;

    @GetMapping(value = "/print", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    @ApiOperation(value = "Print Last State For Shopping Cart", response = Iterable.class)
    ResponseEntity<ShoppingCartDTO> print() {
        ShoppingCartDTO output = shoppingCartService.print();
        return new ResponseEntity<>(output, HttpStatus.OK);
    }

    @PostMapping(value = "/addItem", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    @ApiOperation(value = "Add Item", response = Iterable.class)
    ResponseEntity<ShoppingCartDTO> addItem(@RequestBody CartItemRefDTO cartItemRefDTO) {
        ShoppingCartDTO output = shoppingCartService.addItem(cartItemRefDTO);
        return new ResponseEntity<>(output, HttpStatus.OK);
    }

    @PostMapping(value = "/removeItem", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    @ApiOperation(value = "Remove Item", response = Iterable.class)
    ResponseEntity<ShoppingCartDTO> removeItem(@RequestBody CartItemRefDTO cartItemRefDTO) {
        ShoppingCartDTO output = shoppingCartService.removeItem(cartItemRefDTO);
        return new ResponseEntity<>(output, HttpStatus.OK);
    }

    @PostMapping(value = "/applyCampaign", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    @ApiOperation(value = "Apply Campaign By Campaign Id", response = Iterable.class)
    ResponseEntity<ShoppingCartDTO> applyCampaign(@RequestBody SimpleLongDTO simpleLongDTO) {
        ShoppingCartDTO output = shoppingCartService.applyCampaign(simpleLongDTO);
        return new ResponseEntity<>(output, HttpStatus.OK);
    }

    @PostMapping(value = "/applyCoupon", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    @ApiOperation(value = "Apply Coupon By Coupon Id", response = Iterable.class)
    ResponseEntity<ShoppingCartDTO> applyCoupon(@RequestBody SimpleLongDTO simpleLongDTO) {
        ShoppingCartDTO output = shoppingCartService.applyCoupon(simpleLongDTO);
        return new ResponseEntity<>(output, HttpStatus.OK);
    }

    @PostMapping(value = "/applyDelivery", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    @ApiOperation(value = "Apply Delivery", response = Iterable.class)
    ResponseEntity<ShoppingCartDTO> applyDelivery(@RequestBody DeliveryCostDTO deliveryCostDTO) {
        ShoppingCartDTO output = shoppingCartService.applyDelivery(deliveryCostDTO);
        return new ResponseEntity<>(output, HttpStatus.OK);
    }

}
