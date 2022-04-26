package com.github.iridatelegrambot.service;

import com.github.iridatelegrambot.entity.Invoice;
import com.github.iridatelegrambot.entity.Order;
import com.github.iridatelegrambot.service.statuswait.WaitDocument;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.InlineKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.ReplyKeyboard;

import java.util.Optional;

public interface SendMessageService {

    void sendMessage(String chatId, String message);

    void sendMessage(String chatId, String message, ReplyKeyboard replyKeyboard);

    void sendListCityForOrder(Optional<Order> orderOptional, Long chatId);

    void sendListCityForInvoice(Optional<Invoice> invoiceOptional, Long chatId);

    void sendMainMenu(Long chatId, String messageText);

    void sendActiveOrdersForInvoice(Long chatId, String message, Invoice invoice);

    void sendMessageCloseOrder(Long chatId, String message, Order order);

    void sendActiveOrders(Long chatId, String message);

    void sendMenuOrder(Long chatId, Order order);

    void sendMenuStat(Long chatId, String message);

    void sendMenuStatDetails(Long chatId, String message, WaitDocument waitDocument);

    void sendInviteToAdmin(Long chatIdAdmin, Long chatIdUser, String message);

    void sendAdminMenu(Long chatId, String message);

    void sendAdminSetStatus(Long chatId, boolean status, String message);
}
