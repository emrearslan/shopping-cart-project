package com.ea.shop.util;

import com.ea.shop.message.MetaMessage;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest(classes = MathUtil.class)
public class MetaMessageUtilTest {

    private MetaMessageUtil metaMessageUtil;

    @BeforeEach
    public void setup() {
        metaMessageUtil = new MetaMessageUtil();
    }

    @Test
    public void shouldAddMetaMessageWithMessageAndSeverityStr() {
        metaMessageUtil.addMetaMessage("message", MetaMessage.SEVERITY.ERROR.getDescription());
        Assertions.assertTrue(metaMessageUtil.getMetaMessageList().stream()
                .anyMatch(p -> (p.getMessage().equals("message")
                        && p.getSeverity().equals(MetaMessage.SEVERITY.ERROR.getDescription()))));
    }

    @Test
    public void shouldAddMetaMessageWithMessageAndSeverityType() {
        metaMessageUtil.addMetaMessage("message", MetaMessage.SEVERITY.ERROR);
        Assertions.assertTrue(metaMessageUtil.getMetaMessageList().stream()
                .anyMatch(p -> (p.getMessage().equals("message")
                        && p.getSeverity().equals(MetaMessage.SEVERITY.ERROR.getDescription()))));
    }

    @Test
    public void shouldAddMetaMessageWithMetaMessage() {
        MetaMessage metaMessage = new MetaMessage("message", MetaMessage.SEVERITY.ERROR.getDescription());

        metaMessageUtil.addMetaMessage(metaMessage);
        Assertions.assertTrue(metaMessageUtil.getMetaMessageList().stream()
                .anyMatch(p -> (p.getMessage().equals("message")
                        && p.getSeverity().equals(MetaMessage.SEVERITY.ERROR.getDescription()))));
    }

    @Test
    public void shouldAddMetaMessageErrorSeverity() {
        metaMessageUtil.addMetaMessageErrorSeverity("message");
        Assertions.assertTrue(metaMessageUtil.getMetaMessageList().stream()
                .anyMatch(p -> (p.getMessage().equals("message")
                        && p.getSeverity().equals(MetaMessage.SEVERITY.ERROR.getDescription()))));

    }

    @Test
    public void shouldAddMetaMessageInfoSeverity() {
        metaMessageUtil.addMetaMessageInfoSeverity("message");
        Assertions.assertTrue(metaMessageUtil.getMetaMessageList().stream()
                .anyMatch(p -> (p.getMessage().equals("message")
                        && p.getSeverity().equals(MetaMessage.SEVERITY.INFO.getDescription()))));

    }

    @Test
    public void shouldAddMetaMessageWarningSeverity() {
        metaMessageUtil.addMetaMessageWarningSeverity("message");
        Assertions.assertTrue(metaMessageUtil.getMetaMessageList().stream()
                .anyMatch(p -> (p.getMessage().equals("message")
                        && p.getSeverity().equals(MetaMessage.SEVERITY.WARNING.getDescription()))));

    }


}