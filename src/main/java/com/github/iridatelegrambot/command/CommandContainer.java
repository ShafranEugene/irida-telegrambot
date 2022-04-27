package com.github.iridatelegrambot.command;

import com.github.iridatelegrambot.service.send.SendMessageMainMenuService;
import com.github.iridatelegrambot.service.send.SendMessageOrderMenuService;
import com.github.iridatelegrambot.service.send.SendMessageService;
import com.github.iridatelegrambot.service.UserTelegramService;
import com.github.iridatelegrambot.service.send.SendMessageStatMenuService;
import com.google.common.collect.ImmutableMap;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class CommandContainer {

    private final ImmutableMap<String,Command> commandMap;
    private final Command unknownCommand;
    @Autowired
    public CommandContainer(SendMessageService sendMessage, UserTelegramService userTelegramService,
                            SendMessageMainMenuService sendMainMenu, SendMessageStatMenuService sendMessageStatMenuService,
                            SendMessageOrderMenuService sendMessageOrderMenuService) {

        commandMap = ImmutableMap.<String, Command>builder()
                .put(CommandName.START.getCommandName(), new StartCommand(sendMainMenu,userTelegramService))
                .put(CommandName.STOP.getCommandName(), new StopCommand(sendMessage,userTelegramService))
                .put(CommandName.HELP.getCommandName(), new HelpCommand(sendMessage))
                .put(CommandName.NO.getCommandName(), new NoCommand(sendMessage))
                .put(CommandName.STAT.getCommandName(),new StatCommand(sendMessageStatMenuService,userTelegramService))
                .put(CommandName.ADDORDER.getCommandName(),new AddOrderCommand(sendMessage))
                .put(CommandName.ADDINVOICE.getCommandName(),new AddInvoiceCommand(sendMessage))
                .put(CommandName.SHOWACTIVEORDER.getCommandName(),new ShowActiveOrdersCommand(sendMessageOrderMenuService))
                .build();

        unknownCommand = new UnknownCommand(sendMessage);

    }

    public Command findCommand(String commandIdentifier){
        return commandMap.getOrDefault(commandIdentifier,unknownCommand);
    }
}
