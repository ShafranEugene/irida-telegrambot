package com.github.iridatelegrambot.command.CallbackCommand;

import org.telegram.telegrambots.meta.api.objects.CallbackQuery;

public interface CallbackCommand {

    void execute(CallbackQuery callbackQuery);
}
