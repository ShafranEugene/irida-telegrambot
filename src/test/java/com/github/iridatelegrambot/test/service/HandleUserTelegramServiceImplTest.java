package com.github.iridatelegrambot.test.service;

import com.github.iridatelegrambot.entity.UserTelegram;
import com.github.iridatelegrambot.service.HandleUserTelegramServiceImpl;
import com.github.iridatelegrambot.service.UserTelegramService;
import com.github.iridatelegrambot.service.senders.SendMessageService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.telegram.telegrambots.meta.api.objects.Update;

import java.util.Optional;

import static org.mockito.ArgumentMatchers.eq;

public class HandleUserTelegramServiceImplTest {

    private UserTelegramService userTelegramService;
    private SendMessageService sendMessageService;
    private HandleUserTelegramServiceImpl handleUserTelegramService;
    private Long chatId = 12345678L;

    @BeforeEach
    void init(){
        userTelegramService = Mockito.mock(UserTelegramService.class);
        sendMessageService = Mockito.mock(SendMessageService.class);
        handleUserTelegramService = new HandleUserTelegramServiceImpl(userTelegramService,sendMessageService);
    }

    @Test
    void shouldGetInfo(){
        //given
        UserTelegram userTelegram = new UserTelegram();
        Mockito.when(userTelegramService.findByChatId(chatId)).thenReturn(Optional.of(userTelegram));
        //when
        String info = handleUserTelegramService.toStringInfoForUser(chatId);
        //then
        Assertions.assertTrue(info.contains("Id:"));
    }

    @Test
    void shouldNotFindUserForInfo(){
        //given
        Mockito.when(userTelegramService.findByChatId(chatId)).thenReturn(Optional.empty());
        //when
        String info = handleUserTelegramService.toStringInfoForUser(chatId);
        //then
        Assertions.assertEquals("Пользователь не найден.",info);
    }

    @Test
    void shouldSetActiveStatusUser(){
        //given
        UserTelegram userTelegram = createUserTelegram();
        Mockito.when(userTelegramService.findByChatId(chatId)).thenReturn(Optional.of(userTelegram));
        //when
        handleUserTelegramService.setUserActiveStatus(chatId,true,chatId);
        //then
        Mockito.verify(userTelegramService).save(userTelegram);
    }

    UserTelegram createUserTelegram(){
        UserTelegram userTelegram = new UserTelegram();
        userTelegram.setChatId(chatId);
        userTelegram.setAdmin(false);
        userTelegram.setActive(false);
        return userTelegram;
    }

    @Test
    void shouldSetAdminStatusUser(){
        //given
        UserTelegram userTelegram = createUserTelegram();
        Mockito.when(userTelegramService.findByChatId(chatId)).thenReturn(Optional.of(userTelegram));
        //when
        handleUserTelegramService.setUserAdminStatus(chatId,true,chatId);
        //then
        Mockito.verify(userTelegramService).save(userTelegram);
    }

    @Test
    void shouldGetStatusUser(){
        //given
        Mockito.when(userTelegramService.findByChatId(chatId)).thenReturn(Optional.of(createUserTelegram()));
        //when-then
        Assertions.assertFalse(handleUserTelegramService.getActiveStatusUser(chatId));
    }

    @Test
    void shouldGetAdminStatusUser(){
        //given
        Mockito.when(userTelegramService.findByChatId(chatId)).thenReturn(Optional.of(createUserTelegram()));
        //when-then
        Assertions.assertFalse(handleUserTelegramService.getAdminStatusUser(chatId));
    }

    @Test
    void shouldNotFindUser(){
        //given
        Mockito.when(userTelegramService.findByChatId(chatId)).thenReturn(Optional.empty());
        //when
        handleUserTelegramService.setUserActiveStatus(chatId,true,chatId);
        handleUserTelegramService.setUserAdminStatus(chatId,true,chatId);
        //then
        Mockito.verify(sendMessageService,Mockito.times(2)).sendMessage(eq(chatId.toString()),Mockito.any());
    }

    @Test
    void shouldProperlyCheckUser(){
        //given
        Mockito.when(userTelegramService.findByChatId(chatId)).thenReturn(Optional.of(new UserTelegram()));
        //when-then
        Assertions.assertTrue(handleUserTelegramService.checkUserIsPresent(chatId));
    }

    @Test
    void shouldCreateUser(){
        //when
        Update update = new Update();
        handleUserTelegramService.createUser(update);
        //then
        Mockito.verify(userTelegramService).findOrCreateUser(update);
    }
}
