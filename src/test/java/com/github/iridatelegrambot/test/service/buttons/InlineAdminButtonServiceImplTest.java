package com.github.iridatelegrambot.test.service.buttons;

import com.github.iridatelegrambot.entity.UserTelegram;
import com.github.iridatelegrambot.service.UserTelegramService;
import com.github.iridatelegrambot.service.buttons.InlineAdminButtonServiceImpl;
import com.github.iridatelegrambot.service.buttons.InlineKeyboardService;
import com.github.iridatelegrambot.service.buttons.InlineKeyboardServiceImpl;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.InlineKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.InlineKeyboardButton;

import java.util.*;


import static com.github.iridatelegrambot.command.CallbackCommand.CallbackCommandName.ADD_STATUS_USER;
import static com.github.iridatelegrambot.command.CallbackCommand.CallbackCommandName.ADMIN_MENU_SET_STATUS;

public class InlineAdminButtonServiceImplTest {
    private InlineKeyboardService inlineKeyboardService;
    private UserTelegramService userTelegramService;
    private InlineAdminButtonServiceImpl inlineAdminButtonService;

    @BeforeEach
    void init(){
        inlineKeyboardService = new InlineKeyboardServiceImpl();
        userTelegramService = Mockito.mock(UserTelegramService.class);
        inlineAdminButtonService = new InlineAdminButtonServiceImpl(inlineKeyboardService,userTelegramService);
    }

    @Test
    void shouldGetButtonsInviteForAdmin(){
        //given
        Long chatId = 12345678L;
        String data = ADD_STATUS_USER.getNameForService() + "chatId:" + chatId + ":false";
        InlineKeyboardButton button = new InlineKeyboardButton();
        button.setText("Отклонить");
        button.setCallbackData(data);
        //when
        InlineKeyboardMarkup markup = inlineAdminButtonService.inviteForAdmin(chatId);
        //then
        Assertions.assertEquals(button,markup.getKeyboard().get(0).get(0));
    }

    @Test
    void shouldGetButtonsWithAllUsersForSetStatusFalse(){
        //given
        UserTelegram userTelegram = new UserTelegram();
        userTelegram.setUserName("Pupa");
        userTelegram.setChatId(12345678L);
        userTelegram.setActive(true);

        List<UserTelegram> users = new ArrayList<>();
        users.add(userTelegram);
        Mockito.when(userTelegramService.getAllUser()).thenReturn(users);

        String data = ADMIN_MENU_SET_STATUS.getNameForService() + "setActive:" +
                userTelegram.getChatId() + ":false";
        //when
        InlineKeyboardMarkup markup = inlineAdminButtonService.showAllUsersForSetStatus(false).get();
        //then
        Assertions.assertEquals(data,markup.getKeyboard().get(0).get(0).getCallbackData());
    }


    @Test
    void shouldGetButtonsWithAllUsersForSetStatusTrue(){
        //given
        UserTelegram userTelegram = new UserTelegram();
        userTelegram.setUserName("Pupa");
        userTelegram.setChatId(12345678L);
        userTelegram.setActive(false);

        List<UserTelegram> users = new ArrayList<>();
        users.add(userTelegram);
        Mockito.when(userTelegramService.getAllUser()).thenReturn(users);

        String data = ADMIN_MENU_SET_STATUS.getNameForService() + "setActive:" +
                userTelegram.getChatId() + ":true";
        //when
        InlineKeyboardMarkup markup = inlineAdminButtonService.showAllUsersForSetStatus(true).get();
        //then
        Assertions.assertEquals(data,markup.getKeyboard().get(0).get(0).getCallbackData());
    }

    @Test
    void shouldNotFindUsersForSetStatus(){
        //given
        Mockito.when(userTelegramService.getAllUser()).thenReturn(new ArrayList<>());
        //when
        Optional<InlineKeyboardMarkup> optional = inlineAdminButtonService.showAllUsersForSetStatus(true);
        //then
        Assertions.assertTrue(optional.isEmpty());
    }

    @Test
    void shouldGetAllUsersForSetAdmin(){
        //given
        UserTelegram userTelegram = new UserTelegram();
        userTelegram.setUserName("Pupa");
        userTelegram.setChatId(12345678L);
        userTelegram.setActive(true);
        userTelegram.setAdmin(false);

        List<UserTelegram> users = new ArrayList<>();
        users.add(userTelegram);
        Mockito.when(userTelegramService.getAllUser()).thenReturn(users);

        String data = ADMIN_MENU_SET_STATUS.getNameForService() + "setAdmin:" +
                userTelegram.getChatId() + ":true";

        //when
        InlineKeyboardMarkup markup = inlineAdminButtonService.showAllUsersForSetAdmin();
        //then
        Assertions.assertEquals(data,markup.getKeyboard().get(0).get(0).getCallbackData());

    }



}
