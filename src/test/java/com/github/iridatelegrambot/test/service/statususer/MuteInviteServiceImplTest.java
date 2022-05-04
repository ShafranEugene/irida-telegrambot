package com.github.iridatelegrambot.test.service.statususer;

import com.github.iridatelegrambot.service.statususer.MuteInviteServiceImpl;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class MuteInviteServiceImplTest {
    private MuteInviteServiceImpl muteInviteService = new MuteInviteServiceImpl();

    @Test
    void shouldSetMute(){
        //given
        Long chatId = 12345678L;
        //when
        muteInviteService.setMute(chatId);
        //then
        Assertions.assertFalse(muteInviteService.checkStatus(chatId));
    }

    @Test
    void shouldGetTrue(){
        //given
        Long chatId = 12345678L;
        //when-then
        Assertions.assertTrue(muteInviteService.checkStatus(chatId));
    }
}
