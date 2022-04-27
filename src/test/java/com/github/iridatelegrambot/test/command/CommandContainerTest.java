package com.github.iridatelegrambot.test.command;

import com.github.iridatelegrambot.command.Command;
import com.github.iridatelegrambot.command.CommandContainer;
import com.github.iridatelegrambot.command.CommandName;
import com.github.iridatelegrambot.command.UnknownCommand;
import com.github.iridatelegrambot.service.send.*;
import com.github.iridatelegrambot.service.UserTelegramService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import static org.junit.jupiter.api.Assertions.*;

class CommandContainerTest {

    private CommandContainer container;
    private SendMessageService mSendMessageService;
    private UserTelegramService mUserTelegramService;
    private SendMessageMainMenuService sendMainMenu;
    private SendMessageStatMenuService sendMessageStatMenuService;
    private SendMessageOrderMenuService sendMessageOrderMenuService;

    @BeforeEach
    void init(){
        mSendMessageService = Mockito.mock(SendMessageServiceImpl.class);
        mUserTelegramService = Mockito.mock(UserTelegramService.class);
        sendMainMenu = Mockito.mock(SendMessageServiceImpl.class);
        sendMessageStatMenuService = Mockito.mock(SendMessageServiceImpl.class);
        sendMessageOrderMenuService = Mockito.mock(SendMessageServiceImpl.class);
        container = new CommandContainer(mSendMessageService,mUserTelegramService,sendMainMenu,sendMessageStatMenuService,sendMessageOrderMenuService);
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