package com.github.iridatelegrambot.service.senders;

import com.github.iridatelegrambot.entity.Invoice;
import com.github.iridatelegrambot.entity.Order;

public interface SendWithOrderService {

    void sendActiveOrdersForInvoice(Long chatId, String message,Integer messageId, Invoice invoice);

    void sendMessageCloseOrder(Long chatId, Integer messageId, String message, Order order);
}
