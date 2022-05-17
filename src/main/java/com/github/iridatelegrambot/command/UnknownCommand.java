package com.github.iridatelegrambot.command;

import com.github.iridatelegrambot.service.senders.CommandSenderService;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.objects.Update;
@Component
public class UnknownCommand implements Command{

    private final CommandSenderService commandSenderService;

    public final static String UNKNOWN_MESSAGE = "Я не знаю такой команды, обратившись по /help можешь узнать список доступных команд.";

    public UnknownCommand(CommandSenderService commandSenderService) {
        this.commandSenderService = commandSenderService;
    }

    @Override
    public void execute(Update update) {
        commandSenderService.sendMessage(update.getMessage().getChatId().toString(),UNKNOWN_MESSAGE);
    }

    @Override
    public CommandName getCommand() {
        return CommandName.NO;
    }

    @Override
    public void containerFiller(CommandContainer commandContainer) {
        commandContainer.setUnknownCommand(this);
    }
}
