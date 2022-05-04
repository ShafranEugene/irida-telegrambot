package com.github.iridatelegrambot.command;

import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;

@Component
public class CommandContainer {

    private final Map<String,Command> commandMap = new HashMap<>();
    private Command unknownCommand;

    public void setCommand(Command command){
        commandMap.put(command.getCommand().getCommandName(),command);
    }

    public void setUnknownCommand(Command unknownCommand) {
        this.unknownCommand = unknownCommand;
    }

    public Command findCommand(String commandIdentifier){
        return commandMap.getOrDefault(commandIdentifier,unknownCommand);
    }
}
