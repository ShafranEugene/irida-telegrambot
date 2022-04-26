package com.github.iridatelegrambot.command.CallbackCommand;

import com.github.iridatelegrambot.service.SendMessageService;
import com.github.iridatelegrambot.service.UserTelegramService;
import org.telegram.telegrambots.meta.api.objects.CallbackQuery;

import java.util.ArrayList;
import java.util.List;

public class AdminMenuCallbackCommand implements CallbackCommand {
    private final SendMessageService sendMessageService;
    private final UserTelegramService userTelegramService;
    private final List<String> adminCommand = new ArrayList<>();
    private Long chatId;

    public AdminMenuCallbackCommand(SendMessageService sendMessageService, UserTelegramService userTelegramService) {
        this.sendMessageService = sendMessageService;
        this.userTelegramService = userTelegramService;
        adminCommand.add("mainAdminMenu");
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
        }
    }

    private void sendListUsersForCloseStatus(){
        sendMessageService.sendAdminSetStatus(chatId,false,"Выберете пользователя которому хотите закрыть доступ.");
    }

    private void sendListUsersForOpenStatus(){
        sendMessageService.sendAdminSetStatus(chatId,true,"Выберете пользователя которому хотите открыть доступ.");
    }

    private void sendListUsersForSetAdmin(){
        // TODO: 26.04.2022  
    }
}
