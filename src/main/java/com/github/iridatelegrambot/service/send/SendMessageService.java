package com.github.iridatelegrambot.service.send;

import org.telegram.telegrambots.meta.api.objects.replykeyboard.ReplyKeyboard;


public interface SendMessageService {

    void sendMessage(String chatId, String message);

    void sendMessage(String chatId, String message, ReplyKeyboard replyKeyboard);
}
