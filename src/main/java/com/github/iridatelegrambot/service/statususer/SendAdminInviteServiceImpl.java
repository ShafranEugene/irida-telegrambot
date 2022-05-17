package com.github.iridatelegrambot.service.statususer;

import com.github.iridatelegrambot.entity.UserTelegram;
import com.github.iridatelegrambot.service.AnswerCatcherServiceImpl;
import com.github.iridatelegrambot.service.HandleUserTelegramService;
import com.github.iridatelegrambot.service.UserTelegramService;
import com.github.iridatelegrambot.service.senders.SendInviteForAdminService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class SendAdminInviteServiceImpl implements SendAdminInviteService{
    private final SendInviteForAdminService sendMessageService;
    private final MuteInviteService muteInviteService;
    private final UserTelegramService userTelegramService;
    private final HandleUserTelegramService handleUserTelegramService;
    private final Logger logger = LoggerFactory.getLogger(SendAdminInviteServiceImpl.class);

    @Autowired
    public SendAdminInviteServiceImpl(SendInviteForAdminService sendMessageService, HandleUserTelegramService handleUserTelegramService,
                                      UserTelegramService userTelegramService, MuteInviteService muteInviteService) {
        this.sendMessageService = sendMessageService;
        this.muteInviteService = muteInviteService;
        this.userTelegramService = userTelegramService;
        this.handleUserTelegramService = handleUserTelegramService;
    }

    @Override
    public void send(Long chatId){
        String infoUser = handleUserTelegramService.toStringInfoForUser(chatId);
        if(muteInviteService.checkStatus(chatId)) {
            String message = "Получена заявка на доступ к боту от пользователя:\n" +
                    infoUser;
            for (UserTelegram admin : userTelegramService.findAllAdmin()) {
                sendMessageService.sendInviteToAdmin(admin.getChatId(), chatId, message);
                logger.info("Admin - " + admin.getUserName() + ", received a request to add a new user " + infoUser);
            }
            muteInviteService.setMute(chatId);
        }
    }
}
