package com.ea.shop.persistence.validator;

import com.ea.shop.exception.BusinessException;
import com.ea.shop.message.MessageService;
import com.ea.shop.persistence.dto.CampaignDTO;
import com.ea.shop.persistence.validator.base.BaseValidator;
import com.ea.shop.util.MetaMessageUtil;
import com.ea.shop.util.StringUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class CampaignValidator {

    @Autowired
    private BaseValidator baseValidator;

    @Autowired
    private MessageService messageService;

    public void saveCampaignValidator(CampaignDTO campaignDTO) {
        baseValidator.validateNullCheck(campaignDTO);

        MetaMessageUtil metaMessageUtil = new MetaMessageUtil();

        if (StringUtil.isEmpty(campaignDTO.getTitle())) {
            metaMessageUtil.addMetaMessageErrorSeverity(messageService
                    .getMessage("message.validate.saveCampaignValidator.title.notNull"));
        }

        if (campaignDTO.getCategoryId() == null) {
            metaMessageUtil.addMetaMessageErrorSeverity(messageService
                    .getMessage("message.validate.saveCampaignValidator.category.notNull"));
        }

        if (campaignDTO.getDiscountAmount() == null) {
            metaMessageUtil.addMetaMessageErrorSeverity(messageService
                    .getMessage("message.validate.saveCampaignValidator.discountAmount.notNull"));
        }

        if (campaignDTO.getItemLimit() == null) {
            metaMessageUtil.addMetaMessageErrorSeverity(messageService
                    .getMessage("message.validate.saveCampaignValidator.itemLimit.notNull"));
        }

        if (campaignDTO.getDiscountType() == null) {
            metaMessageUtil.addMetaMessageErrorSeverity(messageService
                    .getMessage("message.validate.saveCampaignValidator.discountType.notNull"));
        }

        if (!metaMessageUtil.isEmpty()) {
            throw new BusinessException(metaMessageUtil.getMetaMessageList());
        }

    }

}
