package com.github.iridatelegrambot.command;

import com.github.iridatelegrambot.service.SendMessageService;
import com.github.iridatelegrambot.service.SendMessageServiceImpl;
import com.github.iridatelegrambot.service.UserTelegramService;
import com.google.common.collect.ImmutableMap;

import static com.github.iridatelegrambot.command.CommandName.*;

public class CommandContainer {

    private final ImmutableMap<String,Command> commandMap;
    private final Command unknownCommand;

    public CommandContainer(SendMessageService sendMessage, UserTelegramService userTelegramService) {

        commandMap = ImmutableMap.<String, Command>builder()
                .put(START.getCommandName(), new StartCommand((SendMessageServiceImpl) sendMessage,userTelegramService))
                .put(STOP.getCommandName(), new StopCommand((SendMessageServiceImpl) sendMessage,userTelegramService))
                .put(HELP.getCommandName(), new HelpCommand((SendMessageServiceImpl) sendMessage))
                .put(NO.getCommandName(), new NoCommand((SendMessageServiceImpl) sendMessage))
                .put(STAT.getCommandName(),new StatCommand((SendMessageServiceImpl) sendMessage,userTelegramService))
                .build();

        unknownCommand = new UnknownCommand((SendMessageServiceImpl) sendMessage);

    }

    public Command findCommand(String commandIdentifier){
        return commandMap.getOrDefault(commandIdentifier,unknownCommand);
    }
}
