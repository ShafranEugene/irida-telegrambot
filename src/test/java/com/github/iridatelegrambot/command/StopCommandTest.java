package com.github.iridatelegrambot.command;

import static com.github.iridatelegrambot.command.CommandName.STOP;
import static com.github.iridatelegrambot.command.StopCommand.STOP_MESSAGE;

public class StopCommandTest extends AbstractCommandTest {
    @Override
    String getCommandName() {
        return STOP.getCommandName();
    }

    @Override
    String getCommandMessage() {
        return STOP_MESSAGE;
    }

    @Override
    Command getCommand() {
        return new StopCommand(sendMessageService);
    }
}
