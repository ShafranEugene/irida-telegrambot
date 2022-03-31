package com.github.iridatelegrambot.command;

import com.github.iridatelegrambot.bot.CheckUpdateOnPost;
import com.github.iridatelegrambot.service.SendMessageService;
import com.github.iridatelegrambot.service.SendMessageServiceImpl;
import com.github.iridatelegrambot.service.UserTelegramService;
import com.google.common.collect.ImmutableMap;

public class CommandContainer {

    private final ImmutableMap<String,Command> commandMap;
    private final Command unknownCommand;

    public CommandContainer(SendMessageService sendMessage, UserTelegramService userTelegramService, CheckUpdateOnPost checkUpdateOnPost) {

        commandMap = ImmutableMap.<String, Command>builder()
                .put(CommandName.START.getCommandName(), new StartCommand((SendMessageServiceImpl) sendMessage,userTelegramService))
                .put(CommandName.STOP.getCommandName(), new StopCommand((SendMessageServiceImpl) sendMessage,userTelegramService))
                .put(CommandName.HELP.getCommandName(), new HelpCommand((SendMessageServiceImpl) sendMessage))
                .put(CommandName.NO.getCommandName(), new NoCommand((SendMessageServiceImpl) sendMessage))
                .put(CommandName.STAT.getCommandName(),new StatCommand((SendMessageServiceImpl) sendMessage,userTelegramService))
                .put(CommandName.ADDORDER.getCommandName(),new AddOrderCommand(sendMessage,checkUpdateOnPost))
                .build();

        unknownCommand = new UnknownCommand((SendMessageServiceImpl) sendMessage);

    }

    public Command findCommand(String commandIdentifier){
        return commandMap.getOrDefault(commandIdentifier,unknownCommand);
    }
}
