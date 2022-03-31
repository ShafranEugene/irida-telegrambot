package com.github.iridatelegrambot.service;

import com.github.iridatelegrambot.bot.CheckUpdateOnPost;
import com.github.iridatelegrambot.entity.Order;
import org.springframework.stereotype.Service;
import org.telegram.telegrambots.meta.api.objects.Update;
@Service
public class AnswerCatcherServiceImpl implements AnswerCatcherService{

    private final SendMessageService sendMessageService;
    private final OrderService orderService;
    private final CheckUpdateOnPost checkUpdateOnPost;

    public AnswerCatcherServiceImpl(SendMessageService sendMessageService, OrderService orderService, CheckUpdateOnPost checkUpdateOnPost) {
        this.sendMessageService = sendMessageService;
        this.orderService = orderService;
        this.checkUpdateOnPost = checkUpdateOnPost;
    }

    @Override
    public void answerByOrder(Update update) {

        String numberOrder = update.getMessage().getText();
        Long chatId = update.getMessage().getChatId();

        if(numberOrder.replaceAll("[^0-9]","").isBlank()){
            sendMessageService.sendMessage(chatId.toString(),"Сообщение не содержит номера!\nПовторите попытку.");
            return;
        }
        Order order = new Order();

        order.setNumber(numberOrder);
        order.setIdUser(chatId);
        order.setStatusActive(true);

        orderService.save(order);

        sendMessageService.sendMessage(chatId.toString(),"Готово! \nВведите город который сделал заказ:");
        checkUpdateOnPost.setLastMessageAddOrder(false);
    }
}
