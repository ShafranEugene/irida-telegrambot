package com.github.iridatelegrambot.test.service.statususer;

import com.github.iridatelegrambot.entity.UserTelegram;
import com.github.iridatelegrambot.service.HandleUserTelegramService;
import com.github.iridatelegrambot.service.HandleUserTelegramServiceImpl;
import com.github.iridatelegrambot.service.UserTelegramService;
import com.github.iridatelegrambot.service.UserTelegramServiceImpl;
import com.github.iridatelegrambot.service.senders.SendInviteForAdminService;
import com.github.iridatelegrambot.service.senders.SendInviteForAdminServiceImpl;
import com.github.iridatelegrambot.service.statususer.MuteInviteService;
import com.github.iridatelegrambot.service.statususer.MuteInviteServiceImpl;
import com.github.iridatelegrambot.service.statususer.SendAdminInviteServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.util.*;

public class SendAdminInviteServiceImplTest {
    private SendAdminInviteServiceImpl sendAdminInviteService;
    private UserTelegramService userTelegramService;
    private MuteInviteService muteInviteService;
    private SendInviteForAdminService sendMessageService;
    private HandleUserTelegramService handleUserTelegramService;

    @BeforeEach
    void init(){
        userTelegramService = Mockito.mock(UserTelegramServiceImpl.class);
        muteInviteService = Mockito.mock(MuteInviteServiceImpl.class);
        sendMessageService = Mockito.mock(SendInviteForAdminServiceImpl.class);
        handleUserTelegramService = Mockito.mock(HandleUserTelegramServiceImpl.class);
        sendAdminInviteService = new SendAdminInviteServiceImpl(sendMessageService, handleUserTelegramService,userTelegramService,muteInviteService);
    }

    @Test
    void shouldProperlySendMessage(){
        //given
        UserTelegram newUser = new UserTelegram();
        newUser.setChatId(11111111L);
        UserTelegram admin = new UserTelegram();
        admin.setChatId(22222222L);
        admin.setAdmin(true);

        List<UserTelegram> adminList = new ArrayList<>();
        adminList.add(admin);

        Mockito.when(userTelegramService.findAllAdmin()).thenReturn(adminList);
        Mockito.when(userTelegramService.findByChatId(11111111L)).thenReturn(Optional.of(newUser));
        Mockito.when(muteInviteService.checkStatus(newUser.getChatId())).thenReturn(true);
        String message = "Получена заявка на доступ к боту от пользователя:\n" +
                handleUserTelegramService.toStringInfoForUser(11111111L);
        //when
        sendAdminInviteService.send(11111111L);
        //then
        Mockito.verify(sendMessageService).sendInviteToAdmin(admin.getChatId(),newUser.getChatId(),message);
    }
}
