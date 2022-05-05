package com.github.iridatelegrambot.test.command;

import com.github.iridatelegrambot.command.Command;
import com.github.iridatelegrambot.command.ShowActiveOrdersCommand;

import static com.github.iridatelegrambot.command.CommandName.SHOWACTIVEORDER;
import static com.github.iridatelegrambot.command.ShowActiveOrdersCommand.SHOW_ACTIVE_ORDERS_MESSAGE;

public class ShowActiveOrdersCommandTest extends AbstractCommandTest{
    @Override
    String getCommandName() {
        return SHOWACTIVEORDER.getCommandName();
    }

    @Override
    String getCommandMessage() {
        return SHOW_ACTIVE_ORDERS_MESSAGE;
    }

    @Override
    Command getCommand() {
        return new ShowActiveOrdersCommand(commandSenderService);
    }
}
