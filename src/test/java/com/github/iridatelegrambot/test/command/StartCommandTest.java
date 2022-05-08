package com.github.iridatelegrambot.test.command;
import com.github.iridatelegrambot.command.Command;
import com.github.iridatelegrambot.command.StartCommand;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

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
        return new StartCommand(commandSenderService,mUserTelegramService);
    }
    @Test
    @Override
    void shouldExecuteCommand() {
        //when
        getCommand().execute(createUpdate());
        //then
        Mockito.verify(commandSenderService).sendMainMenu(chatId,getCommandMessage());
    }
}
