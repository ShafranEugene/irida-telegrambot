package com.github.iridatelegrambot.bot;

import com.github.iridatelegrambot.command.CallbackCommand.CallbackCommandContainer;
import com.github.iridatelegrambot.service.*;
import com.github.iridatelegrambot.command.CommandContainer;
import com.github.iridatelegrambot.command.CommandName;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.objects.CallbackQuery;
import org.telegram.telegrambots.meta.api.objects.Update;

@Component
public class IridaBot extends TelegramLongPollingBot {

    private final static String COMMAND_PREFIX = "/";

    private final CommandContainer container;
    private final AnswerCatcherService answerCatcher;
    private final CheckUpdateOnPost checkUpdateOnPost;
    private final CallbackCommandContainer callbackCommandContainer;

    @Value("${bot.username}")
    private String username;

    @Value("${bot.token}")
    private String token;

    @Autowired
    public IridaBot(UserTelegramService userTelegramService, CheckUpdateOnPost checkUpdateOnPost, OrderService orderService) {
        SendMessageServiceImpl userService = new SendMessageServiceImpl(this);
        this.checkUpdateOnPost = checkUpdateOnPost;
        this.container = new CommandContainer(userService,userTelegramService,checkUpdateOnPost);
        this.callbackCommandContainer = new CallbackCommandContainer(userService,orderService);
        this.answerCatcher = new AnswerCatcherServiceImpl(userService,orderService,checkUpdateOnPost);
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

        if(update.hasCallbackQuery()){
            handleCallback(update);
        } else if(update.hasMessage()){
            handleMessage(update);
        }

    }


    private void handleMessage(Update update){
        Long idChat = update.getMessage().getChatId();
        if(checkUpdateOnPost.waitingNumberOrder(idChat) | checkUpdateOnPost.waitingNumberInvoice(idChat)){
            answerCatcher.answerByOrder(update);
            return;
        }



        if(update.getMessage().hasText()){
            String message = update.getMessage().getText().trim();

            if(message.startsWith(COMMAND_PREFIX)){
                String commandIdentifier = message.split(" ")[0].toLowerCase();

                container.findCommand(commandIdentifier).execute(update);
            } else {
                container.findCommand(CommandName.NO.getCommandName()).execute(update);
            }
        }
    }


    private void handleCallback(Update update){
        CallbackQuery callbackQuery = update.getCallbackQuery();

        callbackCommandContainer.findAnswer(callbackQuery).execute(callbackQuery);

        }
    }
