package com.github.iridatelegrambot.bot;

import com.github.iridatelegrambot.command.CallbackCommand.CallbackCommandContainer;
import com.github.iridatelegrambot.command.CommandContainer;
import com.github.iridatelegrambot.command.CommandName;
import com.github.iridatelegrambot.service.buttons.CommandNameForButtons;
import com.github.iridatelegrambot.service.statususer.CheckStatusUserService;
import com.github.iridatelegrambot.service.statuswait.HandleWaitNumber;
import com.github.iridatelegrambot.service.statuswait.WaitDocument;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.objects.CallbackQuery;
import org.telegram.telegrambots.meta.api.objects.Update;

import java.util.Objects;


@Component
public class IridaBot extends TelegramLongPollingBot {

    private final static String COMMAND_PREFIX = "/";

    private final CommandContainer container;
    private final CallbackCommandContainer callbackCommandContainer;
    private final HandleWaitNumber handleWaitNumber;
    private final CheckStatusUserService checkStatusUserService;
    private final static Logger logger = LoggerFactory.getLogger(IridaBot.class);

    @Value("${bot.username}")
    private String username;

    @Value("${bot.token}")
    private String token;

    @Autowired
    public IridaBot(CommandContainer container, CallbackCommandContainer callbackCommandContainer,
                    HandleWaitNumber handleWaitNumber, CheckStatusUserService checkStatusUserService) {
        this.container = container;
        this.callbackCommandContainer = callbackCommandContainer;
        this.handleWaitNumber = handleWaitNumber;
        this.checkStatusUserService = checkStatusUserService;
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

        if(!checkStatusUserService.check(update)){
            return;
        }

        if(update.hasCallbackQuery()){
            handleCallback(update);
        } else if(update.hasMessage()){
            handleMessage(update);
        }

    }


    private void handleMessage(Update update){
        Long idChat = update.getMessage().getChatId();

        if(WaitDocument.getWaitAllStatus(idChat)){
            handleWaitNumber.handle(update);
            return;
        }

        if(update.getMessage().hasText()){
            String message = update.getMessage().getText().trim();

            if(message.startsWith(COMMAND_PREFIX)){
                String commandIdentifier = message.split(" ")[0].toLowerCase();

                container.findCommand(commandIdentifier).execute(update);
            } else if(CommandNameForButtons.hasMainCommand(message)) {
                    container.findCommand(Objects.requireNonNull(CommandNameForButtons.findCommandName(message)).getCommandName()).execute(update);
            } else {
                    container.findCommand(CommandName.NO.getCommandName()).execute(update);
                }
            logger.info("Bot received a command \"" + message + "\" from User - " + update.getMessage().getChat().getUserName());
        }
    }


    private void handleCallback(Update update){
        CallbackQuery callbackQuery = update.getCallbackQuery();
        callbackCommandContainer.findAnswer(callbackQuery).execute(callbackQuery);
        logger.info("Bot received a callback \"" + callbackQuery.getData() + "\" from User - " + update.getCallbackQuery().getMessage().getChat().getUserName());

    }
}
