package com.github.iridatelegrambot.service;

import com.github.iridatelegrambot.bot.CheckUpdateOnPost;
import com.github.iridatelegrambot.bot.IridaBot;
import com.github.iridatelegrambot.entity.Invoice;
import com.github.iridatelegrambot.entity.Order;
import com.github.iridatelegrambot.service.buttons.InlineKeyboardService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.InlineKeyboardMarkup;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

import java.util.Optional;

import static org.apache.commons.lang3.StringUtils.isBlank;

@Service
public class SendMessageServiceImpl implements SendMessageService {

    private final IridaBot iridaBot;
    private final InlineKeyboardService inlineKeyboardService;
    private final CheckUpdateOnPost checkUpdateOnPost;

    @Autowired
    public SendMessageServiceImpl(IridaBot iridaBot,InlineKeyboardService inlineKeyboardService,
                                  CheckUpdateOnPost checkUpdateOnPost) {
        this.iridaBot = iridaBot;
        this.inlineKeyboardService = inlineKeyboardService;
        this.checkUpdateOnPost = checkUpdateOnPost;
    }

    @Override
    public void sendMessage(String chatId, String message) {
        if(isBlank(message)) return;

        SendMessage sendMessage = new SendMessage();
        sendMessage.setText(message);
        sendMessage.setChatId(chatId);
        sendMessage.enableHtml(true);


        try {
            iridaBot.execute(sendMessage);
        } catch (TelegramApiException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void sendMessage(String chatId, String message, InlineKeyboardMarkup inlineKeyboardMarkup) {
        if(isBlank(message)) return;

        SendMessage sendMessage = new SendMessage();
        sendMessage.setText(message);
        sendMessage.setChatId(chatId);
        sendMessage.setReplyMarkup(inlineKeyboardMarkup);
        sendMessage.enableHtml(true);

        try {
            iridaBot.execute(sendMessage);
        } catch (TelegramApiException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void sendListCityForOrder(Optional<Order> orderOptional, Long chatId){
        if(orderOptional.isEmpty()){
            sendMessage(chatId.toString(),
                    "Повторите попытку.\nСообщение не содержит номера или такой номер уже есть.");
            return;
        }

        Order order = orderOptional.get();
        InlineKeyboardMarkup inlineKeyboardMarkup = inlineKeyboardService.cityButtons(order);
        sendMessage(chatId.toString(),"Введите город который сделал заказ:",inlineKeyboardMarkup);
        checkUpdateOnPost.setStatusOrder(chatId,false);
    }

    public void sendListCityForInvoice(Optional<Invoice> invoiceOptional, Long chatId){
        if (invoiceOptional.isEmpty()){
            sendMessage(chatId.toString(),
                    "Повторите попытку.\nСообщение не содержит номера или такой номер уже есть.");
            return;
        }

        Invoice invoice = invoiceOptional.get();
        InlineKeyboardMarkup inlineKeyboardMarkup = inlineKeyboardService.cityButtons(invoice);
        sendMessage(chatId.toString(),"Введите город из которого была накладная:",inlineKeyboardMarkup);
        checkUpdateOnPost.setStatusInvoice(chatId,false);
    }
}
