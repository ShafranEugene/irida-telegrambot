package com.github.iridatelegrambot.command.CallbackCommand;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.iridatelegrambot.entity.Order;
import com.github.iridatelegrambot.service.OrderService;
import com.github.iridatelegrambot.service.send.SendMessageMainMenuService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.objects.CallbackQuery;

import java.util.Optional;

@Component
public class AddOrderCallbackCommand implements CallbackCommand{

    private final SendMessageMainMenuService sendMessageService;
    private final OrderService orderService;
    private final CallbackCommandName commandName = CallbackCommandName.ADD_ORDER;

    @Autowired
    public AddOrderCallbackCommand(SendMessageMainMenuService sendMessageService, OrderService orderService) {
        this.sendMessageService = sendMessageService;
        this.orderService = orderService;
    }

    @Override
    public void execute(CallbackQuery callbackQuery) {
        Long chatId = callbackQuery.getMessage().getChatId();
        String query = callbackQuery.getData();
        String JSONData = query.substring(query.indexOf('{'));

        ObjectMapper objectMapper = new ObjectMapper();
        int id = Integer.parseInt(JSONData.replaceAll("[^0-9]",""));

        if(orderService.getOrderById(id).isEmpty()){
            sendMessageService.sendMessage(chatId.toString(),"Заказ не найден.");
            return;
        }

        try {
            Optional<Order> orderOptional = orderService.getOrderById(id);
            Order order = orderOptional.get();
            Order orderJson = objectMapper.readValue(JSONData,Order.class);
            order.setCity(orderJson.getCity());
            orderService.save(order);
            sendMessageService.deleteMessage(chatId,callbackQuery.getMessage().getMessageId());
            sendMessageService.sendMainMenu(chatId,"Готово!");
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }


    }

    @Override
    public String getNameCommand() {
        return commandName.getName();
    }
}
