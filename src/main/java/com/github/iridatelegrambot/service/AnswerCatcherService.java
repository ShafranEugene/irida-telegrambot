package com.github.iridatelegrambot.service;

import org.telegram.telegrambots.meta.api.objects.Update;

public interface AnswerCatcherService {

    void answerByOrder(Update update);

    void answerByInvoice(Update update);
}
