package com.github.iridatelegrambot.service.senders;

import com.github.iridatelegrambot.entity.Order;

public interface SendOrderMenuService {

    void sendActiveOrders(Long chatId, String message);

    void sendMenuOrder(Long chatId, Order order);
}
