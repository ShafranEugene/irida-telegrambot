package com.github.iridatelegrambot.service.senders;

import com.github.iridatelegrambot.entity.Order;
import com.github.iridatelegrambot.service.buttons.InlineKeyboardService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.InlineKeyboardMarkup;

@Service
public class SendOrderMenuServiceImpl implements SendOrderMenuService {

    private final SendMessageService sendMessageService;
    private final InlineKeyboardService inlineKeyboardService;
    @Autowired
    public SendOrderMenuServiceImpl(SendMessageService sendMessageService, InlineKeyboardService inlineKeyboardService) {
        this.sendMessageService = sendMessageService;
        this.inlineKeyboardService = inlineKeyboardService;
    }

    @Override
    public void sendActiveOrders(Long chatId, String message){
        sendMessageService.sendMessage(chatId.toString(),message,inlineKeyboardService.showActiveOrders());
    }

    @Override
    public void sendMenuOrder(Long chatId,Order order){
        InlineKeyboardMarkup markup = inlineKeyboardService.showMenuOrder(order);
        sendMessageService.sendMessage(chatId.toString(),order.toStringForUsers(),markup);
    }
}
