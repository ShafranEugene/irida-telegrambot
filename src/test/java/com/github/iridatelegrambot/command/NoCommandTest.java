package com.github.iridatelegrambot.command;

import static com.github.iridatelegrambot.command.CommandName.NO;
import static com.github.iridatelegrambot.command.NoCommand.NO_MESSAGE;

public class NoCommandTest extends AbstractCommandTest {
    @Override
    String getCommandName() {
        return NO.getCommandName();
    }

    @Override
    String getCommandMessage() {
        return NO_MESSAGE;
    }

    @Override
    Command getCommand() {
        return new NoCommand(sendMessageService);
    }
}
