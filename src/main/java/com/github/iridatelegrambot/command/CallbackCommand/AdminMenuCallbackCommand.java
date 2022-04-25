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

    public AdminMenuCallbackCommand(SendMessageService sendMessageService, UserTelegramService userTelegramService) {
        this.sendMessageService = sendMessageService;
        this.userTelegramService = userTelegramService;
        adminCommand.add("mainAdminMenu");
    }

    @Override
    public void execute(CallbackQuery callbackQuery) {
        Long chatId = callbackQuery.getMessage().getChatId();
        String[] data = callbackQuery.getData().split(":");


    }
}
