package com.github.iridatelegrambot.command;

import com.github.iridatelegrambot.service.OrderService;
import com.github.iridatelegrambot.service.SendMessageService;
import org.telegram.telegrambots.meta.api.objects.Update;

public class ShowAllOrderCommand implements Command{
    private final SendMessageService sendMessageService;
    private final OrderService orderService;

    public ShowAllOrderCommand(SendMessageService sendMessageService, OrderService orderService) {
        this.sendMessageService = sendMessageService;
        this.orderService = orderService;
    }

    @Override
    public void execute(Update update) {
        
    }
}
