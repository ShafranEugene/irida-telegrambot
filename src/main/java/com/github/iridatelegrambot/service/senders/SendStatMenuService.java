package com.github.iridatelegrambot.service.senders;

import com.github.iridatelegrambot.service.statuswait.WaitDocument;

public interface SendStatMenuService{

    void sendMenuStat(Long chatId, String message);

    void sendMenuStatDetails(Long chatId, String message, Integer messageId, WaitDocument waitDocument);
}
