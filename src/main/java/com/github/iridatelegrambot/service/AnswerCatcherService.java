package com.github.iridatelegrambot.service;

import com.github.iridatelegrambot.entity.Invoice;
import com.github.iridatelegrambot.entity.Order;
import org.telegram.telegrambots.meta.api.objects.Update;

import java.util.Optional;

public interface AnswerCatcherService {

    Optional<Order> answerByOrder(Update update);

    Optional<Invoice> answerByInvoice(Update update);
}
