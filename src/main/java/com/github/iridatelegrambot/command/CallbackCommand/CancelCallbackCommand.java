package com.github.iridatelegrambot.command.CallbackCommand;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.iridatelegrambot.entity.Invoice;
import com.github.iridatelegrambot.entity.Order;
import com.github.iridatelegrambot.service.InvoiceService;
import com.github.iridatelegrambot.service.OrderService;
import com.github.iridatelegrambot.service.SendMessageService;
import org.telegram.telegrambots.meta.api.objects.CallbackQuery;

public class CancelCallbackCommand implements CallbackCommand{

    private final OrderService orderService;
    private final InvoiceService invoiceService;
    private final SendMessageService sendMessageService;

    public CancelCallbackCommand(OrderService orderService,InvoiceService invoiceService, SendMessageService sendMessageService){
        this.orderService = orderService;
        this.invoiceService = invoiceService;
        this.sendMessageService = sendMessageService;
    }

    @Override
    public void execute(CallbackQuery callbackQuery) {
        String[] data = callbackQuery.getData().split(":");
        String type = data[1];
        int id = Integer.parseInt(data[3]);
        Long chatId = callbackQuery.getMessage().getChatId();

        if(type.equals("order")){
            orderService.delete(id);
            sendMessageService.sendMainMenu(chatId,"Заказ был отменен.");
        } else if(type.equals("invoice")){
            invoiceService.detele(id);
            sendMessageService.sendMainMenu(chatId,"Накладная была отменена.");
        }

    }
}
