package com.ea.shop.rest;

import com.ea.shop.persistence.dto.CartItemRefDTO;
import com.ea.shop.persistence.dto.DeliveryCostDTO;
import com.ea.shop.persistence.dto.base.SimpleLongDTO;
import com.ea.shop.persistence.entity.*;
import com.ea.shop.persistence.entity.builder.CampaignBuilder;
import com.ea.shop.persistence.entity.builder.CategoryBuilder;
import com.ea.shop.persistence.entity.builder.CouponBuilder;
import com.ea.shop.persistence.entity.builder.ProductBuilder;
import com.ea.shop.persistence.repository.*;
import com.ea.shop.persistence.service.ShoppingCartService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.math.BigDecimal;

@RequestMapping("/initDefaultData")
@RestController
@Api(value = "initDefaultDataController")
public class InitDefaultDataController {

    @Autowired
    private CategoryRepository categoryRepository;

    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private CampaignRepository campaignRepository;

    @Autowired
    private CouponRepository couponRepository;

    @Autowired
    private ShoppingCartRepository shoppingCartRepository;

    @Autowired
    private ShoppingCartService shoppingCartService;

    @GetMapping(value = "/onlyParamData", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    @ApiOperation(value = "Data Init", response = Iterable.class)
    ResponseEntity<Boolean> initOnlyParamData() {
        saveOnlyParamData();
        return new ResponseEntity<>(Boolean.TRUE, HttpStatus.OK);
    }

    @GetMapping(value = "/initFullData", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    @ApiOperation(value = "Data Init", response = Iterable.class)
    ResponseEntity<Boolean> initFullData() {
        saveOnlyParamData();
        saveCartData();
        return new ResponseEntity<>(Boolean.TRUE, HttpStatus.OK);
    }

    @Transactional
    public void saveOnlyParamData() {
        Category category1 = new CategoryBuilder().title("Bilgisayar").doBuild();
        category1 = categoryRepository.save(category1);

        Category category2 = new CategoryBuilder().title("Telefon").doBuild();
        category2 = categoryRepository.save(category2);

        Category category3 = new CategoryBuilder().title("Kitap").doBuild();
        category3 = categoryRepository.save(category3);

        Category category4 = new CategoryBuilder().title("Araba").doBuild();
        category4 = categoryRepository.save(category4);


        Product product1 = new ProductBuilder().category(category1).title("Lenovo v130").price(new BigDecimal("1500")).doBuild();
        productRepository.save(product1);

        Product product2 = new ProductBuilder().category(category1).title("Acer A315").price(new BigDecimal("1900")).doBuild();
        productRepository.save(product2);

        Product product3 = new ProductBuilder().category(category1).title("Asus X540").price(new BigDecimal("2400")).doBuild();
        productRepository.save(product3);

        Product product4 = new ProductBuilder().category(category1).title("HP 250").price(new BigDecimal("4000")).doBuild();
        productRepository.save(product4);

        Product product5 = new ProductBuilder().category(category1).title("Lenovo IP330").price(new BigDecimal("2500")).doBuild();
        productRepository.save(product5);

        Product product6 = new ProductBuilder().category(category1).title("Apple Macbook Air").price(new BigDecimal("6000")).doBuild();
        productRepository.save(product6);

        Product product7 = new ProductBuilder().category(category2).title("Xiaomi Redmi Note 8").price(new BigDecimal("1820")).doBuild();
        productRepository.save(product7);

        Product product8 = new ProductBuilder().category(category2).title("Huawei Y6").price(new BigDecimal("1100")).doBuild();
        productRepository.save(product8);

        Product product9 = new ProductBuilder().category(category2).title("Samsung Galaxy A2").price(new BigDecimal("700")).doBuild();
        productRepository.save(product9);

        Product product10 = new ProductBuilder().category(category2).title("iPhone 11").price(new BigDecimal("7000")).doBuild();
        productRepository.save(product10);

        Product product11 = new ProductBuilder().category(category2).title("Samsung Note 10").price(new BigDecimal("10000")).doBuild();
        productRepository.save(product11);

        Product product12 = new ProductBuilder().category(category2).title("Huawei P20 Lite").price(new BigDecimal("1500")).doBuild();
        productRepository.save(product12);

        Product product13 = new ProductBuilder().category(category3).title("Karamazov Kardeşler").price(new BigDecimal("23")).doBuild();
        productRepository.save(product13);

        Product product14 = new ProductBuilder().category(category3).title("Satranç").price(new BigDecimal("7")).doBuild();
        productRepository.save(product14);

        Product product15 = new ProductBuilder().category(category3).title("Suç ve Ceza").price(new BigDecimal("15")).doBuild();
        productRepository.save(product15);

        Product product16 = new ProductBuilder().category(category3).title("Kumarbaz").price(new BigDecimal("10")).doBuild();
        productRepository.save(product16);

        Product product17 = new ProductBuilder().category(category3).title("İlyada").price(new BigDecimal("25")).doBuild();
        productRepository.save(product17);

        Product product18 = new ProductBuilder().category(category3).title("Aforizmalar").price(new BigDecimal("8")).doBuild();
        productRepository.save(product18);

        Product product19 = new ProductBuilder().category(category4).title("Mercedes C-180").price(new BigDecimal("194000")).doBuild();
        productRepository.save(product19);

        Product product20 = new ProductBuilder().category(category4).title("Mini Cooper").price(new BigDecimal("14000")).doBuild();
        productRepository.save(product20);

        Product product21 = new ProductBuilder().category(category4).title("Audi Q7").price(new BigDecimal("510000")).doBuild();
        productRepository.save(product21);

        Product product22 = new ProductBuilder().category(category4).title("Opel Astra").price(new BigDecimal("56000")).doBuild();
        productRepository.save(product22);

        Product product23 = new ProductBuilder().category(category4).title("Volvo S80").price(new BigDecimal("14000")).doBuild();
        productRepository.save(product23);

        Product product24 = new ProductBuilder().category(category4).title("BMW 5.20").price(new BigDecimal("190000")).doBuild();
        productRepository.save(product24);


        Campaign campaign1 = new CampaignBuilder().title("%20 İndirim").category(category1).discountAmount(new BigDecimal("20"))
                .itemLimit(3).discountType(DiscountType.RATE).doBuild();
        campaignRepository.save(campaign1);

        Campaign campaign2 = new CampaignBuilder().title("%50 İndirim").category(category2).discountAmount(new BigDecimal("50"))
                .itemLimit(5).discountType(DiscountType.RATE).doBuild();
        campaignRepository.save(campaign2);

        Campaign campaign3 = new CampaignBuilder().title("5 TL İndirim").category(category3).discountAmount(new BigDecimal("5"))
                .itemLimit(5).discountType(DiscountType.AMOUNT).doBuild();
        campaignRepository.save(campaign3);


        Coupon coupon1 = new CouponBuilder().title("%10 Kupon İndirim").discountAmount(new BigDecimal("10")).minPurchaseAmount(new BigDecimal(100))
                .discountType(DiscountType.RATE).doBuild();
        couponRepository.save(coupon1);

        Coupon coupon2 = new CouponBuilder().title("50 TL Kupon İndirim").discountAmount(new BigDecimal("50")).minPurchaseAmount(new BigDecimal(20))
                .discountType(DiscountType.AMOUNT).doBuild();
        couponRepository.save(coupon2);
    }

    @Transactional
    public void saveCartData() {
        ShoppingCart shoppingCart = new ShoppingCart();
        shoppingCartRepository.save(shoppingCart);

        shoppingCartService.addItem(new CartItemRefDTO(5L, 1));
        shoppingCartService.addItem(new CartItemRefDTO(6L, 2));
        shoppingCartService.addItem(new CartItemRefDTO(10L, 3));
        shoppingCartService.addItem(new CartItemRefDTO(8L, 1));
        shoppingCartService.addItem(new CartItemRefDTO(11L, 1));
        shoppingCartService.addItem(new CartItemRefDTO(14L, 2));
        shoppingCartService.addItem(new CartItemRefDTO(16L, 3));
        shoppingCartService.addItem(new CartItemRefDTO(17L, 1));
        shoppingCartService.addItem(new CartItemRefDTO(18L, 1));
        shoppingCartService.addItem(new CartItemRefDTO(19L, 3));
        shoppingCartService.addItem(new CartItemRefDTO(21L, 1));
        shoppingCartService.addItem(new CartItemRefDTO(23L, 1));


        shoppingCartService.applyCampaign(new SimpleLongDTO(30L));
        shoppingCartService.applyCoupon(new SimpleLongDTO(32L));


        DeliveryCostDTO deliveryCostDTO = new DeliveryCostDTO(new BigDecimal("2"), new BigDecimal("2"));
        shoppingCartService.applyDelivery(deliveryCostDTO);
    }

}
