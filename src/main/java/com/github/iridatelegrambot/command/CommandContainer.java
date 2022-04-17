package com.github.iridatelegrambot.command;

import com.github.iridatelegrambot.bot.CheckUpdateOnPost;
import com.github.iridatelegrambot.service.SendMessageService;
import com.github.iridatelegrambot.service.UserTelegramService;
import com.google.common.collect.ImmutableMap;

public class CommandContainer {

    private final ImmutableMap<String,Command> commandMap;
    private final Command unknownCommand;

    public CommandContainer(SendMessageService sendMessage, UserTelegramService userTelegramService, CheckUpdateOnPost checkUpdateOnPost) {

        commandMap = ImmutableMap.<String, Command>builder()
                .put(CommandName.START.getCommandName(), new StartCommand(sendMessage,userTelegramService))
                .put(CommandName.STOP.getCommandName(), new StopCommand(sendMessage,userTelegramService))
                .put(CommandName.HELP.getCommandName(), new HelpCommand(sendMessage))
                .put(CommandName.NO.getCommandName(), new NoCommand(sendMessage))
                .put(CommandName.STAT.getCommandName(),new StatCommand(sendMessage,userTelegramService))
                .put(CommandName.ADDORDER.getCommandName(),new AddOrderCommand(sendMessage,checkUpdateOnPost))
                .put(CommandName.ADDINVOICE.getCommandName(),new AddInvoiceCommand(sendMessage,checkUpdateOnPost))
                .put(CommandName.SHOWACTIVEORDER.getCommandName(),new ShowActiveOrdersCommand(sendMessage))
                .build();

        unknownCommand = new UnknownCommand(sendMessage);

    }

    public Command findCommand(String commandIdentifier){
        return commandMap.getOrDefault(commandIdentifier,unknownCommand);
    }
}
