package com.github.iridatelegrambot.service.senders;

import com.github.iridatelegrambot.bot.IridaBot;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.methods.updatingmessages.DeleteMessage;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.ReplyKeyboard;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;


import static org.apache.commons.lang3.StringUtils.isBlank;

@Service
public class SendMessageServiceImpl implements SendMessageService{

    private final IridaBot iridaBot;

    @Autowired
    public SendMessageServiceImpl(@Lazy IridaBot iridaBot) {
        this.iridaBot = iridaBot;
    }



    @Override
    public void sendMessage(String chatId, String message) {
        if(isBlank(message)) return;

        SendMessage sendMessage = new SendMessage();
        sendMessage.setText(message);
        sendMessage.setChatId(chatId);
        sendMessage.enableHtml(true);


        try {
            iridaBot.execute(sendMessage);
        } catch (TelegramApiException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void sendMessage(String chatId, String message, ReplyKeyboard replyKeyboard) {
        if(isBlank(message)) return;

        SendMessage sendMessage = new SendMessage();
        sendMessage.setText(message);
        sendMessage.setChatId(chatId);
        sendMessage.setReplyMarkup(replyKeyboard);
        sendMessage.enableHtml(true);

        try {
            iridaBot.execute(sendMessage);
        } catch (TelegramApiException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void deleteMessage(Long chatId,Integer messageId){
        DeleteMessage deleteMessage = new DeleteMessage();
        deleteMessage.setChatId(chatId.toString());
        deleteMessage.setMessageId(messageId);
        try {
            iridaBot.execute(deleteMessage);
        } catch (TelegramApiException e) {
            e.printStackTrace();
        }
    }
}
