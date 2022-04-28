package com.github.iridatelegrambot.command.CallbackCommand;

import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.objects.CallbackQuery;
import java.util.*;

@Component
public class CallbackCommandContainer {

    private final Map<String, CallbackCommand> callbackMap = new HashMap<>();

    public void setCallbackCommand(String callbackCommandName, CallbackCommand callbackCommand){
        callbackMap.put(callbackCommandName,callbackCommand);
    }

    public CallbackCommand findAnswer(CallbackQuery callbackQuery) {
        String[] data = callbackQuery.getData().split(":");
        String prefix = data[0];
        return callbackMap.get(prefix);
    }
}
