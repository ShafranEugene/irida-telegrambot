package com.github.iridatelegrambot.command;

import com.github.iridatelegrambot.service.send.SendMessageOrderMenuService;
import com.github.iridatelegrambot.service.send.SendMessageService;
import org.telegram.telegrambots.meta.api.objects.Update;

public class ShowActiveOrdersCommand implements Command {
    private final SendMessageOrderMenuService sendMessageService;
    public static final String SHOW_ACTIVE_ORDERS_MESSAGE = "Все активные заказы:";

    public ShowActiveOrdersCommand(SendMessageOrderMenuService sendMessageService) {
        this.sendMessageService = sendMessageService;
    }

    @Override
    public void execute(Update update) {
        Long chatId = update.getMessage().getChatId();
        sendMessageService.sendActiveOrders(chatId,SHOW_ACTIVE_ORDERS_MESSAGE);
    }
}
