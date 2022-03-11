package com.github.iridatelegrambot.command;

import com.github.iridatelegrambot.service.SendMessageService;
import com.github.iridatelegrambot.service.SendMessageServiceImpl;
import com.google.common.collect.ImmutableMap;

public class СommandContainer {

    private final ImmutableMap<String,Command> commandMap;
    private final Command unknowCommand;

    public СommandContainer(SendMessageService sendMessage) {
        commandMap = ImmutableMap.builder()
                .put(START.getCommandName,new StartCommand());1
    }
}
