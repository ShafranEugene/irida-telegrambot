package com.github.iridatelegrambot.test.command;

import com.github.iridatelegrambot.command.Command;
import com.github.iridatelegrambot.command.HelpCommand;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import static com.github.iridatelegrambot.command.CommandName.HELP;
import static com.github.iridatelegrambot.command.HelpCommand.HELP_MESSAGE;

public class HelpCommandTest extends AbstractCommandTest {
    @Override
    String getCommandName() {
        return HELP.getCommandName();
    }

    @Override
    String getCommandMessage() {
        return HELP_MESSAGE;
    }

    @Override
    Command getCommand() {
        return new HelpCommand(commandSenderService);
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
