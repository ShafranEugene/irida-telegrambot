package com.github.iridatelegrambot.command.CallbackCommand;

import org.springframework.beans.factory.annotation.Autowired;
import org.telegram.telegrambots.meta.api.objects.CallbackQuery;

public interface CallbackCommand {
    void execute(CallbackQuery callbackQuery);
    String getNameCommand();
    @Autowired
    default void containerFiller(CallbackCommandContainer callbackCommandContainer){
        callbackCommandContainer.setCallbackCommand(getNameCommand(),this);
    }
}
