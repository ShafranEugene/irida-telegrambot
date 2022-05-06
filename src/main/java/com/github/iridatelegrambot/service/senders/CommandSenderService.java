package com.github.iridatelegrambot.service.senders;

public interface CommandSenderService {
    void sendMainMenu(Long chatId, String message);

    void sendMessage(String chatId, String text);

    void sendStatMenu(Long chatId, String text);

    void sendActiveOrders(Long chatId, String message);
}
