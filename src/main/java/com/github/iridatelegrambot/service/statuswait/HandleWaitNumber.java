package com.github.iridatelegrambot.service.statuswait;

import org.telegram.telegrambots.meta.api.objects.Update;

public interface HandleWaitNumber {


    void handle(Update update);

    void addDocument(Update update);

    void infoDocument(Update update);

    void deleteDocument(Update update);
}
