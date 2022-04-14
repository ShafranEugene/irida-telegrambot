package com.github.iridatelegrambot.service;

import com.github.iridatelegrambot.entity.Invoice;
import com.github.iridatelegrambot.entity.Order;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.InlineKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.ReplyKeyboard;

import java.util.Optional;

public interface SendMessageService {

    void sendMessage(String chatId, String message);

    void sendMessage(String chatId, String message, ReplyKeyboard replyKeyboard);

    void sendListCityForOrder(Optional<Order> orderOptional, Long chatId);

    void sendListCityForInvoice(Optional<Invoice> invoiceOptional, Long chatId);

    void sendMainMenu(Long chatId, String messageText);
}
