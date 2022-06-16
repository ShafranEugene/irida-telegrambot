package com.github.iridatelegrambot.test.service.sendlers;

import com.github.iridatelegrambot.entity.Order;
import com.github.iridatelegrambot.service.buttons.InlineDocumentButtonService;
import com.github.iridatelegrambot.service.buttons.InlineMenuButtonService;
import com.github.iridatelegrambot.service.senders.SendMessageService;
import com.github.iridatelegrambot.service.senders.SendOrderMenuService;
import com.github.iridatelegrambot.service.senders.SendOrderMenuServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.InlineKeyboardMarkup;

import java.util.Optional;

public class SendOrderMenuServiceImplTest {

    private SendMessageService sendMessageService;
    private InlineMenuButtonService inlineMenuButtonService;
    private InlineDocumentButtonService inlineDocumentButtonService;
    private SendOrderMenuService sendOrderMenuService;
    private Long chatId = 12345678L;
    private String message = "Test message";

    @BeforeEach
    void init(){
        sendMessageService = Mockito.mock(SendMessageService.class);
        inlineMenuButtonService = Mockito.mock(InlineMenuButtonService.class);
        inlineDocumentButtonService = Mockito.mock(InlineDocumentButtonService.class);
        sendOrderMenuService = new SendOrderMenuServiceImpl(sendMessageService,inlineMenuButtonService,inlineDocumentButtonService);
    }

    @Test
    void shouldSendActiveOrder(){
        //given
        InlineKeyboardMarkup markup = new InlineKeyboardMarkup();
        Mockito.when(inlineDocumentButtonService.showActiveOrders()).thenReturn(Optional.of(markup));
        //when
        sendOrderMenuService.sendActiveOrders(chatId,message);
        //then
        Mockito.verify(sendMessageService).sendMessage(chatId.toString(),message,markup);
    }

    @Test
    void shouldNotFindActiveOrder(){
        //given
        Mockito.when(inlineDocumentButtonService.showActiveOrders()).thenReturn(Optional.empty());
        //when
        sendOrderMenuService.sendActiveOrders(chatId,message);
        //then
        Mockito.verify(sendMessageService).sendMessage(chatId.toString(),"Активных заказов не найдено.");
    }

    @Test
    void shouldSendMenuOrder(){
        //given
        Order order = new Order();
        order.setId(10);

        InlineKeyboardMarkup markup = new InlineKeyboardMarkup();
        Mockito.when(inlineMenuButtonService.showMenuOrder(order.getId())).thenReturn(markup);
        //when
        sendOrderMenuService.sendMenuOrder(chatId,order);
        //then
        Mockito.verify(sendMessageService).sendMessage(chatId.toString(),order.toStringForUsers(),markup);
    }
}
