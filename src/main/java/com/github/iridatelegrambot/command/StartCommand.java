package com.github.iridatelegrambot.command;

import com.github.iridatelegrambot.entity.UserTelegram;
import com.github.iridatelegrambot.service.SendMessageServiceImpl;
import com.github.iridatelegrambot.service.UserTelegramService;
import org.telegram.telegrambots.meta.api.objects.Update;

public class StartCommand implements Command{

    private final SendMessageServiceImpl sendMessage;
    private final UserTelegramService telegramService;

    public final static String START_MESSAGE = "Привет! Я бот Ирида. По команде /help ты можешь узнать что я уже умею.";

    public StartCommand(SendMessageServiceImpl sendMessage, UserTelegramService telegramService) {
        this.sendMessage = sendMessage;
        this.telegramService = telegramService;
    }

    @Override
    public void execute(Update update) {
        sendMessage.sendMessage(update.getMessage().getChatId().toString(),START_MESSAGE);

        Long chatId = update.getMessage().getChatId();

        telegramService.findByChatId(chatId).ifPresentOrElse(
                userTelegram -> {
                    userTelegram.setActive(true);
                    telegramService.save(userTelegram);
                },
                () -> {
                    UserTelegram user = new UserTelegram();
                    user.setActive(true);
                    user.setChatId(chatId);
                    telegramService.save(user);
                });
    }
}
