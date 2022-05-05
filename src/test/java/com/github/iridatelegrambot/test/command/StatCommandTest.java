package com.github.iridatelegrambot.test.command;
import com.github.iridatelegrambot.command.StatCommand;
import com.github.iridatelegrambot.service.buttons.InlineKeyboardService;
import com.github.iridatelegrambot.service.buttons.MenuButtonsService;
import com.github.iridatelegrambot.service.send.CommandSenderService;
import com.github.iridatelegrambot.service.send.CommandSenderServiceImpl;
import com.github.iridatelegrambot.service.UserTelegramService;
import com.github.iridatelegrambot.bot.IridaBot;
import com.github.iridatelegrambot.service.send.SendMessageServiceImpl;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Message;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

import static com.github.iridatelegrambot.command.CommandName.STAT;

public class StatCommandTest {

    private final UserTelegramService mUserTelegramService = Mockito.mock(UserTelegramService.class);
    private final IridaBot mIridaBot = Mockito.mock(IridaBot.class);
    private final InlineKeyboardService mInlineKeyboardService = Mockito.mock(InlineKeyboardService.class);
    private final MenuButtonsService menuButtonsService = Mockito.mock(MenuButtonsService.class);
    private final SendMessageServiceImpl sendMessageService = new SendMessageServiceImpl(mIridaBot,mInlineKeyboardService,menuButtonsService);
    private final CommandSenderService commandSenderService = new CommandSenderServiceImpl(sendMessageService,sendMessageService,sendMessageService,sendMessageService);
    private final StatCommand statCommand = new StatCommand(commandSenderService,mUserTelegramService);

    @Test
    void shouldProperSendMessage() throws TelegramApiException {
        //given
        Long chatId = 12345678L;

        Update update = new Update();

        Message message = Mockito.mock(Message.class);
        Mockito.when(message.getChatId()).thenReturn(chatId);
        Mockito.when(message.getText()).thenReturn(STAT.getCommandName());

        update.setMessage(message);

        int quantityUsers = mUserTelegramService.getAllActiveUser().size();

        SendMessage sendMessage = new SendMessage();
        sendMessage.setChatId(chatId.toString());
        sendMessage.setText(String.format(StatCommand.STAT_MESSAGE,quantityUsers));
        sendMessage.enableHtml(true);

        //when
        statCommand.execute(update);

        //then
        Mockito.verify(mIridaBot).execute(sendMessage);
    }
}
