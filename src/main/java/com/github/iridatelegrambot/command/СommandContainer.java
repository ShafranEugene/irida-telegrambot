package com.github.iridatelegrambot.command;

import com.github.iridatelegrambot.service.SendMessageService;
import com.github.iridatelegrambot.service.SendMessageServiceImpl;
import com.google.common.collect.ImmutableMap;

import static com.github.iridatelegrambot.command.CommandName.*;

public class СommandContainer {

    private final ImmutableMap<String,Command> commandMap;
    private final Command unknowCommand;

    public СommandContainer(SendMessageService sendMessage) {

        commandMap = ImmutableMap.<String, Command>builder()
                .put(START.getCommandName(), new StartCommand((SendMessageServiceImpl) sendMessage))
                .put(STOP.getCommandName(), new StopCommand((SendMessageServiceImpl) sendMessage))
                .put(HELP.getCommandName(), new HelpCommand((SendMessageServiceImpl) sendMessage))
                .put(NO.getCommandName(), new NoCommand((SendMessageServiceImpl) sendMessage))
                .build();

        unknowCommand = new UnknownCommand((SendMessageServiceImpl) sendMessage);

    }
}
