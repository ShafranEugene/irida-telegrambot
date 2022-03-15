package com.github.iridatelegrambot.command;

import com.github.iridatelegrambot.service.SendMessageServiceImpl;
import org.telegram.telegrambots.meta.api.objects.Update;

public class UnknownCommand implements Command{

    private final SendMessageServiceImpl sendMessage;

    public final static String UNKNOWN_MESSAGE = "Я не знаю такой команды, обратившись по /help можешь узнать список доступных команд.";

    public UnknownCommand(SendMessageServiceImpl sendMessage) {
        this.sendMessage = sendMessage;
    }

    @Override
    public void execute(Update update) {
        sendMessage.sendMessage(update.getMessage().getChatId().toString(),UNKNOWN_MESSAGE);
    }
}
