package com.github.iridatelegrambot.test.command;

import com.github.iridatelegrambot.command.Command;
import com.github.iridatelegrambot.service.senders.CommandSenderService;
import com.github.iridatelegrambot.service.senders.CommandSenderServiceImpl;
import com.github.iridatelegrambot.service.UserTelegramService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.telegram.telegrambots.meta.api.objects.Chat;
import org.telegram.telegrambots.meta.api.objects.Message;
import org.telegram.telegrambots.meta.api.objects.Update;

abstract class AbstractCommandTest {
    protected UserTelegramService mUserTelegramService = Mockito.mock(UserTelegramService.class);
    protected CommandSenderService commandSenderService = Mockito.mock(CommandSenderServiceImpl.class);
    protected Long chatId = 12345678L;


    abstract String getCommandName();

    abstract String getCommandMessage();

    abstract Command getCommand();

    abstract void shouldExecuteCommand();

    Update createUpdate() {
        Update update = new Update();
        Message message = Mockito.mock(Message.class);
        Mockito.when(message.getChatId()).thenReturn(chatId);
        Mockito.when(message.getText()).thenReturn(getCommandName());
        Chat chat = Mockito.mock(Chat.class);
        Mockito.when(chat.getFirstName()).thenReturn("Abobus");
        Mockito.when(chat.getUserName()).thenReturn("Abobus228");
        Mockito.when(message.getChat()).thenReturn(chat);
        message.setChat(chat);
        update.setMessage(message);

        return update;
    }

    @Test
    void shouldGetProperlyCommand(){
        Assertions.assertEquals(getCommandName(),getCommand().getCommand().getCommandName());
    }
}
