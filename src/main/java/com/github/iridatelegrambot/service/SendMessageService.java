package com.github.iridatelegrambot.service;

import com.github.iridatelegrambot.entity.Order;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.InlineKeyboardMarkup;

import java.util.Optional;

public interface SendMessageService {

    void sendMessage(String chatId, String message);

    void sendMessage(String chatId, String message, InlineKeyboardMarkup inlineKeyboardMarkup);

    void sendListCityForOrder(Optional<Order> orderOptional, Long chatId);
}
