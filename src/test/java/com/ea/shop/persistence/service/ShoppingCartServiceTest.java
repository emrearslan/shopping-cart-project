package com.ea.shop.persistence.service;

import com.ea.shop.application.ShoppingCartApplication;
import com.ea.shop.persistence.dto.CartItemRefDTO;
import com.ea.shop.persistence.dto.DeliveryCostDTO;
import com.ea.shop.persistence.dto.ShoppingCartDTO;
import com.ea.shop.persistence.dto.base.SimpleLongDTO;
import com.ea.shop.persistence.entity.*;
import com.ea.shop.persistence.entity.builder.*;
import com.ea.shop.persistence.helper.CartItemHelper;
import com.ea.shop.persistence.helper.DeliveryHelper;
import com.ea.shop.persistence.helper.ShoppingCartHelper;
import com.ea.shop.persistence.mapper.ShoppingCartMapper;
import com.ea.shop.persistence.repository.*;
import com.ea.shop.persistence.validator.ShoppingCartValidator;
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
import java.util.Set;

@SpringBootTest(classes = ShoppingCartApplication.class)
public class ShoppingCartServiceTest {

    @InjectMocks
    private ShoppingCartService shoppingCartService;

    @Mock
    private ShoppingCartValidator shoppingCartValidator;

    @Mock
    private ShoppingCartRepository shoppingCartRepository;

    @Mock
    private CartItemRepository cartItemRepository;

    @Mock
    private ProductRepository productRepository;

    @Mock
    private ShoppingCartMapper shoppingCartMapper;

    @Mock
    private CampaignRepository campaignRepository;

    @Mock
    private CouponRepository couponRepository;

    @Mock
    private CartItemHelper cartItemHelper;

    @Mock
    private ShoppingCartHelper shoppingCartHelper;

    @Mock
    private DeliveryHelper deliveryHelper;

    private Product product;

    @BeforeEach
    public void setup() {
        product = new ProductBuilder().id(10L).title("product")
                .price(new BigDecimal("500")).doBuild();
    }

    @Test
    public void shouldPrint() {
        ShoppingCart shoppingCart = new ShoppingCart();
        shoppingCart.setId(ShoppingCart.ACTIVE_USER);
        ShoppingCartDTO shoppingCartDTO = new ShoppingCartDTO();

        Mockito.when(shoppingCartRepository.findActiveShoppingCart()).thenReturn(shoppingCart);
        Mockito.when(shoppingCartMapper.toDto(shoppingCart)).thenReturn(shoppingCartDTO);

        shoppingCartService.print();
    }

    @Test
    public void shouldAddItemNewCart() {
        CartItemRefDTO cartItemRefDTO = new CartItemRefDTO();
        cartItemRefDTO.setProductId(10L);
        cartItemRefDTO.setQuantity(1);

        ShoppingCart shoppingCart = new ShoppingCart();
        shoppingCart.setId(ShoppingCart.ACTIVE_USER);

        Mockito.when(shoppingCartRepository.findActiveShoppingCart()).thenReturn(shoppingCart);
        Mockito.when(productRepository.findById(Mockito.anyLong())).thenReturn(Optional.ofNullable(product));
        Mockito.when(shoppingCartRepository.save(Mockito.any())).thenReturn(shoppingCart);
        Mockito.when(shoppingCartMapper.toDto(Mockito.any())).thenReturn(new ShoppingCartDTO());

        shoppingCartService.addItem(cartItemRefDTO);

        Mockito.verify(shoppingCartValidator).addItemValidator(Mockito.any());
        Mockito.verify(cartItemHelper).calculateFinalPrice(Mockito.any());
        Mockito.verify(shoppingCartHelper).calculateDisplayPrice(Mockito.any());
    }

