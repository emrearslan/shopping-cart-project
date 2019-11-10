package com.ea.shop.message;

import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.MessageSource;

@SpringBootTest(classes = MessageService.class)
public class MessageServiceTest {

    @InjectMocks
    private MessageService messageService;

    @Mock
    private MessageSource messageSource;

    @Test
    public void shouldGetMessageWithKey() {
        Mockito.when(messageSource.getMessage(Mockito.any(), Mockito.any(), Mockito.any())).thenReturn("message");
        messageService.getMessage("key");
    }

    @Test
    public void shouldGetMessageWithKeyAndParam() {
        Mockito.when(messageSource.getMessage(Mockito.any(), Mockito.any(), Mockito.any())).thenReturn("message");
        messageService.getMessage("key", new Object[]{"param"});
    }

}