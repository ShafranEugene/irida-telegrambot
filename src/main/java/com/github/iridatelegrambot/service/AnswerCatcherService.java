package com.github.iridatelegrambot.service;

import com.github.iridatelegrambot.entity.Invoice;
import com.github.iridatelegrambot.entity.Order;
import org.telegram.telegrambots.meta.api.objects.Update;

import java.util.Optional;

public interface AnswerCatcherService {

    Optional<Order> answerByAddOrder(Update update);

    Optional<Invoice> answerByAddInvoice(Update update);

    String deleteOrder(Update update);

    String deleteInvoice(Update update);

    String infoOrder(Update update);

    String infoInvoice(Update update);
}
