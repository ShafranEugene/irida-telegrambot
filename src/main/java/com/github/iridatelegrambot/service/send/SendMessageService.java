package com.github.iridatelegrambot.service.send;

import org.telegram.telegrambots.meta.api.objects.replykeyboard.InlineKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.ReplyKeyboard;


public interface SendMessageService {

    void sendMessage(String chatId, String message);

    void sendMessage(String chatId, String message, ReplyKeyboard replyKeyboard);

    void editMessage(Long chatId,Integer messageId, String message);

    void editButtons(Long chatId, Integer messageId, InlineKeyboardMarkup markup);

    void deleteMessage(Long chatId,Integer messageId);
}
