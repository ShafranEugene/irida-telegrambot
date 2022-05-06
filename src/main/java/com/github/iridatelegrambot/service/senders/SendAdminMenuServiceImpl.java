package com.github.iridatelegrambot.service.senders;

import com.github.iridatelegrambot.service.buttons.InlineKeyboardService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.InlineKeyboardMarkup;

import java.util.Optional;

@Service
public class SendAdminMenuServiceImpl implements SendAdminMenuService{
    private final SendMessageService sendMessageService;
    private final InlineKeyboardService inlineKeyboardService;

    @Autowired
    public SendAdminMenuServiceImpl(SendMessageService sendMessageService,InlineKeyboardService inlineKeyboardService) {
        this.sendMessageService = sendMessageService;
        this.inlineKeyboardService = inlineKeyboardService;
    }

    @Override
    public void sendAdminMenu(Long chatId, String message, Integer idMessage){
        sendMessageService.deleteMessage(chatId,idMessage);
        sendMessageService.sendMessage(chatId.toString(),message,inlineKeyboardService.showMenuAdmin());
    }

    @Override
    public void sendAdminSetStatus(Long chatId, boolean status, String message, Integer messageId){
        Optional<InlineKeyboardMarkup> markup = inlineKeyboardService.showAllUsersForSetStatus(status);
        if(markup.isPresent()) {
            sendMessageService.deleteMessage(chatId, messageId);
            sendMessageService.sendMessage(chatId.toString(), message, markup.get());
        } else {
            sendMessageService.deleteMessage(chatId, messageId);
            sendMessageService.sendMessage(chatId.toString(), "Подходящих пользователей не найдено.");
        }
    }

    @Override
    public void sendUsersForAdmin(Long chatId, String message, Integer messageId){
        sendMessageService.deleteMessage(chatId,messageId);
        sendMessageService.sendMessage(chatId.toString(),message,inlineKeyboardService.showAllUsersForSetAdmin());
    }
}
