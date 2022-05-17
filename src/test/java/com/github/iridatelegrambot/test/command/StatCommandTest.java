package com.github.iridatelegrambot.test.command;
import com.github.iridatelegrambot.command.StatCommand;
import com.github.iridatelegrambot.service.senders.CommandSenderService;
import com.github.iridatelegrambot.service.senders.CommandSenderServiceImpl;
import com.github.iridatelegrambot.service.UserTelegramService;
import com.github.iridatelegrambot.bot.IridaBot;
import com.github.iridatelegrambot.service.senders.SendMessageServiceImpl;
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
    private final CommandSenderService commandSenderService = Mockito.mock(CommandSenderService.class);
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
        Mockito.verify(commandSenderService).sendStatMenu(chatId,"Количество активных пользователей : 0\nАктивные пользователеи:");
    }
}
