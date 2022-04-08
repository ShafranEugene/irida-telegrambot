package com.github.iridatelegrambot.service;

import com.github.iridatelegrambot.bot.CheckUpdateOnPost;
import com.github.iridatelegrambot.entity.Order;
import com.github.iridatelegrambot.service.buttons.InlineKeyboardService;
import com.github.iridatelegrambot.service.buttons.InlineKeyboardServiceImpl;
import org.springframework.stereotype.Service;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.InlineKeyboardMarkup;

import java.util.List;

@Service
public class AnswerCatcherServiceImpl implements AnswerCatcherService{

    private final SendMessageService sendMessageService;
    private final OrderService orderService;
    private final CheckUpdateOnPost checkUpdateOnPost;
    private final InlineKeyboardService inlineKeyboardService;

    public AnswerCatcherServiceImpl(SendMessageService sendMessageService, OrderService orderService, CheckUpdateOnPost checkUpdateOnPost) {
        this.sendMessageService = sendMessageService;
        this.orderService = orderService;
        this.checkUpdateOnPost = checkUpdateOnPost;
        inlineKeyboardService = new InlineKeyboardServiceImpl();
    }

    @Override
    public void answerByOrder(Update update) {

        String numberOrder = update.getMessage().getText();
        Long chatId = update.getMessage().getChatId();

        if(numberOrder.replaceAll("[^0-9]","").isBlank()){
            sendMessageService.sendMessage(chatId.toString(),"Сообщение не содержит номера!\nПовторите попытку.");
            return;
        }

        if(checkIdentityOrder(numberOrder)){
            sendMessageService.sendMessage(chatId.toString(),"Такой номер уже есть в базе!\nПовторите попытку.");
            return;
        }
        Order order = new Order();

        order.setNumber(numberOrder);
        order.setIdUser(chatId);
        order.setStatusActive(true);

        orderService.save(order);

        InlineKeyboardMarkup inlineKeyboardMarkup = inlineKeyboardService.cityButtons(order);

        sendMessageService.sendMessage(chatId.toString(),"Готово! \nВведите город который сделал заказ:",inlineKeyboardMarkup);

//        sendMessageService.sendMessage(chatId.toString(),"Готово! \nВведите город который сделал заказ:");


//        checkUpdateOnPost.setLastMessageAddOrder(false);
    }


    private boolean checkIdentityOrder(String numberOrder){
        final boolean[] DBhasThisNumber = {false};
        List<Order> orders = orderService.getAllOrdersActive();
        orders.forEach(order -> {
            if(order.getNumber().equals(numberOrder)){
                DBhasThisNumber[0] = true;
            }
        });
        return DBhasThisNumber[0];
    }
}
