package com.github.iridatelegrambot.test.command;


import com.github.iridatelegrambot.command.Command;
import com.github.iridatelegrambot.command.CommandName;
import com.github.iridatelegrambot.command.UnknownCommand;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import static com.github.iridatelegrambot.command.UnknownCommand.UNKNOWN_MESSAGE;

public class UnknownCommandTest extends AbstractCommandTest{
    @Override
    String getCommandName() {
        return CommandName.NO.getCommandName();
    }

    @Override
    String getCommandMessage() {
        return UNKNOWN_MESSAGE;
    }

    @Override
    Command getCommand() {
        return new UnknownCommand(commandSenderService);
    }

    @Test
    @Override
    void shouldGetProperlyCommand(){
        Assertions.assertEquals(getCommandName(),getCommand().getCommand().getCommandName());
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
