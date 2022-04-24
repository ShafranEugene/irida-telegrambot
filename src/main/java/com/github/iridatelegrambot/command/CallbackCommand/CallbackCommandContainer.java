package com.github.iridatelegrambot.command.CallbackCommand;

import com.github.iridatelegrambot.service.InvoiceService;
import com.github.iridatelegrambot.service.OrderService;
import com.github.iridatelegrambot.service.SendMessageService;
import com.google.common.collect.ImmutableMap;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.objects.CallbackQuery;
@Component
public class CallbackCommandContainer {

    private final ImmutableMap<String, CallbackCommand> callbackMap;
    @Autowired
    public CallbackCommandContainer(SendMessageService sendMessageService, OrderService orderService,
                                    InvoiceService invoiceService){
        callbackMap = ImmutableMap.<String,CallbackCommand>builder()
                .put(CallbackCommandName.ADD_ORDER.getName(),new AddOrderCallbackCommand(sendMessageService,orderService))
                .put(CallbackCommandName.ADD_INVOICE.getName(),new AddInvoiceCallbackCommand(sendMessageService,invoiceService))
                .put(CallbackCommandName.ADD_ORDER_TO_INVOICE.getName(),new AddOrderToInvoiceCallbackCommand(sendMessageService,invoiceService,orderService))
                .put(CallbackCommandName.CLOSE_ORDER.getName(),new CloseOrderCallbackCommand(orderService,sendMessageService))
                .put(CallbackCommandName.CANCEL.getName(), new CancelCallbackCommand(orderService,invoiceService,sendMessageService))
                .put(CallbackCommandName.SHOW_ORDER.getName(),new ShowActiveOrdersCallbackCommand(sendMessageService,orderService))
                .put(CallbackCommandName.ORDER_MENU.getName(),new OrderMenuCallbackCommand(orderService,sendMessageService))
                .put(CallbackCommandName.STAT_MENU.getName(),new StatMenuCallbackCommand(sendMessageService, orderService, invoiceService))
        .build();
    }




    public CallbackCommand findAnswer(CallbackQuery callbackQuery) {
        String[] data = callbackQuery.getData().split(":");
        String prefix = data[0];
        return callbackMap.get(prefix);
    }
}
