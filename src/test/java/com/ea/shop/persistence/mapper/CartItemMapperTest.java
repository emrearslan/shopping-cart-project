package com.ea.shop.persistence.mapper;

import com.ea.shop.application.ShoppingCartApplication;
import com.ea.shop.persistence.dto.CartItemDTO;
import com.ea.shop.persistence.dto.builder.CartItemDTOBuilder;
import com.ea.shop.persistence.entity.CartItem;
import com.ea.shop.persistence.entity.Product;
import com.ea.shop.persistence.entity.ShoppingCart;
import com.ea.shop.persistence.entity.builder.CartItemBuilder;
import com.ea.shop.persistence.entity.builder.ProductBuilder;
import com.ea.shop.persistence.repository.CartItemRepository;
import com.ea.shop.persistence.repository.ProductRepository;
import com.ea.shop.persistence.repository.ShoppingCartRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.boot.test.context.SpringBootTest;

import java.math.BigDecimal;
import java.util.Optional;

@SpringBootTest(classes = ShoppingCartApplication.class)
public class CartItemMapperTest {

    @InjectMocks
    private CartItemMapper cartItemMapper;

    @Mock
    private CartItemRepository cartItemRepository;

    @Mock
    private ShoppingCartRepository shoppingCartRepository;

    @Mock
    private ProductRepository productRepository;

    private CartItem cartItem;
    private CartItemDTO cartItemDTO;
    private ShoppingCart shoppingCart;
    private Product product;

    @BeforeEach
    public void setup() {
        BigDecimal price = new BigDecimal("500");

        product = new ProductBuilder().id(1L).title("product").price(price).doBuild();
        shoppingCart = new ShoppingCart();
        shoppingCart.setId(ShoppingCart.ACTIVE_USER);

        cartItem = new CartItemBuilder().id(10L).shoppingCart(shoppingCart).product(product).quantity(1)
                .unitPrice(price).totalPrice(price).campaignDiscount(BigDecimal.ZERO).finalPrice(price).doBuild();
        cartItemDTO = new CartItemDTOBuilder().id(10L).shoppingCartId(shoppingCart.getId()).productId(product.getId()).quantity(1)
                .unitPrice(price).totalPrice(price).campaignDiscount(BigDecimal.ZERO).finalPrice(price).doBuild();
    }

    @Test
    public void shouldToEntityReturnNull() {
        CartItem resultDTO = cartItemMapper.toEntity(null);
        Assertions.assertNull(resultDTO);
    }

    @Test
    public void shouldToEntityHasId() {
        Mockito.when(cartItemRepository.findById(Mockito.anyLong())).thenReturn(Optional.ofNullable(cartItem));
        Mockito.when(shoppingCartRepository.findById(Mockito.anyString())).thenReturn(Optional.ofNullable(shoppingCart));
        Mockito.when(productRepository.findById(Mockito.anyLong())).thenReturn(Optional.ofNullable(product));

        CartItem resultEntity = cartItemMapper.toEntity(cartItemDTO);

        Assertions.assertEquals(resultEntity.getId(), cartItem.getId());
        Assertions.assertEquals(resultEntity.getShoppingCart(), cartItem.getShoppingCart());
        Assertions.assertEquals(resultEntity.getProduct(), cartItem.getProduct());
        Assertions.assertEquals(resultEntity.getQuantity(), cartItem.getQuantity());
        Assertions.assertEquals(resultEntity.getUnitPrice(), cartItem.getUnitPrice());
        Assertions.assertEquals(resultEntity.getTotalPrice(), cartItem.getTotalPrice());
        Assertions.assertEquals(resultEntity.getTotalPrice(), cartItem.getTotalPrice());
    }

    @Test
    public void shouldToEntityNotFoundCartItem() {
        Mockito.when(cartItemRepository.findById(Mockito.anyLong())).thenReturn(Optional.ofNullable(null));
        Mockito.when(shoppingCartRepository.findById(Mockito.anyString())).thenReturn(Optional.ofNullable(shoppingCart));
        Mockito.when(productRepository.findById(Mockito.anyLong())).thenReturn(Optional.ofNullable(product));

        CartItem resultEntity = cartItemMapper.toEntity(cartItemDTO);
        Assertions.assertNull(resultEntity.getId());
    }

    @Test
    public void shouldToEntityNewCartItem() {
        cartItemDTO.setId(null);
        Mockito.when(shoppingCartRepository.findById(Mockito.anyString())).thenReturn(Optional.ofNullable(shoppingCart));
        Mockito.when(productRepository.findById(Mockito.anyLong())).thenReturn(Optional.ofNullable(product));

        CartItem resultEntity = cartItemMapper.toEntity(cartItemDTO);
        Assertions.assertNull(resultEntity.getId());
    }

    @Test
    public void shouldToEntityNullShoppingCart() {
        Mockito.when(cartItemRepository.findById(Mockito.anyLong())).thenReturn(Optional.ofNullable(cartItem));
        Mockito.when(shoppingCartRepository.findById(Mockito.anyString())).thenReturn(Optional.ofNullable(null));
        Mockito.when(productRepository.findById(Mockito.anyLong())).thenReturn(Optional.ofNullable(product));

        CartItem resultEntity = cartItemMapper.toEntity(cartItemDTO);
        Assertions.assertNull(resultEntity.getShoppingCart());
    }

    @Test
    public void shouldToEntityNullProduct() {
        Mockito.when(cartItemRepository.findById(Mockito.anyLong())).thenReturn(Optional.ofNullable(cartItem));
        Mockito.when(shoppingCartRepository.findById(Mockito.anyString())).thenReturn(Optional.ofNullable(shoppingCart));
        Mockito.when(productRepository.findById(Mockito.anyLong())).thenReturn(Optional.ofNullable(null));

        CartItem resultEntity = cartItemMapper.toEntity(cartItemDTO);
        Assertions.assertNull(resultEntity.getProduct());
    }

    @Test
    public void shouldToDtoReturnNull() {
        CartItemDTO resultDTO = cartItemMapper.toDto(null);
        Assertions.assertNull(resultDTO);
    }

    @Test
    public void shouldToDto() {
        CartItemDTO resultDTO = cartItemMapper.toDto(cartItem);

        Assertions.assertEquals(resultDTO.getId(), cartItemDTO.getId());
        Assertions.assertEquals(resultDTO.getShoppingCartId(), cartItemDTO.getShoppingCartId());
        Assertions.assertEquals(resultDTO.getProductId(), cartItemDTO.getProductId());
        Assertions.assertEquals(resultDTO.getQuantity(), cartItemDTO.getQuantity());
        Assertions.assertEquals(resultDTO.getUnitPrice(), cartItemDTO.getUnitPrice());
        Assertions.assertEquals(resultDTO.getTotalPrice(), cartItemDTO.getTotalPrice());
        Assertions.assertEquals(resultDTO.getTotalPrice(), cartItemDTO.getTotalPrice());
    }

}