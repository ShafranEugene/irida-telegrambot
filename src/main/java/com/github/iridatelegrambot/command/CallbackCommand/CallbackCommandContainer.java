package com.github.iridatelegrambot.command.CallbackCommand;

import com.github.iridatelegrambot.service.InvoiceService;
import com.github.iridatelegrambot.service.OrderService;
import com.github.iridatelegrambot.service.SendMessageService;
import com.github.iridatelegrambot.service.buttons.InlineKeyboardService;
import com.google.common.collect.ImmutableMap;
import org.telegram.telegrambots.meta.api.objects.CallbackQuery;

public class CallbackCommandContainer {

    private final ImmutableMap<String, CallbackCommand> callbackMap;

    public CallbackCommandContainer(SendMessageService sendMessageService, OrderService orderService,
                                    InvoiceService invoiceService, InlineKeyboardService inlineKeyboardService){
        callbackMap = ImmutableMap.<String,CallbackCommand>builder()
                .put(CallbackCommandName.ADD_ORDER.getName(),new AddOrderCallbackCommand(sendMessageService,orderService))
                .put(CallbackCommandName.ADD_INVOICE.getName(),new AddInvoiceCallbackCommand(sendMessageService,invoiceService, inlineKeyboardService))
                .put(CallbackCommandName.ADD_ORDER_TO_INVOICE.getName(),new AddOrderToInvoiceCallbackCommand(sendMessageService,invoiceService,orderService,inlineKeyboardService))
                .put(CallbackCommandName.CLOSE_ORDER.getName(),new CloseOrderCallbackCommand(orderService,sendMessageService))
                .put(CallbackCommandName.CANCEL.getName(), new CancelCallbackCommand(orderService,invoiceService,sendMessageService))
        .build();
    }


    public CallbackCommand findAnswer(CallbackQuery callbackQuery) {
        String[] data = callbackQuery.getData().split(":");
        String prefix = data[0];
        return callbackMap.get(prefix);
    }
}
