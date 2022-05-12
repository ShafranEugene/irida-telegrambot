package com.github.iridatelegrambot.test.command.buttons;

import com.github.iridatelegrambot.command.CallbackCommand.AddOrderCallbackCommand;
import com.github.iridatelegrambot.command.CallbackCommand.CallbackCommand;
import com.github.iridatelegrambot.command.CallbackCommand.CallbackCommandContainer;
import com.github.iridatelegrambot.command.CallbackCommand.CallbackCommandName;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.telegram.telegrambots.meta.api.objects.CallbackQuery;
import org.telegram.telegrambots.meta.api.objects.Message;

public class CallbackCommandContainerTest {
    private final CallbackCommandContainer callbackCommandContainer = new CallbackCommandContainer();

    @Test
    void shouldAddCommandAndFindIt(){
        CallbackCommandName commandName = CallbackCommandName.ADD_ORDER;
        AddOrderCallbackCommand addOrderCallbackCommand = Mockito.mock(AddOrderCallbackCommand.class);
        callbackCommandContainer.setCallbackCommand(commandName.getName(),addOrderCallbackCommand);

        CallbackQuery callbackQuery = new CallbackQuery();
        Message message = Mockito.mock(Message.class);
        Mockito.when(message.getChatId()).thenReturn(12345678L);
        Mockito.when(message.getMessageId()).thenReturn(10);
        callbackQuery.setMessage(message);
        callbackQuery.setData(commandName.getNameForService());

        CallbackCommand callbackCommand = callbackCommandContainer.findAnswer(callbackQuery);
        Assertions.assertEquals(addOrderCallbackCommand,callbackCommand);
    }
}
