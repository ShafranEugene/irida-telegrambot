package com.github.iridatelegrambot.bot;

import com.github.iridatelegrambot.command.*;
import com.github.iridatelegrambot.service.SendMessageServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.objects.Update;

import static com.github.iridatelegrambot.command.CommandName.NO;

@Component
public class IridaBot extends TelegramLongPollingBot {

    private final static String COMMAND_PREFIX = "/";

    private final CommandContainer container;

    @Value("${bot.username}")
    private String username;

    @Value("${bot.token}")
    private String token;

    public IridaBot() {
        this.container = new CommandContainer(new SendMessageServiceImpl(this));
    }
    @Override
    public String getBotUsername() {
        return username;
    }

    @Override
    public String getBotToken() {
        return token;
    }

    @Override
    public void onUpdateReceived(Update update) {
        if(update.hasMessage() && update.getMessage().hasText()){
            String message = update.getMessage().getText().trim();
            String chatId = update.getMessage().getChatId().toString();

            if(message.startsWith(COMMAND_PREFIX)){
                String commandIdentifier = message.split(" ")[0].toLowerCase();

                container.findCommand(commandIdentifier).execute(update);
            } else {
                container.findCommand(NO.getCommandName()).execute(update);
            }
        }

    }
}
