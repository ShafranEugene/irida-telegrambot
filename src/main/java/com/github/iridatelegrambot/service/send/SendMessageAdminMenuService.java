package com.github.iridatelegrambot.service.send;

public interface SendMessageAdminMenuService extends SendMessageService{

    void sendAdminMenu(Long chatId, String message,Integer idMessage);

    void sendAdminSetStatus(Long chatId, boolean status, String message, Integer messageId);

    void sendUsersForAdmin(Long chatId, String message, Integer messageId);
}
