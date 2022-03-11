package com.github.iridatelegrambot.command;

import com.github.iridatelegrambot.service.SendMessageServiceImpl;
import org.telegram.telegrambots.meta.api.objects.Update;

public class NoCommand implements Command{

    private final SendMessageServiceImpl sendMessage;

    private final static String NO_MESSAGE = "Я поддерживаю команды, о которых ты можешь узнать по команде /help ты можешь узнать что я уже умею.";

    public NoCommand(SendMessageServiceImpl sendMessage) {
        this.sendMessage = sendMessage;
    }

    @Override
    public void execute(Update update) {
        sendMessage.sendMessage(update.getMessage().getChatId().toString(),NO_MESSAGE);
    }
}
