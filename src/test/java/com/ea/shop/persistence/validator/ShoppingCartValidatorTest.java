package com.ea.shop.persistence.validator;

import com.ea.shop.application.ShoppingCartApplication;
import com.ea.shop.exception.BusinessException;
import com.ea.shop.message.MessageService;
import com.ea.shop.persistence.dto.CartItemRefDTO;
import com.ea.shop.persistence.dto.DeliveryCostDTO;
import com.ea.shop.persistence.dto.base.SimpleLongDTO;
import com.ea.shop.persistence.entity.*;
import com.ea.shop.persistence.entity.builder.*;
import com.ea.shop.persistence.repository.*;
import com.ea.shop.persistence.validator.base.BaseValidator;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.boot.test.context.SpringBootTest;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Optional;

@SpringBootTest(classes = ShoppingCartApplication.class)
public class ShoppingCartValidatorTest {

    @InjectMocks
    private ShoppingCartValidator shoppingCartValidator;

    @Mock
    private BaseValidator baseValidator;

    @Mock
    private MessageService messageService;

    @Mock
    private ShoppingCartRepository shoppingCartRepository;

    @Mock
    private CartItemRepository cartItemRepository;

    @Mock
    private ProductRepository productRepository;

    @Mock
    private CampaignRepository campaignRepository;

    @Mock
    private CouponRepository couponRepository;

    private Product product;

    @BeforeEach
    public void setup() {
        product = new ProductBuilder().id(10L).title("product")
                .price(new BigDecimal("500")).doBuild();
    }

    @Test
    public void shouldAddItemValidator() {
        CartItemRefDTO cartItemRefDTO = new CartItemRefDTO();
        cartItemRefDTO.setProductId(10L);
        cartItemRefDTO.setQuantity(1);

        Mockito.when(productRepository.findById(Mockito.anyLong())).thenReturn(Optional.ofNullable(product));

        shoppingCartValidator.addItemValidator(cartItemRefDTO);
        Mockito.verify(baseValidator).validateNullCheck(cartItemRefDTO);
    }

    @Test
    public void shouldAddItemValidatorProductIdNull() {
        CartItemRefDTO cartItemRefDTO = new CartItemRefDTO();
        cartItemRefDTO.setProductId(null);
        cartItemRefDTO.setQuantity(1);

        Mockito.when(messageService.getMessage(Mockito.any())).thenReturn("message");

        Assertions.assertThrows(BusinessException.class,
                () -> shoppingCartValidator.addItemValidator(cartItemRefDTO));
        Mockito.verify(baseValidator).validateNullCheck(cartItemRefDTO);
    }

    @Test
    public void shouldAddItemValidatorProductNotFound() {
        CartItemRefDTO cartItemRefDTO = new CartItemRefDTO();
        cartItemRefDTO.setProductId(10L);
        cartItemRefDTO.setQuantity(1);

        Mockito.when(messageService.getMessage(Mockito.any())).thenReturn("message");
        Mockito.when(productRepository.findById(Mockito.anyLong())).thenReturn(Optional.ofNullable(null));

        Assertions.assertThrows(BusinessException.class,
                () -> shoppingCartValidator.addItemValidator(cartItemRefDTO));
        Mockito.verify(baseValidator).validateNullCheck(cartItemRefDTO);
    }

    @Test
    public void shouldRemoveItemValidator() {
        CartItemRefDTO cartItemRefDTO = new CartItemRefDTO();
        cartItemRefDTO.setProductId(10L);
        cartItemRefDTO.setQuantity(1);

        ShoppingCart shoppingCart = new ShoppingCart();
        shoppingCart.setId(ShoppingCart.ACTIVE_USER);

        CartItem cartItem = new CartItemBuilder().id(20L).product(product).shoppingCart(shoppingCart)
                .product(product).quantity(1).doBuild();
        shoppingCart.setCartItems(new HashSet<>(Arrays.asList(cartItem)));

        Mockito.when(productRepository.findById(Mockito.anyLong())).thenReturn(Optional.ofNullable(product));
        Mockito.when(shoppingCartRepository.findActiveShoppingCart()).thenReturn(shoppingCart);

        shoppingCartValidator.removeItemValidator(cartItemRefDTO);
        Mockito.verify(baseValidator).validateNullCheck(cartItemRefDTO);
    }

