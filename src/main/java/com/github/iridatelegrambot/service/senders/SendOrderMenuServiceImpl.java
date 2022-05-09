package com.github.iridatelegrambot.service.senders;

import com.github.iridatelegrambot.entity.Order;
import com.github.iridatelegrambot.service.buttons.InlineDocumentButtonService;
import com.github.iridatelegrambot.service.buttons.InlineMenuButtonService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.InlineKeyboardMarkup;

@Service
public class SendOrderMenuServiceImpl implements SendOrderMenuService {

    private final SendMessageService sendMessageService;
    private final InlineMenuButtonService inlineKeyboardService;
    private final InlineDocumentButtonService inlineDocumentButtonService;
    @Autowired
    public SendOrderMenuServiceImpl(SendMessageService sendMessageService, InlineMenuButtonService inlineKeyboardService,
                                    InlineDocumentButtonService inlineDocumentButtonService) {
        this.sendMessageService = sendMessageService;
        this.inlineKeyboardService = inlineKeyboardService;
        this.inlineDocumentButtonService = inlineDocumentButtonService;
    }

    @Override
    public void sendActiveOrders(Long chatId, String message){
        sendMessageService.sendMessage(chatId.toString(),message,inlineDocumentButtonService.showActiveOrders());
    }

    @Override
    public void sendMenuOrder(Long chatId,Order order){
        InlineKeyboardMarkup markup = inlineKeyboardService.showMenuOrder(order.getId());
        sendMessageService.sendMessage(chatId.toString(),order.toStringForUsers(),markup);
    }
}
