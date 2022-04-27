package com.github.iridatelegrambot.test.command.buttons;

import com.github.iridatelegrambot.service.*;

import com.github.iridatelegrambot.service.send.SendMessageServiceImpl;
import org.mockito.Mockito;
import org.telegram.telegrambots.meta.api.objects.CallbackQuery;
import org.telegram.telegrambots.meta.api.objects.Message;

public class AbstractCallbackCommandTest {

    protected OrderServiceImpl mOrderService = Mockito.mock(OrderServiceImpl.class);
    protected InvoiceServiceImpl mInvoiceService = Mockito.mock(InvoiceServiceImpl.class);
    protected SendMessageServiceImpl mSendMessageService = Mockito.mock(SendMessageServiceImpl.class);
    protected UserTelegramServiceImpl mUserTelegramService = Mockito.mock(UserTelegramServiceImpl.class);

    protected CallbackQuery createCallbackQuery(String data){
        CallbackQuery callbackQuery = new CallbackQuery();
        Message message = Mockito.mock(Message.class);
        Mockito.when(message.getChatId()).thenReturn(12345678L);
        callbackQuery.setMessage(message);
        callbackQuery.setData(data);
        return callbackQuery;
    }
}
