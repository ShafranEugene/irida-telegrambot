package com.github.iridatelegrambot.command;

import com.github.iridatelegrambot.service.send.SendMessageMainMenuService;
import com.github.iridatelegrambot.service.send.SendMessageService;
import com.github.iridatelegrambot.service.UserTelegramService;
import org.telegram.telegrambots.meta.api.objects.Update;

public class StartCommand implements Command{

    private final SendMessageMainMenuService sendMessage;
    private final UserTelegramService telegramService;

    public final static String START_MESSAGE = "Привет! Я бот Ирида. По команде /help ты можешь узнать что я умею.";

    public StartCommand(SendMessageMainMenuService sendMessage, UserTelegramService telegramService) {
        this.sendMessage = sendMessage;
        this.telegramService = telegramService;
    }

    @Override
    public void execute(Update update) {
        Long chatId = update.getMessage().getChatId();
        telegramService.findOrCreateUser(update);
        sendMessage.sendMainMenu(chatId,START_MESSAGE);
    }
}
