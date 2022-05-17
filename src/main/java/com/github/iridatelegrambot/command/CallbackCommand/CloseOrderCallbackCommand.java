package com.github.iridatelegrambot.command.CallbackCommand;

import com.github.iridatelegrambot.entity.Order;
import com.github.iridatelegrambot.service.OrderService;
import com.github.iridatelegrambot.service.senders.CommandCallbackSenderService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.objects.CallbackQuery;

import java.util.Optional;

@Component
public class CloseOrderCallbackCommand implements CallbackCommand {
    private final OrderService orderService;
    private final CommandCallbackSenderService sendMessageService;
    private final CallbackCommandName commandName = CallbackCommandName.CLOSE_ORDER;
    private final static Logger logger = LoggerFactory.getLogger(CloseOrderCallbackCommand.class);

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
        if(!status) {
            Optional<Order> orderOptional = orderService.getOrderById(idOrder);

            if (orderOptional.isEmpty()) {
                sendMessageService.sendMessage(chatId.toString(), "Заказ не найден.");
                logger.warn("User - " + callbackQuery.getMessage().getChat().getUserName() +
                        ", try close order: " + idOrder + " id. But not find order.");
                return;
            }

            Order order = orderOptional.get();
            order.setStatusActive(false);
            orderService.save(order);
            logger.info("User - " + callbackQuery.getMessage().getChat().getUserName() +
                    ", close order: " + order.getNumber());
        }

        sendMessageService.deleteMessage(chatId,messageId);
        sendMessageService.sendMainMenu(chatId,"Готово");
    }

    @Override
    public String getNameCommand() {
        return commandName.getName();
    }
}
