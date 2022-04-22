package com.github.iridatelegrambot.test.service;

import com.github.iridatelegrambot.bot.IridaBot;
import com.github.iridatelegrambot.service.SendMessageService;
import com.github.iridatelegrambot.service.SendMessageServiceImpl;
import com.github.iridatelegrambot.service.buttons.InlineKeyboardService;
import com.github.iridatelegrambot.service.buttons.MenuButtonsService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

class SendMessageServiceImplTest {
    private SendMessageService sendMessageService;
    private InlineKeyboardService mInline;
    private IridaBot mIridaBot;
    private MenuButtonsService mMenuButtonsService;

    @BeforeEach
    void init(){
        mIridaBot = Mockito.mock(IridaBot.class);
        mInline = Mockito.mock(InlineKeyboardService.class);
        mMenuButtonsService = Mockito.mock(MenuButtonsService.class);
        sendMessageService = new SendMessageServiceImpl(mIridaBot,mInline,mMenuButtonsService);
    }

    @Test
    void shouldSendMessage() throws TelegramApiException {
        //given
        String chatId = "test_id";
        String message = "test_message";
        SendMessage sendMessage = new SendMessage();
        sendMessage.setChatId(chatId);
        sendMessage.setText(message);
        sendMessage.enableHtml(true);

        //when
        sendMessageService.sendMessage(chatId,message);

        //then
        Mockito.verify(mIridaBot).execute(sendMessage);
    }

}