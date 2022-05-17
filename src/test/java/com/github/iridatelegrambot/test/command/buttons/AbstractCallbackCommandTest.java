package com.github.iridatelegrambot.test.command.buttons;

import com.github.iridatelegrambot.command.CallbackCommand.CallbackCommand;
import com.github.iridatelegrambot.command.CallbackCommand.CallbackCommandContainer;
import com.github.iridatelegrambot.service.*;

import com.github.iridatelegrambot.service.senders.CommandCallbackSenderService;
import com.github.iridatelegrambot.service.senders.CommandCallbackSenderServiceImpl;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.telegram.telegrambots.meta.api.objects.CallbackQuery;
import org.telegram.telegrambots.meta.api.objects.Chat;
import org.telegram.telegrambots.meta.api.objects.Message;

public abstract class AbstractCallbackCommandTest {

    protected OrderServiceImpl mOrderService = Mockito.mock(OrderServiceImpl.class);
    protected InvoiceServiceImpl mInvoiceService = Mockito.mock(InvoiceServiceImpl.class);
    protected UserTelegramServiceImpl mUserTelegramService = Mockito.mock(UserTelegramServiceImpl.class);
    protected CommandCallbackSenderService commandCallbackSenderService = Mockito.mock(CommandCallbackSenderServiceImpl.class);
    protected Long chatId = 12345678L;

    protected abstract CallbackCommand getCallbackCommand();

    protected CallbackQuery createCallbackQuery(String data){
        CallbackQuery callbackQuery = new CallbackQuery();
        Message message = Mockito.mock(Message.class);
        Mockito.when(message.getChatId()).thenReturn(chatId);
        Mockito.when(message.getMessageId()).thenReturn(10);
        Chat chat = Mockito.mock(Chat.class);
        Mockito.when(chat.getUserName()).thenReturn("Lupa");
        Mockito.when(message.getChat()).thenReturn(chat);
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
