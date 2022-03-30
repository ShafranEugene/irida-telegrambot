package com.github.iridatelegrambot.test.command;


import com.github.iridatelegrambot.command.Command;
import com.github.iridatelegrambot.command.UnknownCommand;

import static com.github.iridatelegrambot.command.UnknownCommand.UNKNOWN_MESSAGE;

public class UnknownCommandTest extends AbstractCommandTest{
    @Override
    String getCommandName() {
        return "/aboba";
    }

    @Override
    String getCommandMessage() {
        return UNKNOWN_MESSAGE;
    }

    @Override
    Command getCommand() {
        return new UnknownCommand(sendMessageService);
    }
}
