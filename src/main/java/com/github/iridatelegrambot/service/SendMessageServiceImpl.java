package com.github.iridatelegrambot.service;

import com.github.iridatelegrambot.bot.CheckUpdateOnPost;
import com.github.iridatelegrambot.bot.IridaBot;
import com.github.iridatelegrambot.entity.Invoice;
import com.github.iridatelegrambot.entity.Order;
import com.github.iridatelegrambot.service.buttons.InlineKeyboardService;
import com.github.iridatelegrambot.service.buttons.MenuButtonsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.InlineKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.ReplyKeyboard;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.ReplyKeyboardMarkup;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

import java.util.Optional;

import static org.apache.commons.lang3.StringUtils.isBlank;

@Service
public class SendMessageServiceImpl implements SendMessageService {

    private final IridaBot iridaBot;
    private final InlineKeyboardService inlineKeyboardService;
    private final CheckUpdateOnPost checkUpdateOnPost;
    private final MenuButtonsService menuButtonsService;

    @Autowired
    public SendMessageServiceImpl(IridaBot iridaBot,InlineKeyboardService inlineKeyboardService,
                                  CheckUpdateOnPost checkUpdateOnPost, MenuButtonsService menuButtonsService) {
        this.iridaBot = iridaBot;
        this.inlineKeyboardService = inlineKeyboardService;
        this.checkUpdateOnPost = checkUpdateOnPost;
        this.menuButtonsService = menuButtonsService;
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
    public void sendMessage(String chatId, String message, ReplyKeyboard replyKeyboard) {
        if(isBlank(message)) return;

        SendMessage sendMessage = new SendMessage();
        sendMessage.setText(message);
        sendMessage.setChatId(chatId);
        sendMessage.setReplyMarkup(replyKeyboard);
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
    @Override
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

    @Override
    public void sendMainMenu(Long chatId, String message){
        ReplyKeyboardMarkup markup = menuButtonsService.mainMenu();
        sendMessage(chatId.toString(),message,markup);
    }

    @Override
    public void sendActiveOrdersForInvoice(Long chatId, String message, Invoice invoice){
        InlineKeyboardMarkup inlineKeyboardMarkup = inlineKeyboardService.markupActiveOrdersForInvoice(invoice);
        sendMessage(chatId.toString(),message,inlineKeyboardMarkup);
    }

    @Override
    public void sendMessageCloseOrder(Long chatId, String message, Order order){
        InlineKeyboardMarkup inlineKeyboardMarkup = inlineKeyboardService.closeOrder(order);
        sendMessage(chatId.toString(),message, inlineKeyboardMarkup);
    }

    @Override
    public void sendActiveOrders(Long chatId, String message){
        sendMessage(chatId.toString(),message,inlineKeyboardService.showActiveOrders());
    }
}
