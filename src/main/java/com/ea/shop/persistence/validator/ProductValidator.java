package com.ea.shop.persistence.validator;

import com.ea.shop.exception.BusinessException;
import com.ea.shop.message.MessageService;
import com.ea.shop.persistence.dto.ProductDTO;
import com.ea.shop.persistence.validator.base.BaseValidator;
import com.ea.shop.util.MetaMessageUtil;
import com.ea.shop.util.StringUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class ProductValidator {

    @Autowired
    private BaseValidator baseValidator;

    @Autowired
    private MessageService messageService;

    public void saveProductValidator(ProductDTO productDTO) {
        baseValidator.validateNullCheck(productDTO);

        MetaMessageUtil metaMessageUtil = new MetaMessageUtil();

        if (StringUtil.isEmpty(productDTO.getTitle())) {
            metaMessageUtil.addMetaMessageErrorSeverity(messageService
                    .getMessage("message.validate.saveProductValidator.title.notNull"));
        }

        if(productDTO.getPrice() == null) {
            metaMessageUtil.addMetaMessageErrorSeverity(messageService
                    .getMessage("message.validate.saveProductValidator.price.notNull"));
        }

        if(productDTO.getCategoryId() == null) {
            metaMessageUtil.addMetaMessageErrorSeverity(messageService
                    .getMessage("message.validate.saveProductValidator.category.notNull"));
        }

        if (!metaMessageUtil.isEmpty()) {
            throw new BusinessException(metaMessageUtil.getMetaMessageList());
        }

    }

}
