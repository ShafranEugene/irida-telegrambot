package com.github.iridatelegrambot.test.service.statususer;

import com.github.iridatelegrambot.entity.UserTelegram;
import com.github.iridatelegrambot.service.HandleUserTelegramService;
import com.github.iridatelegrambot.service.HandleUserTelegramServiceImpl;
import com.github.iridatelegrambot.service.senders.SendMessageService;
import com.github.iridatelegrambot.service.senders.SendMessageServiceImpl;
import com.github.iridatelegrambot.service.statususer.CheckStatusUserService;
import com.github.iridatelegrambot.service.statususer.CheckStatusUserServiceImpl;
import com.github.iridatelegrambot.service.statususer.SendAdminInviteService;
import com.github.iridatelegrambot.service.statususer.SendAdminInviteServiceImpl;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.telegram.telegrambots.meta.api.objects.Message;
import org.telegram.telegrambots.meta.api.objects.Update;


public class CheckStatusUserServiceImplTest {
    private CheckStatusUserService checkStatusUserService;
    private HandleUserTelegramService handleUserTelegramService;
    private SendMessageService sendMessageServiceImpl;
    private SendAdminInviteService sendAdminInviteServiceImpl;
    private final Long chatId = 12345678L;

    @BeforeEach
    void init(){
        handleUserTelegramService = Mockito.mock(HandleUserTelegramServiceImpl.class);
        sendMessageServiceImpl = Mockito.mock(SendMessageServiceImpl.class);
        sendAdminInviteServiceImpl = Mockito.mock(SendAdminInviteServiceImpl.class);
        checkStatusUserService = new CheckStatusUserServiceImpl(sendMessageServiceImpl,sendAdminInviteServiceImpl,handleUserTelegramService);
    }

    @Test
    void shouldFindActiveUser(){
        //given
        UserTelegram userTelegram = new UserTelegram();
        userTelegram.setChatId(chatId);
        userTelegram.setActive(true);
        Mockito.when(handleUserTelegramService.checkUserOnEmpty(chatId)).thenReturn(false);
        Mockito.when(handleUserTelegramService.getActiveStatusUser(chatId)).thenReturn(true);
        //when-then
        Assertions.assertTrue(checkStatusUserService.check(createUpdate()));
    }

    @Test
    void shouldFindInactiveUser(){
        //given
        UserTelegram userTelegram = new UserTelegram();
        userTelegram.setChatId(chatId);
        userTelegram.setActive(false);
        Mockito.when(handleUserTelegramService.checkUserOnEmpty(chatId)).thenReturn(false);
        Mockito.when(handleUserTelegramService.getActiveStatusUser(chatId)).thenReturn(false);
        //when-then
        Assertions.assertFalse(checkStatusUserService.check(createUpdate()));
        Mockito.verify(sendAdminInviteServiceImpl).send(chatId);
    }

    private Update createUpdate(){
        Update update = new Update();
        Message message = Mockito.mock(Message.class);
        Mockito.when(message.getChatId()).thenReturn(chatId);
        update.setMessage(message);
        return update;
    }

    @Test
    void shouldNotFindUser(){
        //given
        Update update = createUpdate();
        Mockito.when(handleUserTelegramService.checkUserOnEmpty(chatId)).thenReturn(true);
        Mockito.when(handleUserTelegramService.checkOnFirstUser(chatId)).thenReturn(false);
        //when-then
        Assertions.assertFalse(checkStatusUserService.check(update));
        Mockito.verify(sendAdminInviteServiceImpl).send(chatId);
    }

    @Test
    void shouldFindInactiveAdmin(){
        //given
        UserTelegram userTelegram = new UserTelegram();
        userTelegram.setChatId(chatId);
        userTelegram.setActive(false);
        userTelegram.setAdmin(true);
        Mockito.when(handleUserTelegramService.checkUserOnEmpty(chatId)).thenReturn(false);
        Mockito.when(handleUserTelegramService.getActiveStatusUser(chatId)).thenReturn(false);
        Mockito.when(handleUserTelegramService.getAdminStatusUser(chatId)).thenReturn(true);
        //when-then
        Assertions.assertTrue(checkStatusUserService.check(createUpdate()));
    }

    @Test
    void checkFirstUser(){
        Update update = createUpdate();
        UserTelegram userTelegram = new UserTelegram();
        userTelegram.setChatId(chatId);
        Mockito.when(handleUserTelegramService.checkUserOnEmpty(chatId)).thenReturn(true);
        Mockito.when(handleUserTelegramService.checkOnFirstUser(chatId)).thenReturn(true);
        //when-then
        Assertions.assertTrue(checkStatusUserService.check(update));
    }


}
