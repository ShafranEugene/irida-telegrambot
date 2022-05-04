package com.github.iridatelegrambot.test.command.buttons;

import com.github.iridatelegrambot.command.CallbackCommand.CallbackCommand;
import com.github.iridatelegrambot.command.CallbackCommand.CallbackCommandContainer;
import com.github.iridatelegrambot.service.*;

import com.github.iridatelegrambot.service.send.SendMessageServiceImpl;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.telegram.telegrambots.meta.api.objects.CallbackQuery;
import org.telegram.telegrambots.meta.api.objects.Message;

public abstract class AbstractCallbackCommandTest {

    protected OrderServiceImpl mOrderService = Mockito.mock(OrderServiceImpl.class);
    protected InvoiceServiceImpl mInvoiceService = Mockito.mock(InvoiceServiceImpl.class);
    protected SendMessageServiceImpl mSendMessageService = Mockito.mock(SendMessageServiceImpl.class);
    protected UserTelegramServiceImpl mUserTelegramService = Mockito.mock(UserTelegramServiceImpl.class);

    protected abstract CallbackCommand getCallbackCommand();

    protected CallbackQuery createCallbackQuery(String data){
        CallbackQuery callbackQuery = new CallbackQuery();
        Message message = Mockito.mock(Message.class);
        Mockito.when(message.getChatId()).thenReturn(12345678L);
        callbackQuery.setMessage(message);
        callbackQuery.setData(data);
        return callbackQuery;
    }

    @Test
    void shouldSetCommandInContainer(){
        //given
        CallbackCommandContainer callbackCommandContainer = Mockito.mock(CallbackCommandContainer.class);
        CallbackCommand callbackCommand = getCallbackCommand();
        //when
        callbackCommand.containerFiller(callbackCommandContainer);
        //then
        Mockito.verify(callbackCommandContainer).setCallbackCommand(callbackCommand.getNameCommand(),callbackCommand);
    }
}
