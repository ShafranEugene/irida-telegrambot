package com.github.iridatelegrambot.command;

import com.github.iridatelegrambot.service.send.SendMessageService;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.objects.Update;
@Component
public class UnknownCommand implements Command{

    private final SendMessageService sendMessage;

    public final static String UNKNOWN_MESSAGE = "Я не знаю такой команды, обратившись по /help можешь узнать список доступных команд.";

    public UnknownCommand(SendMessageService sendMessage) {
        this.sendMessage = sendMessage;
    }

    @Override
    public void execute(Update update) {
        sendMessage.sendMessage(update.getMessage().getChatId().toString(),UNKNOWN_MESSAGE);
    }

    @Override
    public CommandName getCommand() {
        return null;
    }

    @Override
    public void containerFiller(CommandContainer commandContainer) {
        commandContainer.setUnknownCommand(this);
    }
}
