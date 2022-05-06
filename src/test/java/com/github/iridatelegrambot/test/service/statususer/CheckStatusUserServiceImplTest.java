package com.github.iridatelegrambot.test.service.statususer;

import com.github.iridatelegrambot.entity.UserTelegram;
import com.github.iridatelegrambot.service.UserTelegramService;
import com.github.iridatelegrambot.service.UserTelegramServiceImpl;
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

import java.util.*;
import java.util.Optional;

public class CheckStatusUserServiceImplTest {
    private CheckStatusUserService checkStatusUserService;
    private UserTelegramService userTelegramServiceImpl;
    private SendMessageService sendMessageServiceImpl;
    private SendAdminInviteService sendAdminInviteServiceImpl;
    private final Long chatId = 12345678L;

    @BeforeEach
    void init(){
        userTelegramServiceImpl = Mockito.mock(UserTelegramServiceImpl.class);
        sendMessageServiceImpl = Mockito.mock(SendMessageServiceImpl.class);
        sendAdminInviteServiceImpl = Mockito.mock(SendAdminInviteServiceImpl.class);
        checkStatusUserService = new CheckStatusUserServiceImpl(sendMessageServiceImpl,userTelegramServiceImpl,sendAdminInviteServiceImpl);
    }

    @Test
    void shouldFindActiveUser(){
        //given
        UserTelegram userTelegram = new UserTelegram();
        userTelegram.setChatId(chatId);
        userTelegram.setActive(true);
        Mockito.when(userTelegramServiceImpl.findByChatId(chatId)).thenReturn(Optional.of(userTelegram));
        //when-then
        Assertions.assertTrue(checkStatusUserService.check(createUpdate()));
    }

    @Test
    void shouldFindInactiveUser(){
        //given
        UserTelegram userTelegram = new UserTelegram();
        userTelegram.setChatId(chatId);
        userTelegram.setActive(false);
        Mockito.when(userTelegramServiceImpl.findByChatId(chatId)).thenReturn(Optional.of(userTelegram));
        //when-then
        Assertions.assertFalse(checkStatusUserService.check(createUpdate()));
        Mockito.verify(sendAdminInviteServiceImpl).send(userTelegram);
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
        UserTelegram userTelegram = new UserTelegram();
        userTelegram.setChatId(chatId);
        Mockito.when(userTelegramServiceImpl.findByChatId(chatId)).thenReturn(Optional.empty());
        Mockito.when(userTelegramServiceImpl.findOrCreateUser(update)).thenReturn(userTelegram);
        List<UserTelegram> userList = new ArrayList<>();
        userList.add(new UserTelegram());
        Mockito.when(userTelegramServiceImpl.getAllActiveUser()).thenReturn(userList);
        //when-then
        Assertions.assertFalse(checkStatusUserService.check(update));
        Mockito.verify(sendAdminInviteServiceImpl).send(userTelegram);
    }

    @Test
    void shouldFindInactiveAdmin(){
        //given
        UserTelegram userTelegram = new UserTelegram();
        userTelegram.setChatId(chatId);
        userTelegram.setActive(false);
        userTelegram.setAdmin(true);
        Mockito.when(userTelegramServiceImpl.findByChatId(chatId)).thenReturn(Optional.of(userTelegram));
        //when-then
        Assertions.assertTrue(checkStatusUserService.check(createUpdate()));
    }

    @Test
    void checkFirstUser(){
        Update update = createUpdate();
        UserTelegram userTelegram = new UserTelegram();
        userTelegram.setChatId(chatId);
        Mockito.when(userTelegramServiceImpl.findByChatId(chatId)).thenReturn(Optional.empty());
        Mockito.when(userTelegramServiceImpl.findOrCreateUser(update)).thenReturn(userTelegram);
        Mockito.when(userTelegramServiceImpl.getAllActiveUser()).thenReturn(new ArrayList<>());
        //when-then
        Assertions.assertTrue(checkStatusUserService.check(update));
    }


}
