package com.ea.shop.persistence.validator;

import com.ea.shop.exception.BusinessException;
import com.ea.shop.message.MessageService;
import com.ea.shop.persistence.dto.CouponDTO;
import com.ea.shop.persistence.validator.base.BaseValidator;
import com.ea.shop.util.MetaMessageUtil;
import com.ea.shop.util.StringUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class CouponValidator {

    @Autowired
    private BaseValidator baseValidator;

    @Autowired
    private MessageService messageService;

    public void saveCouponValidator(CouponDTO couponDTO) {
        baseValidator.validateNullCheck(couponDTO);

        MetaMessageUtil metaMessageUtil = new MetaMessageUtil();

        if (StringUtil.isEmpty(couponDTO.getTitle())) {
            metaMessageUtil.addMetaMessageErrorSeverity(messageService
                    .getMessage("message.validate.saveCouponValidator.title.notNull"));
        }

        if (couponDTO.getMinPurchaseAmount() == null) {
            metaMessageUtil.addMetaMessageErrorSeverity(messageService
                    .getMessage("message.validate.saveCouponValidator.minPurchaseAmount.notNull"));
        }

        if (couponDTO.getDiscountAmount() == null) {
            metaMessageUtil.addMetaMessageErrorSeverity(messageService
                    .getMessage("message.validate.saveCouponValidator.discountAmount.notNull"));
        }

        if (couponDTO.getDiscountType() == null) {
            metaMessageUtil.addMetaMessageErrorSeverity(messageService
                    .getMessage("message.validate.saveCouponValidator.discountType.notNull"));
        }

        if (!metaMessageUtil.isEmpty()) {
            throw new BusinessException(metaMessageUtil.getMetaMessageList());
        }

    }

}
