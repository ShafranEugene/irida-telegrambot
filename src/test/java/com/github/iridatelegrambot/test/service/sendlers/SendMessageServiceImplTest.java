package com.github.iridatelegrambot.test.service.sendlers;

import com.github.iridatelegrambot.bot.IridaBot;
import com.github.iridatelegrambot.service.senders.SendMessageService;
import com.github.iridatelegrambot.service.senders.SendMessageServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.methods.updatingmessages.DeleteMessage;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.InlineKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.InlineKeyboardButton;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

import java.util.*;

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

    @Test
    void shouldSendMessageWithButtons() throws TelegramApiException {
        //given
        String chatId = "test_id";
        String message = "test_message";
        List<List<InlineKeyboardButton>> rows = new ArrayList<>();
        rows.add(new ArrayList<>());
        InlineKeyboardButton button = new InlineKeyboardButton();
        button.setText("test_button");
        button.setText("/test_button");
        rows.get(0).add(button);
        InlineKeyboardMarkup markup = new InlineKeyboardMarkup();
        markup.setKeyboard(rows);

        SendMessage sendMessage = new SendMessage();
        sendMessage.setChatId(chatId);
        sendMessage.setText(message);
        sendMessage.enableHtml(true);
        sendMessage.setReplyMarkup(markup);
        //when
        sendMessageService.sendMessage(chatId,message,markup);

        //then
        Mockito.verify(mIridaBot).execute(sendMessage);
    }

    @Test
    void shouldProperlyDeleteMessage() throws TelegramApiException {
        //given
        Long chatId = 12345678L;
        Integer idMessage = 20;

        DeleteMessage deleteMessage = new DeleteMessage();
        deleteMessage.setMessageId(idMessage);
        deleteMessage.setChatId(chatId.toString());

        //when
        sendMessageService.deleteMessage(chatId,idMessage);

        //then
        Mockito.verify(mIridaBot).execute(deleteMessage);
    }

}