package com.github.iridatelegrambot.service.send;

import com.github.iridatelegrambot.service.statuswait.WaitDocument;

public interface SendMessageStatMenuService extends SendMessageService {

    void sendMenuStat(Long chatId, String message);

    void sendMenuStatDetails(Long chatId, String message, Integer messageId, WaitDocument waitDocument);
}
