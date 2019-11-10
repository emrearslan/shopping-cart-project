package com.ea.shop.persistence.validator;

import com.ea.shop.exception.BusinessException;
import com.ea.shop.message.MessageService;
import com.ea.shop.persistence.dto.CartItemRefDTO;
import com.ea.shop.persistence.dto.DeliveryCostDTO;
import com.ea.shop.persistence.dto.base.SimpleLongDTO;
import com.ea.shop.persistence.entity.Campaign;
import com.ea.shop.persistence.entity.CartItem;
import com.ea.shop.persistence.entity.Coupon;
import com.ea.shop.persistence.entity.ShoppingCart;
import com.ea.shop.persistence.repository.*;
import com.ea.shop.persistence.validator.base.BaseValidator;
import com.ea.shop.util.MetaMessageUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;

import java.util.Set;

@Component
public class ShoppingCartValidator {

    @Autowired
    private BaseValidator baseValidator;

    @Autowired
    private MessageService messageService;

    @Autowired
    private ShoppingCartRepository shoppingCartRepository;

    @Autowired
    private CartItemRepository cartItemRepository;

    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private CampaignRepository campaignRepository;

    @Autowired
    private CouponRepository couponRepository;

    public void addItemValidator(CartItemRefDTO cartItemRefDTO) {
        baseValidator.validateNullCheck(cartItemRefDTO);
        cartItemRefProductValidator(cartItemRefDTO);
    }

    private void cartItemRefProductValidator(CartItemRefDTO cartItemRefDTO) {
        MetaMessageUtil metaMessageUtil = new MetaMessageUtil();

        if (cartItemRefDTO.getProductId() == null) {
            metaMessageUtil.addMetaMessageErrorSeverity(messageService
                    .getMessage("message.validate.cartItemRefProductValidator.product.notNull"));
        } else if (!productRepository.findById(cartItemRefDTO.getProductId()).isPresent()) {
            metaMessageUtil.addMetaMessageErrorSeverity(messageService
                    .getMessage("message.validate.cartItemRefProductValidator.product.notExist"));
        }

        if (!metaMessageUtil.isEmpty()) {
            throw new BusinessException(metaMessageUtil.getMetaMessageList());
        }
    }

    public void removeItemValidator(CartItemRefDTO cartItemRefDTO) {
        baseValidator.validateNullCheck(cartItemRefDTO);
        cartItemRefProductValidator(cartItemRefDTO);

        MetaMessageUtil metaMessageUtil = new MetaMessageUtil();
        ShoppingCart shoppingCart = shoppingCartRepository.findActiveShoppingCart();
        CartItem cartItem = shoppingCart.getCartItems().stream()
                .filter(p -> p.getProduct().getId().equals(cartItemRefDTO.getProductId())).findAny().orElse(null);

        if (cartItem == null) {
            metaMessageUtil.addMetaMessageErrorSeverity(messageService
                    .getMessage("message.validate.removeItemValidator.cartItem.notExist"));
        } else if (cartItem.getQuantity() < cartItemRefDTO.getQuantity()) {
            metaMessageUtil.addMetaMessageErrorSeverity(messageService
                    .getMessage("message.validate.removeItemValidator.quantity.moreThanItemQuantity"));
        }

        if (!metaMessageUtil.isEmpty()) {
            throw new BusinessException(metaMessageUtil.getMetaMessageList());
        }
    }

    public void applyCampaignValidator(SimpleLongDTO param) {
        baseValidator.validateNullCheck(param);

        MetaMessageUtil metaMessageUtil = new MetaMessageUtil();

        Campaign campaign;
        if (param.getParam() == null) {
            metaMessageUtil.addMetaMessageErrorSeverity(messageService
                    .getMessage("message.validate.applyCampaignValidator.campaign.notNull"));
            throw new BusinessException(metaMessageUtil.getMetaMessageList());
        } else {
            campaign = campaignRepository.findById(param.getParam()).orElse(null);
            if (campaign == null) {
                metaMessageUtil.addMetaMessageErrorSeverity(messageService
                        .getMessage("message.validate.applyCampaignValidator.campaign.notExist"));
                throw new BusinessException(metaMessageUtil.getMetaMessageList());
            }
        }

        Set<CartItem> cartItems = cartItemRepository.findByCategoryId(campaign.getCategory().getId());
        if (CollectionUtils.isEmpty(cartItems)) {
            metaMessageUtil.addMetaMessageErrorSeverity(messageService
                    .getMessage("message.validate.applyCampaignValidator.category.notExist"));
        } else {
            int itemCount = cartItems.stream().map(p -> p.getQuantity()).reduce(0, Integer::sum);
            if (!campaign.isUsableCampaign(itemCount)) {
                metaMessageUtil.addMetaMessageErrorSeverity(messageService
                        .getMessage("message.validate.applyCampaignValidator.itemLimit.moreThanItemCount"));
            }
        }

        if (!metaMessageUtil.isEmpty()) {
            throw new BusinessException(metaMessageUtil.getMetaMessageList());
        }
    }

    public void applyCouponValidator(SimpleLongDTO param) {
        baseValidator.validateNullCheck(param);

        MetaMessageUtil metaMessageUtil = new MetaMessageUtil();

        Coupon coupon;
        if (param.getParam() == null) {
            metaMessageUtil.addMetaMessageErrorSeverity(messageService
                    .getMessage("message.validate.applyCouponValidator.coupon.notNull"));
            throw new BusinessException(metaMessageUtil.getMetaMessageList());
        } else {
            coupon = couponRepository.findById(param.getParam()).orElse(null);
            if (coupon == null) {
                metaMessageUtil.addMetaMessageErrorSeverity(messageService
                        .getMessage("message.validate.applyCouponValidator.coupon.notExist"));
                throw new BusinessException(metaMessageUtil.getMetaMessageList());
            }
        }

        ShoppingCart shoppingCart = shoppingCartRepository.findActiveShoppingCart();

        if (!shoppingCart.isUsedCampaignDiscount()) {
            metaMessageUtil.addMetaMessageErrorSeverity(messageService
                    .getMessage("message.validate.applyCampaignValidator.campaignDiscount.notUsed"));
        }

        if (!coupon.isUsableCoupon(shoppingCart.getTotalFinalPrice())) {
            metaMessageUtil.addMetaMessageErrorSeverity(messageService
                    .getMessage("message.validate.applyCampaignValidator.minPurchaseAmount.moreThanTotalFinalPrice"));
        }

        if (!metaMessageUtil.isEmpty()) {
            throw new BusinessException(metaMessageUtil.getMetaMessageList());
        }
    }

    public void applyDeliveryValidator(DeliveryCostDTO deliveryCostDTO) {
        baseValidator.validateNullCheck(deliveryCostDTO);

        MetaMessageUtil metaMessageUtil = new MetaMessageUtil();

        if (deliveryCostDTO.getCostPerDelivery() == null) {
            metaMessageUtil.addMetaMessageErrorSeverity(messageService
                    .getMessage("message.validate.applyDeliveryValidator.costPerDelivery.notNull"));
        }

        if (deliveryCostDTO.getCostPerProduct() == null) {
            metaMessageUtil.addMetaMessageErrorSeverity(messageService
                    .getMessage("message.validate.applyDeliveryValidator.costPerProduct.notNull"));
        }

        if (!metaMessageUtil.isEmpty()) {
            throw new BusinessException(metaMessageUtil.getMetaMessageList());
        }
    }

}
