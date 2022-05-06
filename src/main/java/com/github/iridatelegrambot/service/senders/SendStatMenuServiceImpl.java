package com.github.iridatelegrambot.service.senders;

import com.github.iridatelegrambot.service.buttons.InlineKeyboardService;
import com.github.iridatelegrambot.service.statuswait.WaitDocument;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.InlineKeyboardMarkup;

@Service
public class SendStatMenuServiceImpl implements SendStatMenuService {

    private final SendMessageService sendMessageService;
    private final InlineKeyboardService inlineKeyboardService;

    @Autowired
    public SendStatMenuServiceImpl(SendMessageService sendMessageService, InlineKeyboardService inlineKeyboardService) {
        this.sendMessageService = sendMessageService;
        this.inlineKeyboardService = inlineKeyboardService;
    }

    @Override
    public void sendMenuStat(Long chatId, String message){
        InlineKeyboardMarkup markup = inlineKeyboardService.showMenuStat();
        sendMessageService.sendMessage(chatId.toString(),message,markup);
    }

    @Override
    public void sendMenuStatDetails(Long chatId, String message,Integer messageId, WaitDocument waitDocument){
        sendMessageService.deleteMessage(chatId,messageId);
        sendMessageService.sendMessage(chatId.toString(),message,inlineKeyboardService.showMenuStatDetails(waitDocument));
    }
}
