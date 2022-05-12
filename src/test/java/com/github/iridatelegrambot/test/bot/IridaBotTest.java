package com.github.iridatelegrambot.test.bot;

import com.github.iridatelegrambot.bot.IridaBot;
import com.github.iridatelegrambot.command.CallbackCommand.CallbackCommandContainer;
import com.github.iridatelegrambot.command.CommandContainer;
import com.github.iridatelegrambot.service.statususer.CheckStatusUserService;
import com.github.iridatelegrambot.service.statuswait.HandleWaitNumber;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.telegram.telegrambots.meta.api.objects.CallbackQuery;
import org.telegram.telegrambots.meta.api.objects.Message;
import org.telegram.telegrambots.meta.api.objects.Update;

import static org.mockito.Mockito.times;

public class IridaBotTest {
    private IridaBot iridaBot;
    private CommandContainer container;
    private CallbackCommandContainer callbackCommandContainer;
    private HandleWaitNumber handleWaitNumber;
    private CheckStatusUserService checkStatusUserService;

    @BeforeEach
    void init(){
        container = Mockito.mock(CommandContainer.class);
        callbackCommandContainer = Mockito.mock(CallbackCommandContainer.class);
        handleWaitNumber = Mockito.mock(HandleWaitNumber.class);
        checkStatusUserService = Mockito.mock(CheckStatusUserService.class);
        iridaBot = new IridaBot(container,callbackCommandContainer,handleWaitNumber,checkStatusUserService);
    }

    @Test
    void shouldBlockMessageFromInactiveUsers(){
        //given
        Update update = createUpdate("/test");
        Mockito.when(checkStatusUserService.check(update)).thenReturn(false);
        //when
        iridaBot.onUpdateReceived(update);
        //then
        Mockito.verify(handleWaitNumber,times(0)).handle(update);
        Mockito.verify(container,times(0)).findCommand("test");
        Mockito.verify(callbackCommandContainer,times(0)).findAnswer(new CallbackQuery());
    }

    Update createUpdate(String text){
        Update update = new Update();
        Message message = Mockito.mock(Message.class);
        Mockito.when(message.getChatId()).thenReturn(12345678L);
        Mockito.when(message.getText()).thenReturn(text);
        Mockito.when(message.hasText()).thenReturn(true);
        update.setMessage(message);
        return update;
    }
}
