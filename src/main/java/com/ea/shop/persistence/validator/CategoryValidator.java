package com.ea.shop.persistence.validator;

import com.ea.shop.exception.BusinessException;
import com.ea.shop.message.MessageService;
import com.ea.shop.persistence.dto.CategoryDTO;
import com.ea.shop.persistence.validator.base.BaseValidator;
import com.ea.shop.util.MetaMessageUtil;
import com.ea.shop.util.StringUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class CategoryValidator {

    @Autowired
    private BaseValidator baseValidator;

    @Autowired
    private MessageService messageService;

    public void saveCategoryValidator(CategoryDTO categoryDTO) {
        baseValidator.validateNullCheck(categoryDTO);

        MetaMessageUtil metaMessageUtil = new MetaMessageUtil();

        if (StringUtil.isEmpty(categoryDTO.getTitle())) {
            metaMessageUtil.addMetaMessageErrorSeverity(messageService
                    .getMessage("message.validate.saveCategoryValidator.title.notNull"));
        }

        if (!metaMessageUtil.isEmpty()) {
            throw new BusinessException(metaMessageUtil.getMetaMessageList());
        }

    }
}
