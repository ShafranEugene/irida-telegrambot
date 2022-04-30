package com.github.iridatelegrambot.service.send;

import com.github.iridatelegrambot.entity.Invoice;
import com.github.iridatelegrambot.entity.Order;

public interface SendMessageWithOrderService extends SendMessageService{

    void sendActiveOrdersForInvoice(Long chatId, String message,Integer messageId, Invoice invoice);

    void sendMessageCloseOrder(Long chatId, Integer messageId, String message, Order order);
}
