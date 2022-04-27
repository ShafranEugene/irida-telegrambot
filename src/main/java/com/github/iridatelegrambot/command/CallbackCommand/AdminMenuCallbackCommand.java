package com.github.iridatelegrambot.command.CallbackCommand;

import com.github.iridatelegrambot.entity.UserTelegram;
import com.github.iridatelegrambot.service.send.SendMessageAdminMenuService;
import com.github.iridatelegrambot.service.UserTelegramService;
import org.telegram.telegrambots.meta.api.objects.CallbackQuery;

import java.util.Optional;

public class AdminMenuCallbackCommand implements CallbackCommand {
    private final SendMessageAdminMenuService sendMessageService;
    private final UserTelegramService userTelegramService;
    private Long chatId;

    public AdminMenuCallbackCommand(SendMessageAdminMenuService sendMessageService, UserTelegramService userTelegramService) {
        this.sendMessageService = sendMessageService;
        this.userTelegramService = userTelegramService;
    }

    @Override
    public void execute(CallbackQuery callbackQuery) {
        chatId = callbackQuery.getMessage().getChatId();
        String[] data = callbackQuery.getData().split(":");
        String command = data[1];

        if(command.equals("closeStatusUser")){
            sendListUsersForCloseStatus();
        } else if(command.equals("openStatusUser")){
            sendListUsersForOpenStatus();
        } else if(command.equals("setAdmin")){
            sendListUsersForSetAdmin();
        } else if(command.equals("pullOffAdmin")){
            pullOffAdmin();
        }
    }

    private void sendListUsersForCloseStatus(){
        sendMessageService.sendAdminSetStatus(chatId,false,"Выберете пользователя которому хотите закрыть доступ.");
    }

    private void sendListUsersForOpenStatus(){
        sendMessageService.sendAdminSetStatus(chatId,true,"Выберете пользователя которому хотите открыть доступ.");
    }

    private void sendListUsersForSetAdmin(){
        sendMessageService.sendUsersForAdmin(chatId,"Выберете пользователю которому хотите выдать права администратора");
    }

    private void pullOffAdmin(){
        Optional<UserTelegram> userTelegramOptional = userTelegramService.findByChatId(chatId);
        if(userTelegramOptional.isPresent()){
            UserTelegram userTelegram = userTelegramOptional.get();
            userTelegram.setAdmin(false);
            userTelegramService.save(userTelegram);
            sendMessageService.sendMessage(chatId.toString(),"Вы сняли с себя права администратора!");
        } else {
            sendMessageService.sendMessage(chatId.toString(),"Пользователь не найден.");
        }
    }
}