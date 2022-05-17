package com.github.iridatelegrambot.service;

import com.github.iridatelegrambot.entity.Invoice;

import java.util.List;
import java.util.Optional;

public interface InvoiceService {

    void save(Invoice invoice);

    Optional<Invoice> getInvoiceById(int Id);

    List<Invoice> getAllInvoice();

    void delete(int id);
}
