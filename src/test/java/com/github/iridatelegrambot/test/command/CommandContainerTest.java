package com.github.iridatelegrambot.test.command;

import com.github.iridatelegrambot.command.*;
import com.github.iridatelegrambot.service.send.SendMessageService;
import com.github.iridatelegrambot.service.send.SendMessageServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import java.util.*;

import static org.junit.jupiter.api.Assertions.*;

class CommandContainerTest {

    private CommandContainer container;
    private SendMessageService sendMessageService;

    @BeforeEach
    void init(){
        container = new CommandContainer();
        sendMessageService = Mockito.mock(SendMessageServiceImpl.class);
    }

    @Test
    void shouldSetCommand(){
        //given
        Command helpCommand = new HelpCommand(sendMessageService);
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
        Command unknownCommand = new UnknownCommand(sendMessageService);
        container.setUnknownCommand(unknownCommand);
        //when
        Command unknown = container.findCommand(commandIdentifier);

        //then
        assertEquals(UnknownCommand.class,unknownCommand.getClass());
    }

}