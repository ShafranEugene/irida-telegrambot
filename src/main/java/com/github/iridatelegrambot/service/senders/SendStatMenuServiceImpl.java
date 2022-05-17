package com.github.iridatelegrambot.service.senders;

import com.github.iridatelegrambot.service.buttons.InlineAdminButtonService;
import com.github.iridatelegrambot.service.buttons.InlineMenuButtonService;
import com.github.iridatelegrambot.service.statuswait.WaitDocument;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.InlineKeyboardMarkup;

import java.util.Optional;

@Service
public class SendStatMenuServiceImpl implements SendStatMenuService {

    private final SendMessageService sendMessageService;
    private final InlineMenuButtonService inlineKeyboardService;
    private final InlineAdminButtonService inlineAdminButtonService;

    @Autowired
    public SendStatMenuServiceImpl(SendMessageService sendMessageService, InlineMenuButtonService inlineKeyboardService,
                                   InlineAdminButtonService inlineAdminButtonService) {
        this.sendMessageService = sendMessageService;
        this.inlineKeyboardService = inlineKeyboardService;
        this.inlineAdminButtonService = inlineAdminButtonService;
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

    @Override
    public void sendAdminMenu(Long chatId, String message, Integer idMessage){
        sendMessageService.deleteMessage(chatId,idMessage);
        sendMessageService.sendMessage(chatId.toString(),message,inlineKeyboardService.showMenuAdmin());
    }

    @Override
    public void sendAdminSetStatus(Long chatId, boolean status, String message, Integer messageId){
        Optional<InlineKeyboardMarkup> markup = inlineAdminButtonService.showAllUsersForSetStatus(status);
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
        sendMessageService.sendMessage(chatId.toString(),message,inlineAdminButtonService.showAllUsersForSetAdmin());
    }
}
