package com.github.iridatelegrambot.command;

import com.github.iridatelegrambot.bot.CheckUpdateOnPost;
import com.github.iridatelegrambot.entity.UserTelegram;
import com.github.iridatelegrambot.service.OrderService;
import com.github.iridatelegrambot.service.SendMessageService;
import com.github.iridatelegrambot.service.UserTelegramService;
import org.telegram.telegrambots.meta.api.objects.Update;

public class AddOrderCommand implements Command{

    private final SendMessageService sendMessageService;
    private final CheckUpdateOnPost checkUpdateOnPost;

    private static final String ADDORDER_MESSAGE = "Введите номер заказа на перемещение: ";
    public AddOrderCommand(SendMessageService sendMessageService,CheckUpdateOnPost checkUpdateOnPost) {
        this.sendMessageService = sendMessageService;
        this.checkUpdateOnPost = checkUpdateOnPost;
    }

    @Override
    public void execute(Update update) {
        checkUpdateOnPost.setLastMessageAddOrder(true);
        sendMessageService.sendMessage(update.getMessage().getChatId().toString(),ADDORDER_MESSAGE);
    }
}
