package com.github.iridatelegrambot.service.senders;

import com.github.iridatelegrambot.service.statuswait.WaitDocument;

public interface SendStatMenuService{

    void sendMenuStat(Long chatId, String message);

    void sendMenuStatDetails(Long chatId, String message, Integer messageId, WaitDocument waitDocument);

    void sendAdminMenu(Long chatId, String message,Integer idMessage);

    void sendAdminSetStatus(Long chatId, boolean status, String message, Integer messageId);

    void sendUsersForAdmin(Long chatId, String message, Integer messageId);
}
