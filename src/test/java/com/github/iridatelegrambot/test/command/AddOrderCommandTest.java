package com.github.iridatelegrambot.test.command;

import com.github.iridatelegrambot.command.AddOrderCommand;
import com.github.iridatelegrambot.command.Command;

import static com.github.iridatelegrambot.command.AddOrderCommand.ADDORDER_MESSAGE;
import static com.github.iridatelegrambot.command.CommandName.ADDORDER;

public class AddOrderCommandTest extends AbstractCommandTest {
    @Override
    String getCommandName() {
        return ADDORDER.getCommandName();
    }

    @Override
    String getCommandMessage() {
        return ADDORDER_MESSAGE;
    }

    @Override
    Command getCommand() {
        return new AddOrderCommand(sendMessageService,mCheckUpdateOnPost);
    }
}
