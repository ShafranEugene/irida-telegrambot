package com.github.iridatelegrambot.command;

import com.github.iridatelegrambot.service.SendMessageService;
import com.github.iridatelegrambot.service.SendMessageServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.util.ArrayList;
import java.util.Arrays;

import static org.junit.jupiter.api.Assertions.*;

class CommandContainerTest {

    private CommandContainer container;
    private SendMessageService mSendMessageService;

    @BeforeEach
    void init(){
        mSendMessageService = Mockito.mock(SendMessageServiceImpl.class);
        container = new CommandContainer(mSendMessageService);
    }

    @Test
    void shouldGetUnknownCommand(){
        //given
        String commandIdentifier = "/aboba";

        //when
        Command unknownCommand = container.findCommand(commandIdentifier);

        //then
        assertEquals(UnknownCommand.class,unknownCommand.getClass());
    }

    @Test
    void shouldGetAnyExistingCommand(){
        //given
        CommandName[] allCommand = CommandName.values();

        for(CommandName commandName : allCommand){
            //when
            Command command = container.findCommand(commandName.getCommandName());
            //then
            assertNotEquals(UnknownCommand.class,command.getClass());
        }
    }



}