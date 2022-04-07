package com.github.iridatelegrambot.service;

import com.github.iridatelegrambot.entity.Invoice;

import java.util.Optional;

public interface InvoiceService {

    void save(Invoice invoice);

    Optional<Invoice> getInvoiceById(int Id);
}
