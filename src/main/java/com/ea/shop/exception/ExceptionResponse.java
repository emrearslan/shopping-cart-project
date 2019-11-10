package com.ea.shop.exception;

import com.ea.shop.message.MetaMessage;

import java.util.ArrayList;
import java.util.List;

public class ExceptionResponse {

    private List<MetaMessage> errorList;

    public ExceptionResponse() {
    }

    public List<MetaMessage> getErrorList() {
        return errorList;
    }

    public void setErrorList(List<MetaMessage> errorList) {
        this.errorList = errorList;
    }

    public void addMessage(MetaMessage metaMessage) {
        if (getErrorList() == null) {
            this.errorList = new ArrayList<>();
        }

        errorList.add(metaMessage);
    }

}
