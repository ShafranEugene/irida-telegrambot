package com.github.iridatelegrambot.command;

import com.github.iridatelegrambot.service.SendMessageServiceImpl;
import org.telegram.telegrambots.meta.api.objects.Update;

public class StopCommand implements Command{

    private final SendMessageServiceImpl sendMessage;

    public final static String STOP_MESSAGE = "Бот остановлен.";

    public StopCommand(SendMessageServiceImpl sendMessage) {
        this.sendMessage = sendMessage;
    }

    @Override
    public void execute(Update update) {
        sendMessage.sendMessage(update.getMessage().getChatId().toString(),STOP_MESSAGE);
    }
}
