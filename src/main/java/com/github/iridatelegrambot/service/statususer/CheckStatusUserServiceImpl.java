package com.github.iridatelegrambot.service.statususer;

import com.github.iridatelegrambot.entity.UserTelegram;
import com.github.iridatelegrambot.service.send.SendMessageService;
import com.github.iridatelegrambot.service.UserTelegramService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.telegram.telegrambots.meta.api.objects.Update;

import java.util.Optional;

//The bot has simple protection, users must have an active status to work with the bot.
//This status makes it active and gives access to the bot, the administrator.

@Service
public class CheckStatusUserServiceImpl implements CheckStatusUserService {

    private final SendMessageService sendMessageService;
    private final UserTelegramService userTelegramService;
    private final SendAdminInviteService sendAdminInviteService;

    @Autowired
    public CheckStatusUserServiceImpl(SendMessageService sendMessageService, UserTelegramService userTelegramService,
                                      SendAdminInviteService sendAdminInviteService) {
        this.sendMessageService = sendMessageService;
        this.userTelegramService = userTelegramService;
        this.sendAdminInviteService = sendAdminInviteService;
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

        Optional<UserTelegram> optionalUserTelegram = userTelegramService.findByChatId(chatId);
        if(optionalUserTelegram.isEmpty()){
            UserTelegram userTelegram = userTelegramService.findOrCreateUser(update);
            if(userTelegramService.getAllActiveUser().size() == 0){
                userTelegram.setAdmin(true);
            }
            sendFalseAnswer(userTelegram);
            return true;
        }

        UserTelegram userTelegram = optionalUserTelegram.get();
        if(userTelegram.isActive()){
            return true;
        } else if(userTelegram.isAdmin()) {
            userTelegram.setActive(true);
            userTelegramService.save(userTelegram);
            return true;
        } else {
            sendFalseAnswer(userTelegram);
            return false;
        }
    }

    private void sendFalseAnswer(UserTelegram userTelegram){
        sendAdminInviteService.send(userTelegram);
        sendMessageService.sendMessage(userTelegram.getChatId().toString(),
                "Ваша учетная запись не имеет доступа к боту." +
                        "\nЗаявка на согласование была отправлена администратору." +
                        "\nПосле одобрения, бот уведомит Вас.");
    }
}
