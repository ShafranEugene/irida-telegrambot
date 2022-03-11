package com.github.iridatelegrambot.command;

import com.github.iridatelegrambot.service.SendMessageServiceImpl;
import org.telegram.telegrambots.meta.api.objects.Update;

public class HelpCommand implements Command{

    private final SendMessageServiceImpl sendMessage;

    private final static String HELP_MESSAGE = String.format("Доступные команды \n\n"
                                                + "%s - начать работу со мной\n"
                                                + "%s - приостановить работу\n"
                                                + "%s - получить помощь\n");

    public HelpCommand(SendMessageServiceImpl sendMessage) {
        this.sendMessage = sendMessage;
    }

    @Override
    public void execute(Update update) {
        sendMessage.sendMessage(update.getMessage().getChatId().toString(),HELP_MESSAGE);
    }
}
