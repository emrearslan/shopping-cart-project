package com.ea.shop.persistence.validator.base;

import com.ea.shop.exception.BusinessException;
import com.ea.shop.message.MessageService;
import com.ea.shop.util.MetaMessageUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class BaseValidator {

    @Autowired
    private MessageService messageService;

    public void validateNullCheck(Object object) {
        MetaMessageUtil metaMessageUtil = new MetaMessageUtil();

        if (object == null) {
            metaMessageUtil.addMetaMessageErrorSeverity(
                    messageService.getMessage("message.general.object.notNull", new Object[]{Object.class}));
        }

        if (!metaMessageUtil.isEmpty()) {
            throw new BusinessException(metaMessageUtil.getMetaMessageList());
        }
    }

}
