package com.github.iridatelegrambot.test.service.sendlers;

import com.github.iridatelegrambot.service.buttons.InlineAdminButtonService;
import com.github.iridatelegrambot.service.buttons.InlineMenuButtonService;
import com.github.iridatelegrambot.service.senders.SendMessageService;
import com.github.iridatelegrambot.service.senders.SendStatMenuServiceImpl;
import com.github.iridatelegrambot.service.statuswait.WaitDocument;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.InlineKeyboardMarkup;

import java.util.Optional;

public class SendStatMenuServiceImplTest {
    private SendMessageService sendMessageService;
    private InlineMenuButtonService inlineKeyboardService;
    private InlineAdminButtonService inlineAdminButtonService;
    private SendStatMenuServiceImpl sendStatMenuService;
    private InlineKeyboardMarkup markup;
    private Long chatId;
    private String message;
    private Integer idMessage;

    @BeforeEach
    void init(){
        sendMessageService = Mockito.mock(SendMessageService.class);
        inlineKeyboardService = Mockito.mock(InlineMenuButtonService.class);
        inlineAdminButtonService = Mockito.mock(InlineAdminButtonService.class);
        sendStatMenuService = new SendStatMenuServiceImpl(sendMessageService,inlineKeyboardService,inlineAdminButtonService);
        markup = new InlineKeyboardMarkup();
        chatId = 12345678L;
        message = "Test";
        idMessage = 10;
    }

    @Test
    void shouldSendStatMenu(){
        //given
        Mockito.when(inlineKeyboardService.showMenuStat()).thenReturn(markup);
        //when
        sendStatMenuService.sendMenuStat(chatId,message);
        //then
        Mockito.verify(sendMessageService).sendMessage(chatId.toString(),message,markup);
    }

    @Test
    void shouldSendStatMenuDetails(){
        //given
        Mockito.when(inlineKeyboardService.showMenuStatDetails(WaitDocument.ORDER)).thenReturn(markup);
        //when
        sendStatMenuService.sendMenuStatDetails(chatId,message,idMessage,WaitDocument.ORDER);
        //then
        Mockito.verify(sendMessageService).sendMessage(chatId.toString(),message,markup);
    }

    @Test
    void shouldNotFindUsersForSetStatus(){
        //given
        Mockito.when(inlineAdminButtonService.showAllUsersForSetStatus(true)).thenReturn(Optional.empty());
        //when
        sendStatMenuService.sendAdminSetStatus(chatId,true,message,idMessage);
        //then
        Mockito.verify(sendMessageService).sendMessage(chatId.toString(),"Подходящих пользователей не найдено.");
    }

    @Test
    void shouldSendUsersForSetStatus(){
        //given
        Mockito.when(inlineAdminButtonService.showAllUsersForSetStatus(true)).thenReturn(Optional.of(markup));
        //when
        sendStatMenuService.sendAdminSetStatus(chatId,true,message,idMessage);
        //then
        Mockito.verify(sendMessageService).sendMessage(chatId.toString(),message,markup);
    }

    @Test
    void shouldSendUsersForSetAdmin(){
        //given
        Mockito.when(inlineAdminButtonService.showAllUsersForSetAdmin()).thenReturn(markup);
        //when
        sendStatMenuService.sendUsersForAdmin(chatId,message,idMessage);
        //then
        Mockito.verify(sendMessageService).sendMessage(chatId.toString(),message,markup);
    }



}
