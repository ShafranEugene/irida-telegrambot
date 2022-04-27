package com.github.iridatelegrambot.command.CallbackCommand;

import com.github.iridatelegrambot.service.InvoiceService;
import com.github.iridatelegrambot.service.OrderService;
import com.github.iridatelegrambot.service.send.SendMessageMainMenuService;
import com.github.iridatelegrambot.service.send.SendMessageService;
import org.telegram.telegrambots.meta.api.objects.CallbackQuery;

public class CancelCallbackCommand implements CallbackCommand{

    private final OrderService orderService;
    private final InvoiceService invoiceService;
    private final SendMessageMainMenuService sendMessageService;

    public CancelCallbackCommand(OrderService orderService,InvoiceService invoiceService, SendMessageMainMenuService sendMessageService){
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
