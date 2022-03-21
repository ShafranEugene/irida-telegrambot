package com.github.iridatelegrambot.command;

import static com.github.iridatelegrambot.command.CommandName.STAT;
import static com.github.iridatelegrambot.command.StatCommand.STAT_MESSAGE;

public class StatCommandTest extends AbstractCommandTest {
    @Override
    String getCommandName() {
        return STAT.getCommandName();
    }

    @Override
    String getCommandMessage() {
        return STAT_MESSAGE;
    }

    @Override
    Command getCommand() {
        return new StatCommand(sendMessageService,mUserTelegramService);
    }
}
