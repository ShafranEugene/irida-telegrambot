package com.github.iridatelegrambot.command.CallbackCommand;

import com.github.iridatelegrambot.entity.Order;
import com.github.iridatelegrambot.service.OrderService;
import com.github.iridatelegrambot.service.SendMessageService;
import org.telegram.telegrambots.meta.api.objects.CallbackQuery;

public class CloseOrderCallbackCommand implements CallbackCommand {
    private final OrderService orderService;
    private final SendMessageService sendMessageService;

    public CloseOrderCallbackCommand(OrderService orderService, SendMessageService sendMessageService) {
        this.orderService = orderService;
        this.sendMessageService = sendMessageService;
    }

    @Override
    public void execute(CallbackQuery callbackQuery) {
        String query = callbackQuery.getData();
        String[] text = query.split(":");

        int idOrder = Integer.parseInt(text[1]);
        boolean status = Boolean.parseBoolean(text[2]);

        Order order = orderService.getOrderById(idOrder).get();
        order.setStatusActive(status);

        orderService.save(order);

        sendMessageService.sendMainMenu(callbackQuery.getMessage().getChatId(),"Готово");
    }
}
