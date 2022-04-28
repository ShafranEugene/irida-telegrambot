package com.github.iridatelegrambot.command.CallbackCommand;

import com.github.iridatelegrambot.entity.Order;
import com.github.iridatelegrambot.service.OrderService;
import com.github.iridatelegrambot.service.send.SendMessageMainMenuService;
import com.github.iridatelegrambot.service.send.SendMessageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.objects.CallbackQuery;
@Component
public class CloseOrderCallbackCommand implements CallbackCommand {
    private final OrderService orderService;
    private final SendMessageMainMenuService sendMessageService;
    private final CallbackCommandName commandName = CallbackCommandName.CLOSE_ORDER;

    @Autowired
    public CloseOrderCallbackCommand(OrderService orderService, SendMessageMainMenuService sendMessageService) {
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

    @Override
    public String getNameCommand() {
        return commandName.getName();
    }
}
