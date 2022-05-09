package com.github.iridatelegrambot.service.statususer;

import com.github.iridatelegrambot.service.HandleUserTelegramService;
import com.github.iridatelegrambot.service.senders.SendMessageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.telegram.telegrambots.meta.api.objects.Update;

//The bot has simple protection, users must have an active status to work with the bot.
//This status makes it active and gives access to the bot, the administrator.

@Service
public class CheckStatusUserServiceImpl implements CheckStatusUserService {

    private final SendMessageService sendMessageService;
    private final SendAdminInviteService sendAdminInviteService;
    private final HandleUserTelegramService handleUserTelegramService;

    @Autowired
    public CheckStatusUserServiceImpl(SendMessageService sendMessageService,
                                      SendAdminInviteService sendAdminInviteService, HandleUserTelegramService handleUserTelegramService) {
        this.sendMessageService = sendMessageService;
        this.sendAdminInviteService = sendAdminInviteService;
        this.handleUserTelegramService = handleUserTelegramService;
    }

    @Override
    public boolean check(Update update){
        Long chatId;
        if(update.hasCallbackQuery()){
            chatId = update.getCallbackQuery().getMessage().getChatId();
        } else if(update.hasEditedMessage()) {
            chatId = update.getEditedMessage().getChat().getId();
        } else {
            chatId = update.getMessage().getChatId();
        }

        if(handleUserTelegramService.checkUserOnEmpty(chatId)){
            handleUserTelegramService.createUser(update);
            if(handleUserTelegramService.checkOnFirstUser(chatId)){
                handleUserTelegramService.setUserActiveStatus(chatId,true);
                return true;
            } else {
                sendFalseAnswer(chatId);
                return false;
            }
        }

        if(handleUserTelegramService.getActiveStatusUser(chatId)){
            return true;
        } else if(handleUserTelegramService.getAdminStatusUser(chatId)) {
            handleUserTelegramService.setUserActiveStatus(chatId,true);
            return true;
        } else {
            sendFalseAnswer(chatId);
            return false;
        }
    }

    private void sendFalseAnswer(Long chatId){
        sendAdminInviteService.send(chatId);
        sendMessageService.sendMessage(chatId.toString(),
                "Ваша учетная запись не имеет доступа к боту." +
                        "\nЗаявка на согласование была отправлена администратору." +
                        "\nПосле одобрения, бот уведомит Вас.");
    }
}
