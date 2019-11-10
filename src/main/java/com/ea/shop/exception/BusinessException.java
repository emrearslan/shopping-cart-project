package com.ea.shop.exception;

import com.ea.shop.message.MetaMessage;

import java.util.ArrayList;
import java.util.List;

public class BusinessException extends RuntimeException {

    private static final long serialVersionUID = 1L;
    private List<MetaMessage> errorList;

    public BusinessException() {
        super();
    }

    public BusinessException(String message, Throwable cause) {
        super(message, cause);
    }

    public BusinessException(String message) {
        super(message);
        addMessage(message);
    }

    public BusinessException(Throwable cause) {
        super(cause);
    }

    public BusinessException(List<MetaMessage> errorList) {
        this.errorList = errorList;
    }

    public List<MetaMessage> getErrorList() {
        return errorList;
    }

    public void setErrorList(List<MetaMessage> errorList) {
        this.errorList = errorList;
    }

    private void addMessage(String message) {
        if (this.errorList == null) {
            this.errorList = new ArrayList<>();
        }

        MetaMessage newMetaMessage = new MetaMessage();
        newMetaMessage.setMessage(message);
        newMetaMessage.setSeverity(MetaMessage.SEVERITY.ERROR.getDescription());

        errorList.add(newMetaMessage);
    }
}