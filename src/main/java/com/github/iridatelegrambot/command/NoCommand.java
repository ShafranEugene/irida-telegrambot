package com.github.iridatelegrambot.command;

import com.github.iridatelegrambot.service.send.SendMessageService;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.objects.Update;
@Component
public class NoCommand implements Command{

    private final SendMessageService sendMessage;
    private final CommandName commandName = CommandName.NO;

    public final static String NO_MESSAGE = "Я поддерживаю команды, о которых ты можешь узнать по команде /help что я уже умею.";

    public NoCommand(SendMessageService sendMessage) {
        this.sendMessage = sendMessage;
    }

    @Override
    public void execute(Update update) {
        sendMessage.sendMessage(update.getMessage().getChatId().toString(),NO_MESSAGE);
    }

    @Override
    public CommandName getCommand() {
        return commandName;
    }
}
