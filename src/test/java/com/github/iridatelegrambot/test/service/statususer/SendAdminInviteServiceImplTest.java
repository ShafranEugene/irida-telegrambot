package com.github.iridatelegrambot.test.service.statususer;

import com.github.iridatelegrambot.entity.UserTelegram;
import com.github.iridatelegrambot.service.UserTelegramService;
import com.github.iridatelegrambot.service.UserTelegramServiceImpl;
import com.github.iridatelegrambot.service.send.SendMessageInviteForAdminService;
import com.github.iridatelegrambot.service.send.SendMessageServiceImpl;
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
    private SendMessageInviteForAdminService sendMessageService;

    @BeforeEach
    void init(){
        userTelegramService = Mockito.mock(UserTelegramServiceImpl.class);
        muteInviteService = Mockito.mock(MuteInviteServiceImpl.class);
        sendMessageService = Mockito.mock(SendMessageServiceImpl.class);
        sendAdminInviteService = new SendAdminInviteServiceImpl(sendMessageService,userTelegramService,muteInviteService);
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
        Mockito.when(muteInviteService.checkStatus(newUser.getChatId())).thenReturn(true);
        String message = "Получена заявка на доступ к боту от пользователя:\n" + newUser.toStringInfoForUser();
        //when
        sendAdminInviteService.send(newUser);
        //then
        Mockito.verify(sendMessageService).sendInviteToAdmin(admin.getChatId(),newUser.getChatId(),message);
    }
}
