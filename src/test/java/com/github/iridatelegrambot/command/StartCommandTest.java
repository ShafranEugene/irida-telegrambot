package com.github.iridatelegrambot.command;

import static com.github.iridatelegrambot.command.CommandName.HELP;
import static com.github.iridatelegrambot.command.CommandName.START;
import static com.github.iridatelegrambot.command.StartCommand.START_MESSAGE;

public class StartCommandTest extends AbstractCommandTest{
    @Override
    String getCommandName() {
        return START.getCommandName();
    }

    @Override
    String getCommandMessage() {
        return START_MESSAGE;
    }

    @Override
    Command getCommand() {
        return new StartCommand(sendMessageService,mUserTelegramService);
    }
}
