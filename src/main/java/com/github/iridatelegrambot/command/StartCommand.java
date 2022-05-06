package com.github.iridatelegrambot.command;

import com.github.iridatelegrambot.service.senders.CommandSenderService;
import com.github.iridatelegrambot.service.UserTelegramService;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.objects.Update;
@Component
public class StartCommand implements Command{

    private final CommandSenderService sendMessage;
    private final UserTelegramService telegramService;
    private final CommandName commandName = CommandName.START;

    public final static String START_MESSAGE = "Привет! Я бот Ирида. По команде /help ты можешь узнать что я умею.";

    public StartCommand(CommandSenderService sendMessage, UserTelegramService telegramService) {
        this.sendMessage = sendMessage;
        this.telegramService = telegramService;
    }

    @Override
    public void execute(Update update) {
        Long chatId = update.getMessage().getChatId();
        telegramService.findOrCreateUser(update);
        sendMessage.sendMainMenu(chatId,START_MESSAGE);
    }

    @Override
    public CommandName getCommand() {
        return commandName;
    }
}
