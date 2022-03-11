package com.github.iridatelegrambot.command;

import com.github.iridatelegrambot.service.SendMessageServiceImpl;
import org.telegram.telegrambots.meta.api.objects.Update;

public class StartCommand implements Command{

    private final SendMessageServiceImpl sendMessage;

    private final static String START_MESSAGE = "Привет! Я бот Ирида. По команде /help ты можешь узнать что я уже умею.";

    public StartCommand(SendMessageServiceImpl sendMessage) {
        this.sendMessage = sendMessage;
    }

    @Override
    public void execute(Update update) {
        sendMessage.sendMessage(update.getMessage().getChatId().toString(),START_MESSAGE);
    }
}
