package com.ea.shop.persistence.service;

import com.ea.shop.persistence.dto.CartItemRefDTO;
import com.ea.shop.persistence.dto.DeliveryCostDTO;
import com.ea.shop.persistence.dto.ShoppingCartDTO;
import com.ea.shop.persistence.dto.base.SimpleLongDTO;
import com.ea.shop.persistence.entity.*;
import com.ea.shop.persistence.entity.builder.CartItemBuilder;
import com.ea.shop.persistence.helper.CartItemHelper;
import com.ea.shop.persistence.helper.DeliveryHelper;
import com.ea.shop.persistence.helper.ShoppingCartHelper;
import com.ea.shop.persistence.mapper.ShoppingCartMapper;
import com.ea.shop.persistence.repository.*;
import com.ea.shop.persistence.validator.ShoppingCartValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.Set;

@Service
public class ShoppingCartService {

    @Autowired
    private ShoppingCartValidator shoppingCartValidator;

    @Autowired
    private ShoppingCartRepository shoppingCartRepository;

    @Autowired
    private CartItemRepository cartItemRepository;

    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private ShoppingCartMapper shoppingCartMapper;

    @Autowired
    private CampaignRepository campaignRepository;

    @Autowired
    private CouponRepository couponRepository;

    @Autowired
    private CartItemHelper cartItemHelper;

    @Autowired
    private ShoppingCartHelper shoppingCartHelper;

    @Autowired
    private DeliveryHelper deliveryHelper;

    public ShoppingCartDTO print() {
        ShoppingCart shoppingCart = shoppingCartRepository.findActiveShoppingCart();
        return shoppingCartMapper.toDto(shoppingCart);
    }

    @Transactional
    public ShoppingCartDTO addItem(CartItemRefDTO cartItemRefDTO) {
        shoppingCartValidator.addItemValidator(cartItemRefDTO);

        ShoppingCart shoppingCart = shoppingCartRepository.findActiveShoppingCart();
        CartItem cartItem = shoppingCart.getCartItems().stream()
                .filter(p -> p.getProduct().getId().equals(cartItemRefDTO.getProductId())).findAny().orElse(null);

        if (cartItem != null) {
            addExistCartItem(cartItemRefDTO, cartItem);
        } else {
            cartItem = addNewCartItem(cartItemRefDTO, shoppingCart);
            shoppingCart.getCartItems().add(cartItem);
        }

        ShoppingCart result = updateShoppingCartPriceData(shoppingCart);
        return shoppingCartMapper.toDto(result);
    }

    private void addExistCartItem(CartItemRefDTO cartItemRefDTO, CartItem cartItem) {
        cartItem.addQuantity(cartItemRefDTO.getQuantity());

        BigDecimal totalPrice = cartItem.getUnitPrice().multiply(new BigDecimal(cartItem.getQuantity()));
        cartItem.setTotalPrice(totalPrice);

        cartItemHelper.calculateFinalPrice(cartItem);
    }

    private CartItem addNewCartItem(CartItemRefDTO cartItemRefDTO, ShoppingCart shoppingCart) {
        Product product = productRepository.findById(cartItemRefDTO.getProductId()).get();

        int quantity = cartItemRefDTO.getQuantity();
        BigDecimal unitPrice = product.getPrice();
        BigDecimal totalPrice = unitPrice.multiply(new BigDecimal(quantity));

        CartItem cartItem = new CartItemBuilder().shoppingCart(shoppingCart).product(product)
                .quantity(cartItemRefDTO.getQuantity()).unitPrice(unitPrice)
                .totalPrice(totalPrice).doBuild();

        cartItemHelper.calculateFinalPrice(cartItem);

        return cartItem;
    }

    @Transactional
    public ShoppingCartDTO removeItem(CartItemRefDTO cartItemRefDTO) {
        shoppingCartValidator.removeItemValidator(cartItemRefDTO);

        ShoppingCart shoppingCart = shoppingCartRepository.findActiveShoppingCart();
        CartItem cartItem = shoppingCart.getCartItems().stream()
                .filter(p -> p.getProduct().getId().equals(cartItemRefDTO.getProductId())).findAny().get();

        int quantity = cartItemRefDTO.getQuantity();
        cartItem.removeQuantity(quantity);

        if (cartItem.isEmptyCartItem()) {
            shoppingCart.getCartItems().remove(cartItem);
            cartItemRepository.delete(cartItem);
        } else {
            BigDecimal totalPrice = cartItem.getUnitPrice().multiply(new BigDecimal(quantity));
            cartItem.setTotalPrice(totalPrice);

            cartItemHelper.calculateFinalPrice(cartItem);
        }

        ShoppingCart result = updateShoppingCartPriceData(shoppingCart);
        return shoppingCartMapper.toDto(result);
    }

    @Transactional
    public ShoppingCartDTO applyCampaign(SimpleLongDTO param) {
        shoppingCartValidator.applyCampaignValidator(param);

        Campaign campaign = campaignRepository.findById(param.getParam()).get();
        Set<CartItem> cartItems = cartItemRepository.findByCategoryId(campaign.getCategory().getId());

        for (CartItem cartItem : cartItems) {
            cartItemHelper.calculateCampaignDiscount(cartItem, campaign);
        }

        cartItemRepository.saveAll(cartItems);

        ShoppingCart shoppingCart = shoppingCartRepository.findActiveShoppingCart();
        ShoppingCart result = updateShoppingCartPriceData(shoppingCart);

        return shoppingCartMapper.toDto(result);
    }

    @Transactional
    public ShoppingCartDTO applyCoupon(SimpleLongDTO param) {
        shoppingCartValidator.applyCouponValidator(param);

        Coupon coupon = couponRepository.findById(param.getParam()).get();
        ShoppingCart shoppingCart = shoppingCartRepository.findActiveShoppingCart();

        shoppingCartHelper.calculateCouponDiscount(shoppingCart, coupon);
        ShoppingCart result = updateShoppingCartPriceData(shoppingCart);

        return shoppingCartMapper.toDto(result);
    }

    @Transactional
    public ShoppingCartDTO applyDelivery(DeliveryCostDTO deliveryCostDTO) {
        shoppingCartValidator.applyDeliveryValidator(deliveryCostDTO);

        ShoppingCart shoppingCart = shoppingCartRepository.findActiveShoppingCart();
        deliveryHelper.calculateDelivery(shoppingCart, deliveryCostDTO);

        ShoppingCart result = updateShoppingCartPriceData(shoppingCart);
        return shoppingCartMapper.toDto(result);
    }

    public ShoppingCart updateShoppingCartPriceData(ShoppingCart shoppingCart) {
        shoppingCartHelper.calculateDisplayPrice(shoppingCart);
        return shoppingCartRepository.save(shoppingCart);
    }


}
