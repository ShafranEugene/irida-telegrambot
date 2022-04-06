package com.github.iridatelegrambot.command.CallbackCommand;

import com.github.iridatelegrambot.command.Command;
import com.github.iridatelegrambot.service.OrderService;
import com.github.iridatelegrambot.service.SendMessageService;
import com.google.common.collect.ImmutableMap;
import org.telegram.telegrambots.meta.api.objects.CallbackQuery;

public class CallbackCommandContainer {

    private final ImmutableMap<String, CallbackCommand> callbackMap;

    public CallbackCommandContainer(SendMessageService sendMessageService, OrderService orderService){
        callbackMap = ImmutableMap.<String,CallbackCommand>builder()
                .put(CallbackCommandName.ADD_ORDER.getName(),new AddOrderCallbackCommand(sendMessageService,orderService))
        .build();
    }


    public CallbackCommand findAnswer(CallbackQuery callbackQuery) {
        String[] data = callbackQuery.getData().split(":");
        String prefix = data[0];
        return callbackMap.get(prefix);
    }
}
