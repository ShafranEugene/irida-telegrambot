package com.github.iridatelegrambot.service.senders;

import com.github.iridatelegrambot.service.buttons.InlineAdminButtonService;
import org.springframework.stereotype.Service;

@Service
public class SendInviteForAdminServiceImpl implements SendInviteForAdminService{

    private final SendMessageService sendMessageService;
    private final InlineAdminButtonService inlineKeyboardService;

    public SendInviteForAdminServiceImpl(SendMessageService sendMessageService, InlineAdminButtonService service) {
        this.sendMessageService = sendMessageService;
        this.inlineKeyboardService = service;
    }

    @Override
    public void sendInviteToAdmin(Long chatIdAdmin,Long chatIdUser, String message){
        sendMessageService.sendMessage(chatIdAdmin.toString(),message, inlineKeyboardService.inviteForAdmin(chatIdUser));
    }
}
