package com.github.iridatelegrambot.service.senders;

import com.github.iridatelegrambot.service.buttons.InlineKeyboardService;
import org.springframework.stereotype.Service;

@Service
public class SendInviteForAdminServiceImpl implements SendInviteForAdminService{

    private final SendMessageService sendMessageService;
    private final InlineKeyboardService inlineKeyboardService;

    public SendInviteForAdminServiceImpl(SendMessageService sendMessageService, InlineKeyboardService service) {
        this.sendMessageService = sendMessageService;
        this.inlineKeyboardService = service;
    }

    @Override
    public void sendInviteToAdmin(Long chatIdAdmin,Long chatIdUser, String message){
        sendMessageService.sendMessage(chatIdAdmin.toString(),message, inlineKeyboardService.inviteForAdmin(chatIdUser));
    }
}
