package com.github.iridatelegrambot.command;

import com.github.iridatelegrambot.service.SendMessageService;
import com.github.iridatelegrambot.service.SendMessageServiceImpl;
import com.github.iridatelegrambot.service.UserTelegramService;
import org.telegram.telegrambots.meta.api.objects.Update;

public class StopCommand implements Command{

    private final SendMessageService sendMessage;
    private final UserTelegramService userTelegramService;

    public final static String STOP_MESSAGE = "Бот остановлен.";

    public StopCommand(SendMessageService sendMessage, UserTelegramService userTelegramService) {
        this.sendMessage = sendMessage;
        this.userTelegramService = userTelegramService;
    }

    @Override
    public void execute(Update update) {
        sendMessage.sendMessage(update.getMessage().getChatId().toString(),STOP_MESSAGE);

        Long chatId = update.getMessage().getChatId();

        userTelegramService.findByChatId(chatId).ifPresent(
                userTelegram -> {
                    userTelegram.setActive(false);
                    userTelegramService.save(userTelegram);
                });
    }
}
