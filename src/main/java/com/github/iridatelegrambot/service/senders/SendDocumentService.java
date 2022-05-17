package com.github.iridatelegrambot.service.senders;

import com.github.iridatelegrambot.entity.Invoice;
import com.github.iridatelegrambot.entity.Order;

import java.util.Optional;

public interface SendDocumentService{

    void sendListCityForOrder(Optional<Order> orderOptional, Long chatId);

    void sendListCityForInvoice(Optional<Invoice> invoiceOptional, Long chatId);

    void sendActiveOrdersForInvoice(Long chatId, String message,Integer messageId, Invoice invoice);

    void sendMessageCloseOrder(Long chatId, Integer messageId, String message, Order order);
}
