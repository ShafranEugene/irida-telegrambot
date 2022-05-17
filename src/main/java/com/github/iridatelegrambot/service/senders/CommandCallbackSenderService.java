package com.github.iridatelegrambot.service.senders;

import com.github.iridatelegrambot.entity.Invoice;
import com.github.iridatelegrambot.entity.Order;
import com.github.iridatelegrambot.service.statuswait.WaitDocument;

import java.util.Optional;

public interface CommandCallbackSenderService {
    void sendMessage(String chatId, String message);

    void sendActiveOrdersForInvoice(Long chatId, String message, Integer messageId, Invoice invoice);

    void deleteMessage(Long chatId, Integer messageId);

    void sendMainMenu(Long chatId, String message);

    void sendMessageCloseOrder(Long chatId, Integer messageId, String message, Order order);

    void sendAdminSetStatus(Long chatId, boolean status, String message, Integer messageId);

    void sendUsersForAdmin(Long chatId, String message, Integer messageId);

    void sendMenuOrder(Long chatId, Order order);

    void sendMenuStatDetails(Long chatId, String message, Integer messageId, WaitDocument waitDocument);

    void sendAdminMenu(Long chatId, String message, Integer messageId);

    void sendListCityForOrder(Optional<Order> orderOptional, Long chatId);

    void sendListCityForInvoice(Optional<Invoice> invoiceOptional, Long chatId);
}
