package com.github.iridatelegrambot.command.CallbackCommand;

import com.github.iridatelegrambot.entity.Order;
import com.github.iridatelegrambot.service.OrderService;
import com.github.iridatelegrambot.service.senders.CommandCallbackSenderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.objects.CallbackQuery;

import java.util.Optional;

@Component
public class CloseOrderCallbackCommand implements CallbackCommand {
    private final OrderService orderService;
    private final CommandCallbackSenderService sendMessageService;
    private final CallbackCommandName commandName = CallbackCommandName.CLOSE_ORDER;

    @Autowired
    public CloseOrderCallbackCommand(OrderService orderService, CommandCallbackSenderService sendMessageService) {
        this.orderService = orderService;
        this.sendMessageService = sendMessageService;
    }

    @Override
    public void execute(CallbackQuery callbackQuery) {
        Long chatId = callbackQuery.getMessage().getChatId();
        Integer messageId = callbackQuery.getMessage().getMessageId();
        String query = callbackQuery.getData();
        String[] text = query.split(":");

        int idOrder = Integer.parseInt(text[1]);
        boolean status = Boolean.parseBoolean(text[2]);

        Optional<Order> orderOptional = orderService.getOrderById(idOrder);

        if(orderOptional.isEmpty()){
            sendMessageService.sendMessage(chatId.toString(),"Заказ не найден.");
            return;
        }

        Order order = orderOptional.get();
        order.setStatusActive(status);

        orderService.save(order);
        sendMessageService.deleteMessage(chatId,messageId);
        sendMessageService.sendMainMenu(chatId,"Готово");
    }

    @Override
    public String getNameCommand() {
        return commandName.getName();
    }
}
