package com.github.iridatelegrambot.command.CallbackCommand;

import com.github.iridatelegrambot.entity.UserTelegram;
import com.github.iridatelegrambot.service.SendMessageService;
import com.github.iridatelegrambot.service.UserTelegramService;
import org.telegram.telegrambots.meta.api.objects.CallbackQuery;

import java.util.Optional;

public class AdminSetStatusCallbackCommand implements CallbackCommand {
    private final SendMessageService sendMessageService;
    private final UserTelegramService userTelegramService;

    public AdminSetStatusCallbackCommand(SendMessageService sendMessageService, UserTelegramService userTelegramService) {
        this.sendMessageService = sendMessageService;
        this.userTelegramService = userTelegramService;
    }

    @Override
    public void execute(CallbackQuery callbackQuery) {
        Long chatId = callbackQuery.getMessage().getChatId();
        String[] data = callbackQuery.getData().split(":");

        if(data[1].equals("setActive")){
            setStatusUser(chatId,Long.valueOf(data[2]),Boolean.parseBoolean(data[3]));
        }
    }

    private void setStatusUser(Long chatId,Long chatIdUser, boolean status){
        Optional<UserTelegram> userOptional = userTelegramService.findByChatId(chatIdUser);
        if(userOptional.isPresent()){
            UserTelegram userTelegram = userOptional.get();
            userTelegram.setActive(status);
            userTelegramService.save(userTelegram);
            sendMessageService.sendMessage(chatId.toString(),"Готво");
        } else {
            sendMessageService.sendMessage(chatId.toString(),"Не смог найти такого пользователя.");
        }
    }
}