    @Test
    public void shouldAddItemExistCart() {
        CartItemRefDTO cartItemRefDTO = new CartItemRefDTO();
        cartItemRefDTO.setProductId(10L);
        cartItemRefDTO.setQuantity(1);

        ShoppingCart shoppingCart = new ShoppingCart();
        shoppingCart.setId(ShoppingCart.ACTIVE_USER);

        CartItem cartItem = new CartItemBuilder().id(20L).product(product).shoppingCart(shoppingCart)
                .product(product).quantity(1).unitPrice(new BigDecimal("500")).doBuild();
        shoppingCart.setCartItems(new HashSet<>(Arrays.asList(cartItem)));

        Mockito.when(shoppingCartRepository.findActiveShoppingCart()).thenReturn(shoppingCart);
        Mockito.when(shoppingCartRepository.save(Mockito.any())).thenReturn(shoppingCart);
        Mockito.when(shoppingCartMapper.toDto(Mockito.any())).thenReturn(new ShoppingCartDTO());

        shoppingCartService.addItem(cartItemRefDTO);

        Mockito.verify(shoppingCartValidator).addItemValidator(Mockito.any());
        Mockito.verify(cartItemHelper).calculateFinalPrice(Mockito.any());
        Mockito.verify(shoppingCartHelper).calculateDisplayPrice(Mockito.any());
    }

    @Test
    public void shouldRemoveItemAfterEmptyCart() {
        CartItemRefDTO cartItemRefDTO = new CartItemRefDTO();
        cartItemRefDTO.setProductId(10L);
        cartItemRefDTO.setQuantity(1);

        ShoppingCart shoppingCart = new ShoppingCart();
        shoppingCart.setId(ShoppingCart.ACTIVE_USER);

        CartItem cartItem = new CartItemBuilder().id(20L).product(product).shoppingCart(shoppingCart)
                .product(product).quantity(1).unitPrice(new BigDecimal("500")).doBuild();
        shoppingCart.setCartItems(new HashSet<>(Arrays.asList(cartItem)));

        Mockito.when(shoppingCartRepository.findActiveShoppingCart()).thenReturn(shoppingCart);
        Mockito.when(shoppingCartRepository.save(Mockito.any())).thenReturn(shoppingCart);
        Mockito.when(shoppingCartMapper.toDto(Mockito.any())).thenReturn(new ShoppingCartDTO());

        shoppingCartService.removeItem(cartItemRefDTO);

        Mockito.verify(shoppingCartValidator).removeItemValidator(Mockito.any());
        Mockito.verify(cartItemRepository).delete(Mockito.any());
        Mockito.verify(shoppingCartHelper).calculateDisplayPrice(Mockito.any());
    }

    @Test
    public void shouldRemoveItemAfterHasCart() {
        CartItemRefDTO cartItemRefDTO = new CartItemRefDTO();
        cartItemRefDTO.setProductId(10L);
        cartItemRefDTO.setQuantity(1);

        ShoppingCart shoppingCart = new ShoppingCart();
        shoppingCart.setId(ShoppingCart.ACTIVE_USER);

        CartItem cartItem = new CartItemBuilder().id(20L).product(product).shoppingCart(shoppingCart)
                .product(product).quantity(3).unitPrice(new BigDecimal("500")).doBuild();
        shoppingCart.setCartItems(new HashSet<>(Arrays.asList(cartItem)));

        Mockito.when(shoppingCartRepository.findActiveShoppingCart()).thenReturn(shoppingCart);
        Mockito.when(shoppingCartRepository.save(Mockito.any())).thenReturn(shoppingCart);
        Mockito.when(shoppingCartMapper.toDto(Mockito.any())).thenReturn(new ShoppingCartDTO());

        shoppingCartService.removeItem(cartItemRefDTO);

        Mockito.verify(shoppingCartValidator).removeItemValidator(Mockito.any());
        Mockito.verify(cartItemHelper).calculateFinalPrice(Mockito.any());
        Mockito.verify(shoppingCartHelper).calculateDisplayPrice(Mockito.any());
    }

