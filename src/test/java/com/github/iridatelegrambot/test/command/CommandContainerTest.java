package com.github.iridatelegrambot.test.command;

import com.github.iridatelegrambot.command.*;
import com.github.iridatelegrambot.service.senders.CommandSenderService;
import com.github.iridatelegrambot.service.senders.CommandSenderServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import static org.junit.jupiter.api.Assertions.*;

class CommandContainerTest {

    private CommandContainer container;
    private CommandSenderService commandSenderService;

    @BeforeEach
    void init(){
        container = new CommandContainer();
        commandSenderService = Mockito.mock(CommandSenderServiceImpl.class);
    }

    @Test
    void shouldSetCommand(){
        //given
        Command helpCommand = new HelpCommand(commandSenderService);
        //when
        container.setCommand(helpCommand);
        Command command = container.findCommand("/help");
        //then
        assertEquals(helpCommand,command);
    }

    @Test
    void shouldGetUnknownCommand(){
        //given
        String commandIdentifier = "/aboba";
        Command unknownCommand = new UnknownCommand(commandSenderService);
        container.setUnknownCommand(unknownCommand);
        //when
        Command unknown = container.findCommand(commandIdentifier);

        //then
        assertEquals(UnknownCommand.class,unknownCommand.getClass());
    }

}