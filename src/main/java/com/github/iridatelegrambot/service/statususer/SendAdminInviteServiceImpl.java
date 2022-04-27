package com.github.iridatelegrambot.service.statususer;

import com.github.iridatelegrambot.entity.UserTelegram;
import com.github.iridatelegrambot.service.send.SendMessageInviteForAdminService;
import com.github.iridatelegrambot.service.send.SendMessageService;
import com.github.iridatelegrambot.service.UserTelegramService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class SendAdminInviteServiceImpl implements SendAdminInviteService{
    private final SendMessageInviteForAdminService sendMessageService;
    private final List<UserTelegram> adminList;

    @Autowired
    public SendAdminInviteServiceImpl(SendMessageInviteForAdminService sendMessageService, UserTelegramService userTelegramService) {
        this.sendMessageService = sendMessageService;
        adminList = userTelegramService.findAllAdmin();
    }

    @Override
    public void send(UserTelegram user){
        String message = "Получена заявка на доступ к боту от пользователя:\n" + user.toStringInfoForUser();
        for (UserTelegram admin : adminList){
            sendMessageService.sendInviteToAdmin(admin.getChatId(),user.getChatId(),message);
        }
    }
}