    @Test
    public void shouldApplyCampaign() {
        SimpleLongDTO param = new SimpleLongDTO();
        param.setParam(30L);

        ShoppingCart shoppingCart = new ShoppingCart();
        shoppingCart.setId(ShoppingCart.ACTIVE_USER);

        Category category = new CategoryBuilder().id(10L).title("cat1").doBuild();
        Campaign campaign = new CampaignBuilder().id(10L).title("campaign").category(category)
                .itemLimit(1).doBuild();
        CartItem cartItem = new CartItemBuilder().id(20L).product(product)
                .quantity(4).doBuild();

        Set<CartItem> cartItems = new HashSet<>(Arrays.asList(cartItem));
        shoppingCart.setCartItems(cartItems);

        Mockito.when(campaignRepository.findById(Mockito.anyLong())).thenReturn(Optional.ofNullable(campaign));
        Mockito.when(cartItemRepository.findByCategoryId(Mockito.anyLong())).thenReturn(cartItems);
        Mockito.when(cartItemRepository.saveAll(Mockito.any())).thenReturn(cartItems);
        Mockito.when(shoppingCartRepository.findActiveShoppingCart()).thenReturn(shoppingCart);
        Mockito.when(shoppingCartRepository.save(Mockito.any())).thenReturn(shoppingCart);
        Mockito.when(shoppingCartMapper.toDto(Mockito.any())).thenReturn(new ShoppingCartDTO());

        shoppingCartService.applyCampaign(param);

        Mockito.verify(shoppingCartValidator).applyCampaignValidator(Mockito.any());
        Mockito.verify(cartItemHelper, Mockito.times(cartItems.size())).calculateCampaignDiscount(Mockito.any(), Mockito.any());
        Mockito.verify(shoppingCartHelper).calculateDisplayPrice(Mockito.any());
    }

    @Test
    public void shouldApplyCoupon() {
        SimpleLongDTO param = new SimpleLongDTO();
        param.setParam(40L);

        Coupon coupon = new CouponBuilder().id(10L).title("coupon")
                .minPurchaseAmount(new BigDecimal("100")).doBuild();

        ShoppingCart shoppingCart = new ShoppingCart();
        shoppingCart.setId(ShoppingCart.ACTIVE_USER);

        Mockito.when(couponRepository.findById(Mockito.any())).thenReturn(Optional.ofNullable(coupon));
        Mockito.when(shoppingCartRepository.findActiveShoppingCart()).thenReturn(shoppingCart);
        Mockito.when(shoppingCartRepository.save(Mockito.any())).thenReturn(shoppingCart);
        Mockito.when(shoppingCartMapper.toDto(Mockito.any())).thenReturn(new ShoppingCartDTO());

        shoppingCartService.applyCoupon(param);

        Mockito.verify(shoppingCartValidator).applyCouponValidator(Mockito.any());
        Mockito.verify(shoppingCartHelper).calculateCouponDiscount(Mockito.any(), Mockito.any());
        Mockito.verify(shoppingCartHelper).calculateDisplayPrice(Mockito.any());
    }

    @Test
    public void shouldApplyDelivery() {
        ShoppingCart shoppingCart = new ShoppingCart();
        shoppingCart.setId(ShoppingCart.ACTIVE_USER);

        DeliveryCostDTO deliveryCostDTO = new DeliveryCostDTO(new BigDecimal("2"), new BigDecimal("2"));

        Mockito.when(shoppingCartRepository.findActiveShoppingCart()).thenReturn(shoppingCart);
        Mockito.when(shoppingCartRepository.save(Mockito.any())).thenReturn(shoppingCart);
        Mockito.when(shoppingCartMapper.toDto(Mockito.any())).thenReturn(new ShoppingCartDTO());

        shoppingCartService.applyDelivery(deliveryCostDTO);

        Mockito.verify(shoppingCartValidator).applyDeliveryValidator(Mockito.any());
        Mockito.verify(deliveryHelper).calculateDelivery(Mockito.any(), Mockito.any());
        Mockito.verify(shoppingCartHelper).calculateDisplayPrice(Mockito.any());
    }

}