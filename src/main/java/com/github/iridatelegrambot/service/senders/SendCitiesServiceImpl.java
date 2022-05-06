package com.github.iridatelegrambot.service.senders;

import com.github.iridatelegrambot.entity.Invoice;
import com.github.iridatelegrambot.entity.Order;
import com.github.iridatelegrambot.service.buttons.InlineKeyboardService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.InlineKeyboardMarkup;

import java.util.Optional;
@Service
public class SendCitiesServiceImpl implements SendCitiesService {
    private final SendMessageService sendMessageService;
    private final InlineKeyboardService inlineKeyboardService;

    @Autowired
    public SendCitiesServiceImpl(SendMessageService sendMessageService, InlineKeyboardService inlineKeyboardService) {
        this.sendMessageService = sendMessageService;
        this.inlineKeyboardService = inlineKeyboardService;
    }

    @Override
    public void sendListCityForOrder(Optional<Order> orderOptional, Long chatId){
        if(orderOptional.isEmpty()){
            sendMessageService.sendMessage(chatId.toString(),
                    "Повторите попытку.\nСообщение не содержит номера или такой номер уже есть.");
            return;
        }

        Order order = orderOptional.get();
        InlineKeyboardMarkup inlineKeyboardMarkup = inlineKeyboardService.cityButtons(order);
        sendMessageService.sendMessage(chatId.toString(),"Введите город который сделал заказ:",inlineKeyboardMarkup);
    }
    @Override
    public void sendListCityForInvoice(Optional<Invoice> invoiceOptional, Long chatId){
        if (invoiceOptional.isEmpty()){
            sendMessageService.sendMessage(chatId.toString(),
                    "Повторите попытку.\nСообщение не содержит номера или такой номер уже есть.");
            return;
        }

        Invoice invoice = invoiceOptional.get();
        InlineKeyboardMarkup inlineKeyboardMarkup = inlineKeyboardService.cityButtons(invoice);
        sendMessageService.sendMessage(chatId.toString(),"Введите город из которого была накладная:",inlineKeyboardMarkup);
    }
}
