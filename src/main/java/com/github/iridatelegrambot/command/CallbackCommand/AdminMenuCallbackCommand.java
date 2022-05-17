package com.github.iridatelegrambot.command.CallbackCommand;

import com.github.iridatelegrambot.service.HandleUserTelegramService;
import com.github.iridatelegrambot.service.senders.CommandCallbackSenderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.objects.CallbackQuery;

@Component
public class AdminMenuCallbackCommand implements CallbackCommand {
    private final CommandCallbackSenderService sendMessageService;
    private final CallbackCommandName commandName = CallbackCommandName.ADMIN_MENU;
    private final HandleUserTelegramService handleUserTelegramService;
    private Long chatId;
    private Integer messageId;

    @Autowired
    public AdminMenuCallbackCommand(CommandCallbackSenderService sendMessageService, HandleUserTelegramService handleUserTelegramService) {
        this.sendMessageService = sendMessageService;
        this.handleUserTelegramService = handleUserTelegramService;
    }

    @Override
    public void execute(CallbackQuery callbackQuery) {
        chatId = callbackQuery.getMessage().getChatId();
        messageId = callbackQuery.getMessage().getMessageId();
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

    @Override
    public String getNameCommand() {
        return commandName.getName();
    }

    private void sendListUsersForCloseStatus(){
        sendMessageService.sendAdminSetStatus(chatId,false,"Выберете пользователя которому хотите закрыть доступ.",messageId);
    }

    private void sendListUsersForOpenStatus(){
        sendMessageService.sendAdminSetStatus(chatId,true,"Выберете пользователя которому хотите открыть доступ.",messageId);
    }

    private void sendListUsersForSetAdmin(){
        sendMessageService.sendUsersForAdmin(chatId,"Выберете пользователю которому хотите выдать права администратора", messageId);
    }

    private void pullOffAdmin(){
        if(!handleUserTelegramService.checkUserOnEmpty(chatId)){
            handleUserTelegramService.setUserAdminStatus(chatId,false);
            sendMessageService.sendMessage(chatId.toString(),"Вы сняли с себя права администратора!");
        } else {
            sendMessageService.sendMessage(chatId.toString(),"Пользователь не найден.");
        }
    }
}