package com.ea.shop.persistence.mapper;

import com.ea.shop.application.ShoppingCartApplication;
import com.ea.shop.persistence.dto.CartItemDTO;
import com.ea.shop.persistence.dto.ShoppingCartDTO;
import com.ea.shop.persistence.dto.builder.CartItemDTOBuilder;
import com.ea.shop.persistence.entity.CartItem;
import com.ea.shop.persistence.entity.ShoppingCart;
import com.ea.shop.persistence.entity.builder.CartItemBuilder;
import com.ea.shop.persistence.repository.ShoppingCartRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Arrays;
import java.util.HashSet;

@SpringBootTest(classes = ShoppingCartApplication.class)
class ShoppingCartMapperTest {

    @InjectMocks
    private ShoppingCartMapper shoppingCartMapper;

    @Mock
    private ShoppingCartRepository shoppingCartRepository;

    @Mock
    private CartItemMapper cartItemMapper;

    private ShoppingCart shoppingCart;
    private ShoppingCartDTO shoppingCartDTO;

    @BeforeEach
    public void setup() {
        shoppingCart = new ShoppingCart();
        shoppingCart.setId(ShoppingCart.ACTIVE_USER);

        shoppingCartDTO = new ShoppingCartDTO();
    }

    @Test
    public void shouldToEntityReturnNull() {
        ShoppingCart resultEntity = shoppingCartMapper.toEntity(null);
        Assertions.assertNull(resultEntity);
    }

    @Test
    public void shouldToEntity() {
        Mockito.when(shoppingCartRepository.findActiveShoppingCart()).thenReturn(shoppingCart);
        ShoppingCart resultEntity = shoppingCartMapper.toEntity(shoppingCartDTO);

        Assertions.assertEquals(resultEntity.getId(), shoppingCart.getId());
        Assertions.assertEquals(resultEntity.getCartItems(), shoppingCart.getCartItems());
        Assertions.assertEquals(resultEntity.getCouponDiscount(), shoppingCart.getCouponDiscount());
        Assertions.assertEquals(resultEntity.getDeliveryCost(), shoppingCart.getDeliveryCost());
        Assertions.assertEquals(resultEntity.getTotalFinalPrice(), shoppingCart.getTotalFinalPrice());
        Assertions.assertEquals(resultEntity.getDisplayPrice(), shoppingCart.getDisplayPrice());
    }

    @Test
    public void shouldToEntityHasCartItem() {
        CartItem cartItem = new CartItemBuilder().id(10L).shoppingCart(shoppingCart).quantity(1).doBuild();
        CartItemDTO cartItemDTO = new CartItemDTOBuilder().id(10L).shoppingCartId(shoppingCart.getId()).doBuild();
        shoppingCartDTO.setCartItems(new HashSet<>(Arrays.asList(cartItemDTO)));

        Mockito.when(cartItemMapper.toEntitySet(Mockito.any())).thenReturn(new HashSet<>(Arrays.asList(cartItem)));
        Mockito.when(shoppingCartRepository.findActiveShoppingCart()).thenReturn(shoppingCart);

        ShoppingCart resultEntity = shoppingCartMapper.toEntity(shoppingCartDTO);

        Assertions.assertEquals(resultEntity.getCartItems().size(), 1);
        Assertions.assertTrue(resultEntity.getCartItems().stream().anyMatch(p->p.getId().equals(cartItem.getId())));
    }

    @Test
    public void shouldToDtoReturnNull() {
        ShoppingCartDTO resultDTO = shoppingCartMapper.toDto(null);
        Assertions.assertNull(resultDTO);
    }

    @Test
    public void shouldToDto() {
        ShoppingCartDTO resultDTO = shoppingCartMapper.toDto(shoppingCart);

        Assertions.assertEquals(resultDTO.getId(), shoppingCartDTO.getId());
        Assertions.assertEquals(resultDTO.getCartItems(), shoppingCartDTO.getCartItems());
        Assertions.assertEquals(resultDTO.getCouponDiscount(), shoppingCartDTO.getCouponDiscount());
        Assertions.assertEquals(resultDTO.getDeliveryCost(), shoppingCartDTO.getDeliveryCost());
        Assertions.assertEquals(resultDTO.getTotalFinalPrice(), shoppingCartDTO.getTotalFinalPrice());
        Assertions.assertEquals(resultDTO.getDisplayPrice(), shoppingCartDTO.getDisplayPrice());
    }

    @Test
    public void shouldToDtoHasCartItem() {
        CartItem cartItem = new CartItemBuilder().id(10L).shoppingCart(shoppingCart).quantity(1).doBuild();
        CartItemDTO cartItemDTO = new CartItemDTOBuilder().id(10L).shoppingCartId(shoppingCart.getId()).doBuild();
        shoppingCart.setCartItems(new HashSet<>(Arrays.asList(cartItem)));

        Mockito.when(cartItemMapper.toDtoSet(Mockito.any())).thenReturn(new HashSet<>(Arrays.asList(cartItemDTO)));
        ShoppingCartDTO resultDTO = shoppingCartMapper.toDto(shoppingCart);

        Assertions.assertEquals(resultDTO.getCartItems().size(), 1);
        Assertions.assertTrue(resultDTO.getCartItems().stream().anyMatch(p->p.getId().equals(cartItemDTO.getId())));
    }

}