package com.github.iridatelegrambot.test.service.sendlers;

import com.github.iridatelegrambot.service.buttons.InlineAdminButtonService;
import com.github.iridatelegrambot.service.senders.SendInviteForAdminService;
import com.github.iridatelegrambot.service.senders.SendInviteForAdminServiceImpl;
import com.github.iridatelegrambot.service.senders.SendMessageService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.InlineKeyboardMarkup;

public class SendInviteForAdminServiceImplTest {
    private SendMessageService sendMessageService;
    private InlineAdminButtonService inlineAdminButtonService;
    private SendInviteForAdminService inviteForAdminService;

    @BeforeEach
    void init(){
        sendMessageService = Mockito.mock(SendMessageService.class);
        inlineAdminButtonService = Mockito.mock(InlineAdminButtonService.class);
        inviteForAdminService = new SendInviteForAdminServiceImpl(sendMessageService,inlineAdminButtonService);
    }

    @Test
    void shouldSendInvite(){
        //given
        Long chatId = 12345678L;
        Long chatIdUser = 12345679L;
        String message = "test";
        InlineKeyboardMarkup markup = new InlineKeyboardMarkup();
        Mockito.when(inlineAdminButtonService.inviteForAdmin(chatIdUser)).thenReturn(markup);
        //when
        inviteForAdminService.sendInviteToAdmin(chatId,chatIdUser,message);
        //then
        Mockito.verify(sendMessageService).sendMessage(chatId.toString(),message,markup);
    }

}
