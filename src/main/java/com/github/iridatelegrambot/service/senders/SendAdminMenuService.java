package com.github.iridatelegrambot.service.senders;

public interface SendAdminMenuService{

    void sendAdminMenu(Long chatId, String message,Integer idMessage);

    void sendAdminSetStatus(Long chatId, boolean status, String message, Integer messageId);

    void sendUsersForAdmin(Long chatId, String message, Integer messageId);
}
