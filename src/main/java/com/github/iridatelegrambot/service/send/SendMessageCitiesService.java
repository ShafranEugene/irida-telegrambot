package com.github.iridatelegrambot.service.send;

import com.github.iridatelegrambot.entity.Invoice;
import com.github.iridatelegrambot.entity.Order;

import java.util.Optional;

public interface SendMessageCitiesService extends SendMessageService {

    void sendListCityForOrder(Optional<Order> orderOptional, Long chatId);

    void sendListCityForInvoice(Optional<Invoice> invoiceOptional, Long chatId);
}