    @Test
    public void shouldRemoveItemValidatorCartItemNotFound() {
        CartItemRefDTO cartItemRefDTO = new CartItemRefDTO();
        cartItemRefDTO.setProductId(10L);
        cartItemRefDTO.setQuantity(1);

        ShoppingCart shoppingCart = new ShoppingCart();
        shoppingCart.setId(ShoppingCart.ACTIVE_USER);

        Mockito.when(messageService.getMessage(Mockito.any())).thenReturn("message");
        Mockito.when(productRepository.findById(Mockito.anyLong())).thenReturn(Optional.ofNullable(product));
        Mockito.when(shoppingCartRepository.findActiveShoppingCart()).thenReturn(shoppingCart);

        Assertions.assertThrows(BusinessException.class,
                () -> shoppingCartValidator.removeItemValidator(cartItemRefDTO));
        Mockito.verify(baseValidator).validateNullCheck(cartItemRefDTO);
    }

    @Test
    public void shouldRemoveItemValidatorMoreThanNotExistQuantity() {
        CartItemRefDTO cartItemRefDTO = new CartItemRefDTO();
        cartItemRefDTO.setProductId(10L);
        cartItemRefDTO.setQuantity(2);

        ShoppingCart shoppingCart = new ShoppingCart();
        shoppingCart.setId(ShoppingCart.ACTIVE_USER);

        CartItem cartItem = new CartItemBuilder().id(20L).product(product).shoppingCart(shoppingCart)
                .product(product).quantity(1).doBuild();
        shoppingCart.setCartItems(new HashSet<>(Arrays.asList(cartItem)));

        Mockito.when(messageService.getMessage(Mockito.any())).thenReturn("message");
        Mockito.when(productRepository.findById(Mockito.anyLong())).thenReturn(Optional.ofNullable(product));
        Mockito.when(shoppingCartRepository.findActiveShoppingCart()).thenReturn(shoppingCart);

        Assertions.assertThrows(BusinessException.class,
                () -> shoppingCartValidator.removeItemValidator(cartItemRefDTO));
        Mockito.verify(baseValidator).validateNullCheck(cartItemRefDTO);
    }

    @Test
    public void shouldApplyCampaignValidator() {
        SimpleLongDTO param = new SimpleLongDTO();
        param.setParam(30L);

        Category category = new CategoryBuilder().id(10L).title("cat1").doBuild();
        Campaign campaign = new CampaignBuilder().id(10L).title("campaign").category(category)
                .itemLimit(1).doBuild();
        CartItem cartItem = new CartItemBuilder().id(20L).product(product)
                .quantity(4).doBuild();

        Mockito.when(campaignRepository.findById(Mockito.anyLong())).thenReturn(Optional.ofNullable(campaign));
        Mockito.when(cartItemRepository.findByCategoryId(Mockito.anyLong())).thenReturn(new HashSet<>(Arrays.asList(cartItem)));

        shoppingCartValidator.applyCampaignValidator(param);
        Mockito.verify(baseValidator).validateNullCheck(param);
    }

    @Test
    public void shouldApplyCampaignValidatorCampaignIdNull() {
        Mockito.when(messageService.getMessage(Mockito.any())).thenReturn("message");

        Assertions.assertThrows(BusinessException.class,
                () -> shoppingCartValidator.applyCampaignValidator(new SimpleLongDTO()));
        Mockito.verify(baseValidator).validateNullCheck(Mockito.any());
    }

    @Test
    public void shouldApplyCampaignValidatorCampaignNotFound() {
        Mockito.when(messageService.getMessage(Mockito.any())).thenReturn("message");
        Mockito.when(campaignRepository.findById(Mockito.anyLong())).thenReturn(Optional.ofNullable(null));

        Assertions.assertThrows(BusinessException.class,
                () -> shoppingCartValidator.applyCampaignValidator(new SimpleLongDTO(11L)));
        Mockito.verify(baseValidator).validateNullCheck(Mockito.any());
    }

