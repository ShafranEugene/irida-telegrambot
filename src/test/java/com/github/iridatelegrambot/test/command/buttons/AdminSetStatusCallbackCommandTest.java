package com.github.iridatelegrambot.test.command.buttons;

import com.github.iridatelegrambot.command.CallbackCommand.AdminSetStatusCallbackCommand;
import com.github.iridatelegrambot.command.CallbackCommand.CallbackCommand;
import com.github.iridatelegrambot.service.HandleUserTelegramService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.telegram.telegrambots.meta.api.objects.CallbackQuery;

public class AdminSetStatusCallbackCommandTest extends AbstractCallbackCommandTest {

    private AdminSetStatusCallbackCommand setStatusCallbackCommand;
    private HandleUserTelegramService handleUserTelegramService;
    @BeforeEach
    void init(){
        handleUserTelegramService = Mockito.mock(HandleUserTelegramService.class);
        setStatusCallbackCommand = new AdminSetStatusCallbackCommand(commandCallbackSenderService,handleUserTelegramService);
    }
    @Override
    protected CallbackCommand getCallbackCommand() {
        return setStatusCallbackCommand;
    }
    @Test
    void shouldSetAdminStatus(){
        //given
        CallbackQuery callbackQuery = createCallbackQuery("admin_set_status:setAdmin:10:true");
        Mockito.when(handleUserTelegramService.checkUserIsPresent(10L)).thenReturn(true);
        //when
        setStatusCallbackCommand.execute(callbackQuery);
        //then
        Mockito.verify(handleUserTelegramService).setUserAdminStatus(10L,true);
    }

    @Test
    void shouldNotFindSetAdminStatus(){
        //given
        CallbackQuery callbackQuery = createCallbackQuery("admin_set_status:setAdmin:10:true");
        Mockito.when(handleUserTelegramService.checkUserIsPresent(10L)).thenReturn(false);
        //when
        setStatusCallbackCommand.execute(callbackQuery);
        //then
        Mockito.verify(commandCallbackSenderService).sendMessage(chatId.toString(),"Не смог найти такого пользователя.");
    }

    @Test
    void shouldSetActiveStatus(){
        //given
        CallbackQuery callbackQuery = createCallbackQuery("admin_set_status:setActive:10:true");
        Mockito.when(handleUserTelegramService.checkUserIsPresent(10L)).thenReturn(true);
        //when
        setStatusCallbackCommand.execute(callbackQuery);
        //then
        Mockito.verify(handleUserTelegramService).setUserActiveStatus(10L,true);
    }

    @Test
    void shouldNotFindSetActiveStatus(){
        //given
        CallbackQuery callbackQuery = createCallbackQuery("admin_set_status:setActive:10:true");
        Mockito.when(handleUserTelegramService.checkUserIsPresent(10L)).thenReturn(false);
        //when
        setStatusCallbackCommand.execute(callbackQuery);
        //then
        Mockito.verify(commandCallbackSenderService).sendMessage(chatId.toString(),"Не смог найти такого пользователя.");
    }
}
