package com.github.iridatelegrambot.test.bot;

import com.github.iridatelegrambot.bot.IridaBot;
import com.github.iridatelegrambot.command.*;
import com.github.iridatelegrambot.command.CallbackCommand.AddOrderCallbackCommand;
import com.github.iridatelegrambot.command.CallbackCommand.CallbackCommandContainer;
import com.github.iridatelegrambot.command.CallbackCommand.CallbackCommandName;
import com.github.iridatelegrambot.command.CallbackCommand.NotFindCallbackCommand;
import com.github.iridatelegrambot.service.buttons.CommandNameForButtons;
import com.github.iridatelegrambot.service.statususer.CheckStatusUserService;
import com.github.iridatelegrambot.service.statuswait.HandleWaitNumber;
import com.github.iridatelegrambot.service.statuswait.WaitDocument;
import com.github.iridatelegrambot.service.statuswait.WaitTypeStatus;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.telegram.telegrambots.meta.api.objects.CallbackQuery;
import org.telegram.telegrambots.meta.api.objects.Chat;
import org.telegram.telegrambots.meta.api.objects.Message;
import org.telegram.telegrambots.meta.api.objects.Update;

import java.util.Objects;

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
        Chat chat = Mockito.mock(Chat.class);
        Mockito.when(chat.getUserName()).thenReturn("Lupa");
        Mockito.when(message.getChat()).thenReturn(chat);
        update.setMessage(message);
        Mockito.when(checkStatusUserService.check(update)).thenReturn(true);
        return update;
    }

    @Test
    void shouldCatchCallback(){
        //given
        Update update = new Update();
        Message message = Mockito.mock(Message.class);
        Chat chat = Mockito.mock(Chat.class);
        Mockito.when(chat.getUserName()).thenReturn("Lupa");
        Mockito.when(message.getChat()).thenReturn(chat);
        update.setMessage(message);
        CallbackQuery callbackQuery = new CallbackQuery();
        callbackQuery.setMessage(message);
        update.setCallbackQuery(callbackQuery);
        Mockito.when(checkStatusUserService.check(update)).thenReturn(true);
        Mockito.when(callbackCommandContainer.findAnswer(callbackQuery)).thenReturn(Mockito.mock(AddOrderCallbackCommand.class));
        //when
        iridaBot.onUpdateReceived(update);
        //then
        Mockito.verify(callbackCommandContainer).findAnswer(callbackQuery);
    }

    @Test
    void shouldCatchMessage(){
        //given
        Update update = createUpdate("/stat");
        StatCommand statCommand = Mockito.mock(StatCommand.class);
        WaitDocument.INVOICE.setWaitNumber(12345678L,false, WaitTypeStatus.ADD);
        WaitDocument.ORDER.setWaitNumber(12345678L,false, WaitTypeStatus.ADD);
        Mockito.when(container.findCommand("/stat")).thenReturn(statCommand);
        //when
        iridaBot.onUpdateReceived(update);
        //then
        Mockito.verify(statCommand).execute(update);
    }

    @Test
    void shouldNotFindMessage(){
        //given
        Update update = createUpdate("test");
        NoCommand noCommand = Mockito.mock(NoCommand.class);
        WaitDocument.INVOICE.setWaitNumber(12345678L,false, WaitTypeStatus.ADD);
        WaitDocument.ORDER.setWaitNumber(12345678L,false, WaitTypeStatus.ADD);
        Mockito.when(container.findCommand(CommandName.NO.getCommandName())).thenReturn(noCommand);
        //when
        iridaBot.onUpdateReceived(update);
        //then
        Mockito.verify(noCommand).execute(update);
    }

    @Test
    void shouldCatchMainCommand(){
        //given
        Update update = createUpdate(CommandNameForButtons.HELP.getName());
        HelpCommand helpCommand = Mockito.mock(HelpCommand.class);
        WaitDocument.INVOICE.setWaitNumber(12345678L,false, WaitTypeStatus.ADD);
        WaitDocument.ORDER.setWaitNumber(12345678L,false, WaitTypeStatus.ADD);
        Mockito.when(container.findCommand(Objects.requireNonNull(CommandNameForButtons.findCommandName("Помощь")).getCommandName()))
                .thenReturn(helpCommand);
        //when
        iridaBot.onUpdateReceived(update);
        //then
        Mockito.verify(helpCommand).execute(update);
    }

    @Test
    void shouldNotFindCallbackCommand(){
        //given
        Update update = new Update();
        CallbackQuery callbackQuery = Mockito.mock(CallbackQuery.class);
        Mockito.when(callbackQuery.getData()).thenReturn("test_1");
        Message message = Mockito.mock(Message.class);
        Chat chat = Mockito.mock(Chat.class);
        Mockito.when(chat.getUserName()).thenReturn("Lupa");
        Mockito.when(message.getChat()).thenReturn(chat);
        update.setMessage(message);
        Mockito.when(callbackQuery.getMessage()).thenReturn(message);
        update.setCallbackQuery(callbackQuery);

        CallbackCommandContainer callbackContainer = new CallbackCommandContainer();
        NotFindCallbackCommand notFindCallbackCommand = Mockito.mock(NotFindCallbackCommand.class);
        callbackContainer.setCallbackCommand(CallbackCommandName.NOT_FIND.getName(),notFindCallbackCommand);
        Mockito.when(checkStatusUserService.check(update)).thenReturn(true);
        IridaBot iridaBot1 = new IridaBot(container,callbackContainer,handleWaitNumber,checkStatusUserService);
        //when
        iridaBot1.onUpdateReceived(update);
        //then
        Mockito.verify(notFindCallbackCommand).execute(callbackQuery);
    }
}