    @Test
    public void shouldApplyCampaignValidatorCategoryNotFound() {
        SimpleLongDTO param = new SimpleLongDTO();
        param.setParam(30L);

        Category category = new CategoryBuilder().id(10L).title("cat1").doBuild();
        Campaign campaign = new CampaignBuilder().id(10L).title("campaign").category(category)
                .itemLimit(1).doBuild();

        Mockito.when(messageService.getMessage(Mockito.any())).thenReturn("message");
        Mockito.when(campaignRepository.findById(Mockito.anyLong())).thenReturn(Optional.ofNullable(campaign));
        Mockito.when(cartItemRepository.findByCategoryId(Mockito.anyLong())).thenReturn(null);

        Assertions.assertThrows(BusinessException.class,
                () -> shoppingCartValidator.applyCampaignValidator(param));
        Mockito.verify(baseValidator).validateNullCheck(param);
    }

    @Test
    public void shouldApplyCampaignValidatorNotUsableCampaign() {
        SimpleLongDTO param = new SimpleLongDTO();
        param.setParam(30L);

        Category category = new CategoryBuilder().id(10L).title("cat1").doBuild();
        Campaign campaign = new CampaignBuilder().id(10L).title("campaign").category(category)
                .itemLimit(5).doBuild();
        CartItem cartItem = new CartItemBuilder().id(20L).product(product)
                .quantity(1).doBuild();

        Mockito.when(messageService.getMessage(Mockito.any())).thenReturn("message");
        Mockito.when(campaignRepository.findById(Mockito.anyLong())).thenReturn(Optional.ofNullable(campaign));
        Mockito.when(cartItemRepository.findByCategoryId(Mockito.anyLong())).thenReturn(new HashSet<>(Arrays.asList(cartItem)));

        Assertions.assertThrows(BusinessException.class,
                () -> shoppingCartValidator.applyCampaignValidator(param));
        Mockito.verify(baseValidator).validateNullCheck(param);
    }

    @Test
    public void shouldApplyCouponValidator() {
        SimpleLongDTO param = new SimpleLongDTO();
        param.setParam(40L);

        Coupon coupon = new CouponBuilder().id(10L).title("coupon")
                .minPurchaseAmount(new BigDecimal("100")).doBuild();

        CartItem cartItem = new CartItemBuilder().id(20L).product(product)
                .quantity(1).campaignDiscount(new BigDecimal("500")).doBuild();

        ShoppingCart shoppingCart = new ShoppingCart();
        shoppingCart.setId(ShoppingCart.ACTIVE_USER);
        shoppingCart.setTotalFinalPrice(new BigDecimal("1000"));
        shoppingCart.setCartItems(new HashSet<>(Arrays.asList(cartItem)));

        Mockito.when(couponRepository.findById(Mockito.anyLong())).thenReturn(Optional.ofNullable(coupon));
        Mockito.when(shoppingCartRepository.findActiveShoppingCart()).thenReturn(shoppingCart);

        shoppingCartValidator.applyCouponValidator(param);
        Mockito.verify(baseValidator).validateNullCheck(param);
    }

    @Test
    public void shouldApplyCouponValidatorCouponNull() {
        Mockito.when(messageService.getMessage(Mockito.any())).thenReturn("message");

        Assertions.assertThrows(BusinessException.class,
                () -> shoppingCartValidator.applyCouponValidator(new SimpleLongDTO()));
        Mockito.verify(baseValidator).validateNullCheck(Mockito.any());
    }

    @Test
    public void shouldApplyCouponNotFound() {
        SimpleLongDTO param = new SimpleLongDTO();
        param.setParam(40L);

        Mockito.when(couponRepository.findById(Mockito.anyLong())).thenReturn(Optional.ofNullable(null));
        Mockito.when(messageService.getMessage(Mockito.any())).thenReturn("message");

        Assertions.assertThrows(BusinessException.class,
                () -> shoppingCartValidator.applyCouponValidator(param));
        Mockito.verify(baseValidator).validateNullCheck(param);
    }

