package com.ea.shop.util;

import com.ea.shop.message.MetaMessage;

import java.util.ArrayList;
import java.util.List;

public class MetaMessageUtil {

    private List<MetaMessage> metaMessageList;

    public void addMetaMessage(String message, String severity) {
        MetaMessage metaMessage = new MetaMessage(message, severity);
        if (metaMessageList == null) {
            metaMessageList = new ArrayList<>();
        }
        metaMessageList.add(metaMessage);
    }

    public void addMetaMessage(String message, MetaMessage.SEVERITY severity) {
        MetaMessage metaMessage = new MetaMessage(message, severity.getDescription());
        if (metaMessageList == null) {
            metaMessageList = new ArrayList<>();
        }
        metaMessageList.add(metaMessage);
    }

    public void addMetaMessage(MetaMessage metaMessage) {
        if (metaMessageList == null) {
            metaMessageList = new ArrayList<>();
        }
        metaMessageList.add(metaMessage);
    }

    public void addMetaMessageErrorSeverity(String message) {
        addMetaMessage(message, MetaMessage.SEVERITY.ERROR);
    }

    public void addMetaMessageInfoSeverity(String message) {
        addMetaMessage(message, MetaMessage.SEVERITY.INFO);
    }

    public void addMetaMessageWarningSeverity(String message) {
        addMetaMessage(message, MetaMessage.SEVERITY.WARNING);
    }

    public Boolean isEmpty() {
        return metaMessageList == null || metaMessageList.isEmpty();
    }

    public List<MetaMessage> getMetaMessageList() {
        return metaMessageList;
    }

    public void setMetaMessageList(List<MetaMessage> metaMessageList) {
        this.metaMessageList = metaMessageList;
    }

}
