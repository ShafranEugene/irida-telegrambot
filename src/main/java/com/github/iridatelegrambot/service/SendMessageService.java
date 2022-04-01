package com.github.iridatelegrambot.service;

import org.telegram.telegrambots.meta.api.objects.replykeyboard.InlineKeyboardMarkup;

public interface SendMessageService {

    void sendMessage(String chatId, String message);

    void sendMessage(String chatId, String message, InlineKeyboardMarkup inlineKeyboardMarkup);
}
