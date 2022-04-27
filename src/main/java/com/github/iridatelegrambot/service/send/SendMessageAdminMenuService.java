package com.github.iridatelegrambot.service.send;

public interface SendMessageAdminMenuService extends SendMessageService{

    void sendAdminMenu(Long chatId, String message);

    void sendAdminSetStatus(Long chatId, boolean status, String message);

    void sendUsersForAdmin(Long chatId, String message);
}
