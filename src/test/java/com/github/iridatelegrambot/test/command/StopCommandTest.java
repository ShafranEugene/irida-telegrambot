package com.github.iridatelegrambot.test.command;

import com.github.iridatelegrambot.command.Command;
import com.github.iridatelegrambot.command.StopCommand;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

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
        return new StopCommand(commandSenderService,mUserTelegramService);
    }
    @Test
    @Override
    void shouldExecuteCommand() {
        //when
        getCommand().execute(createUpdate());
        //then
        Mockito.verify(commandSenderService).sendMessage(chatId.toString(),getCommandMessage());
    }
}
