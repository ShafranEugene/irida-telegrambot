package com.github.iridatelegrambot.service.send;

import com.github.iridatelegrambot.entity.Order;

public interface SendMessageOrderMenuService extends SendMessageService {

    void sendActiveOrders(Long chatId, String message);

    void sendMenuOrder(Long chatId, Order order);
}
