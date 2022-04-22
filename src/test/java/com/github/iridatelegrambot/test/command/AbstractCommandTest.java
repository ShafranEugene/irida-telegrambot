package com.github.iridatelegrambot.test.command;

import com.github.iridatelegrambot.command.Command;
import com.github.iridatelegrambot.service.SendMessageServiceImpl;
import com.github.iridatelegrambot.service.UserTelegramService;
import com.github.iridatelegrambot.bot.IridaBot;
import com.github.iridatelegrambot.service.buttons.InlineKeyboardService;
import com.github.iridatelegrambot.service.buttons.MenuButtonsService;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Chat;
import org.telegram.telegrambots.meta.api.objects.Message;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

abstract class AbstractCommandTest {
    protected UserTelegramService mUserTelegramService = Mockito.mock(UserTelegramService.class);
    protected IridaBot mIridaBot = Mockito.mock(IridaBot.class);
    protected InlineKeyboardService mInlineKeyboardService = Mockito.mock(InlineKeyboardService.class);
    protected MenuButtonsService menuButtonsService = Mockito.mock(MenuButtonsService.class);
    protected SendMessageServiceImpl sendMessageService = new SendMessageServiceImpl(mIridaBot,mInlineKeyboardService,menuButtonsService);

    abstract String getCommandName();

    abstract String getCommandMessage();

    abstract Command getCommand();

    @Test
    void shouldExecuteCommand() throws TelegramApiException {
        //given
        Long chatId = 12345678L;

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

        SendMessage sendMessage = new SendMessage();
        sendMessage.setChatId(chatId.toString());
        sendMessage.setText(getCommandMessage());
        sendMessage.enableHtml(true);

        //when
        getCommand().execute(update);

        //then
        Mockito.verify(mIridaBot).execute(sendMessage);
    }
}
