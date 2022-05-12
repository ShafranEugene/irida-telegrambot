package com.github.iridatelegrambot.test.command.buttons;

import com.github.iridatelegrambot.command.CallbackCommand.AdminMenuCallbackCommand;
import com.github.iridatelegrambot.command.CallbackCommand.CallbackCommand;
import com.github.iridatelegrambot.service.HandleUserTelegramService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.telegram.telegrambots.meta.api.objects.CallbackQuery;


public class AdminMenuCallbackCommandTest extends AbstractCallbackCommandTest {
    private AdminMenuCallbackCommand adminMenuCallbackCommand;
    private HandleUserTelegramService handleUserTelegramService;
    @BeforeEach
    void init(){
        handleUserTelegramService = Mockito.mock(HandleUserTelegramService.class);
        adminMenuCallbackCommand = new AdminMenuCallbackCommand(commandCallbackSenderService,handleUserTelegramService);
    }
    @Override
    protected CallbackCommand getCallbackCommand() {
        return adminMenuCallbackCommand;
    }

    @Test
    void shouldProperlySendClose(){
        //given
        CallbackQuery callbackQuery = createCallbackQuery("admin_menu:closeStatusUser");
        //when
        adminMenuCallbackCommand.execute(callbackQuery);
        //then
        Mockito.verify(commandCallbackSenderService).sendAdminSetStatus(chatId,false,
                "Выберете пользователя которому хотите закрыть доступ.",10);
    }

    @Test
    void shouldProperlySendOpen(){
        //given
        CallbackQuery callbackQuery = createCallbackQuery("admin_menu:openStatusUser");
        //when
        adminMenuCallbackCommand.execute(callbackQuery);
        //then
        Mockito.verify(commandCallbackSenderService).sendAdminSetStatus(chatId,true,
                "Выберете пользователя которому хотите открыть доступ.",10);
    }

    @Test
    void shouldProperlySetAdmin(){
        //given
        CallbackQuery callbackQuery = createCallbackQuery("admin_menu:setAdmin");
        //when
        adminMenuCallbackCommand.execute(callbackQuery);
        //then
        Mockito.verify(commandCallbackSenderService).sendUsersForAdmin(chatId,
                "Выберете пользователю которому хотите выдать права администратора",10);
    }

    @Test
    void shouldProperlyOffAdmin(){
        //given
        CallbackQuery callbackQuery = createCallbackQuery("admin_menu:pullOffAdmin");
        Mockito.when(handleUserTelegramService.checkUserOnEmpty(chatId)).thenReturn(false);
        //when
        adminMenuCallbackCommand.execute(callbackQuery);
        //then
        Mockito.verify(commandCallbackSenderService).sendMessage(chatId.toString(),
                "Вы сняли с себя права администратора!");
    }

}
