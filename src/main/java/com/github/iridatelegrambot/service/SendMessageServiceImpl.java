package com.github.iridatelegrambot.service;

import com.github.iridatelegrambot.bot.IridaBot;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

import static org.apache.commons.lang3.StringUtils.isBlank;

@Service
public class SendMessageServiceImpl implements SendMessageService {

    private IridaBot iridaBot;

    @Autowired
    public SendMessageServiceImpl(IridaBot iridaBot) {
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
}