    @Test
    public void shouldApplyCouponValidatorNotUsedCampaignDiscount() {
        SimpleLongDTO param = new SimpleLongDTO();
        param.setParam(40L);

        Coupon coupon = new CouponBuilder().id(10L).title("coupon")
                .minPurchaseAmount(new BigDecimal("100")).doBuild();

        CartItem cartItem = new CartItemBuilder().id(20L).product(product)
                .quantity(1).doBuild();

        ShoppingCart shoppingCart = new ShoppingCart();
        shoppingCart.setId(ShoppingCart.ACTIVE_USER);
        shoppingCart.setTotalFinalPrice(new BigDecimal("1000"));
        shoppingCart.setCartItems(new HashSet<>(Arrays.asList(cartItem)));

        Mockito.when(couponRepository.findById(Mockito.anyLong())).thenReturn(Optional.ofNullable(coupon));
        Mockito.when(shoppingCartRepository.findActiveShoppingCart()).thenReturn(shoppingCart);
        Mockito.when(messageService.getMessage(Mockito.any())).thenReturn("message");

        Assertions.assertThrows(BusinessException.class,
                () -> shoppingCartValidator.applyCouponValidator(param));
        Mockito.verify(baseValidator).validateNullCheck(param);
    }

    @Test
    public void shouldApplyCouponValidatorNotUsableCoupon() {
        SimpleLongDTO param = new SimpleLongDTO();
        param.setParam(40L);

        Coupon coupon = new CouponBuilder().id(10L).title("coupon")
                .minPurchaseAmount(new BigDecimal("100")).doBuild();

        CartItem cartItem = new CartItemBuilder().id(20L).product(product)
                .quantity(1).campaignDiscount(new BigDecimal("50")).doBuild();

        ShoppingCart shoppingCart = new ShoppingCart();
        shoppingCart.setId(ShoppingCart.ACTIVE_USER);
        shoppingCart.setTotalFinalPrice(new BigDecimal("70"));
        shoppingCart.setCartItems(new HashSet<>(Arrays.asList(cartItem)));

        Mockito.when(couponRepository.findById(Mockito.anyLong())).thenReturn(Optional.ofNullable(coupon));
        Mockito.when(shoppingCartRepository.findActiveShoppingCart()).thenReturn(shoppingCart);
        Mockito.when(messageService.getMessage(Mockito.any())).thenReturn("message");

        Assertions.assertThrows(BusinessException.class,
                () -> shoppingCartValidator.applyCouponValidator(param));
        Mockito.verify(baseValidator).validateNullCheck(param);
    }

    @Test
    public void shouldApplyDeliveryValidator() {
        DeliveryCostDTO deliveryCostDTO = new DeliveryCostDTO();
        deliveryCostDTO.setCostPerDelivery(new BigDecimal("2"));
        deliveryCostDTO.setCostPerProduct(new BigDecimal("2"));

        shoppingCartValidator.applyDeliveryValidator(deliveryCostDTO);
        Mockito.verify(baseValidator).validateNullCheck(deliveryCostDTO);
    }

    @Test
    public void shouldApplyDeliveryValidatorCostPerDeliveryNull() {
        DeliveryCostDTO deliveryCostDTO = new DeliveryCostDTO();
        deliveryCostDTO.setCostPerDelivery(null);
        deliveryCostDTO.setCostPerProduct(new BigDecimal("2"));

        Mockito.when(messageService.getMessage(Mockito.any())).thenReturn("message");

        Assertions.assertThrows(BusinessException.class,
                () -> shoppingCartValidator.applyDeliveryValidator(deliveryCostDTO));
        Mockito.verify(baseValidator).validateNullCheck(deliveryCostDTO);
    }

    @Test
    public void shouldApplyDeliveryValidatorCostPerProductNull() {
        DeliveryCostDTO deliveryCostDTO = new DeliveryCostDTO();
        deliveryCostDTO.setCostPerDelivery(new BigDecimal("2"));
        deliveryCostDTO.setCostPerProduct(null);

        Mockito.when(messageService.getMessage(Mockito.any())).thenReturn("message");

        Assertions.assertThrows(BusinessException.class,
                () -> shoppingCartValidator.applyDeliveryValidator(deliveryCostDTO));
        Mockito.verify(baseValidator).validateNullCheck(deliveryCostDTO);
    }


}