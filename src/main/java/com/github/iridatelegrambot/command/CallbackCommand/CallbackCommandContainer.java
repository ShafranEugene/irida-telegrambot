package com.github.iridatelegrambot.command.CallbackCommand;

import com.github.iridatelegrambot.service.InvoiceService;
import com.github.iridatelegrambot.service.OrderService;
import com.github.iridatelegrambot.service.SendMessageService;
import com.github.iridatelegrambot.service.buttons.InlineKeyboardService;
import com.google.common.collect.ImmutableMap;
import org.telegram.telegrambots.meta.api.objects.CallbackQuery;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.InlineKeyboardMarkup;

public class CallbackCommandContainer {

    private final ImmutableMap<String, CallbackCommand> callbackMap;

    public CallbackCommandContainer(SendMessageService sendMessageService, OrderService orderService,
                                    InvoiceService invoiceService, InlineKeyboardService inlineKeyboardService){
        callbackMap = ImmutableMap.<String,CallbackCommand>builder()
                .put(CallbackCommandName.ADD_ORDER.getName(),new AddOrderCallbackCommand(sendMessageService,orderService))
                .put(CallbackCommandName.ADD_INVOICE.getName(),new AddInvoiceCallbackCommand(sendMessageService,invoiceService, inlineKeyboardService))
        .build();
    }


    public CallbackCommand findAnswer(CallbackQuery callbackQuery) {
        String[] data = callbackQuery.getData().split(":");
        String prefix = data[0];
        return callbackMap.get(prefix);
    }
}
