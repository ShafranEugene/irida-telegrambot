package com.github.iridatelegrambot.command;

import com.github.iridatelegrambot.service.SendMessageServiceImpl;
import com.github.iridatelegrambot.service.UserTelegramService;
import org.telegram.telegrambots.meta.api.objects.Update;

public class StatCommand implements Command{

    private final SendMessageServiceImpl sendMessage;
    private final UserTelegramService telegramService;

    public final static String STAT_MESSAGE ="Количество активных пользователей: %s";

    public StatCommand(SendMessageServiceImpl sendMessage, UserTelegramService telegramService) {
        this.sendMessage = sendMessage;
        this.telegramService = telegramService;
    }

    @Override
    public void execute(Update update) {
        int quantityUsers = telegramService.getAllActiveUser().size();
        sendMessage.sendMessage(update.getMessage().getChatId().toString(),String.format(STAT_MESSAGE, quantityUsers));
    }
}
