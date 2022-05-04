package com.github.iridatelegrambot.command.CallbackCommand;

import com.github.iridatelegrambot.entity.Order;
import com.github.iridatelegrambot.service.OrderService;
import com.github.iridatelegrambot.service.send.SendMessageOrderMenuService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.objects.CallbackQuery;

import java.util.Optional;
@Component
public class ShowActiveOrdersCallbackCommand implements CallbackCommand {

    private final SendMessageOrderMenuService sendMessageService;
    private final OrderService orderService;
    private final CallbackCommandName commandName = CallbackCommandName.SHOW_ORDER;

    @Autowired
    public ShowActiveOrdersCallbackCommand(SendMessageOrderMenuService sendMessageService, OrderService orderService) {
        this.sendMessageService = sendMessageService;
        this.orderService = orderService;
    }

    @Override
    public void execute(CallbackQuery callbackQuery) {
        Long chatId = callbackQuery.getMessage().getChatId();
        String[] date = callbackQuery.getData().split(":");
        Optional<Order> orderOptional = orderService.getOrderById(Integer.parseInt(date[2]));

        if(orderOptional.isEmpty()){
            sendMessageService.sendMessage(chatId.toString(),"Заказ не найден.");
            return;
        }
        Order order = orderOptional.get();
        sendMessageService.sendMenuOrder(chatId,order);
    }

    @Override
    public String getNameCommand() {
        return commandName.getName();
    }
}
