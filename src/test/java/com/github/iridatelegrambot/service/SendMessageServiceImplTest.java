package com.github.iridatelegrambot.service;

import com.github.iridatelegrambot.bot.IridaBot;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

import static org.junit.jupiter.api.Assertions.*;

class SendMessageServiceImplTest {
    private SendMessageService sendMessageService;
    private IridaBot mIridaBot;

    @BeforeEach
    void init(){
        mIridaBot = Mockito.mock(IridaBot.class);
        sendMessageService = new SendMessageServiceImpl(mIridaBot);
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