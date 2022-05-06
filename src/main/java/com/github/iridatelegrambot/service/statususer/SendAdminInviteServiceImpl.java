package com.github.iridatelegrambot.service.statususer;

import com.github.iridatelegrambot.entity.UserTelegram;
import com.github.iridatelegrambot.service.UserTelegramService;
import com.github.iridatelegrambot.service.senders.SendInviteForAdminService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class SendAdminInviteServiceImpl implements SendAdminInviteService{
    private final SendInviteForAdminService sendMessageService;
    private final MuteInviteService muteInviteService;
    private final UserTelegramService userTelegramService;

    @Autowired
    public SendAdminInviteServiceImpl(SendInviteForAdminService sendMessageService,
                                      UserTelegramService userTelegramService, MuteInviteService muteInviteService) {
        this.sendMessageService = sendMessageService;
        this.muteInviteService = muteInviteService;
        this.userTelegramService = userTelegramService;
    }

    @Override
    public void send(UserTelegram user){
        Long chatId = user.getChatId();
        if(muteInviteService.checkStatus(chatId)) {
            String message = "Получена заявка на доступ к боту от пользователя:\n" + user.toStringInfoForUser();
            for (UserTelegram admin : userTelegramService.findAllAdmin()) {
                sendMessageService.sendInviteToAdmin(admin.getChatId(), user.getChatId(), message);
            }
            muteInviteService.setMute(chatId);
        }
    }
}
