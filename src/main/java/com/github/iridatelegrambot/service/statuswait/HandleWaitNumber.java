package com.github.iridatelegrambot.service.statuswait;

import org.telegram.telegrambots.meta.api.objects.Update;

public interface HandleWaitNumber {

    void handle(Update update);
}
