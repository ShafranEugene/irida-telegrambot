package com.github.iridatelegrambot.service;

import com.github.iridatelegrambot.entity.Invoice;
import com.github.iridatelegrambot.repository.InvoiceRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
@Service
public class InvoiceServiceImpl implements InvoiceService {

    private final InvoiceRepository invoiceRepository;
    @Autowired
    public InvoiceServiceImpl(InvoiceRepository invoiceRepository) {
        this.invoiceRepository = invoiceRepository;
    }

    @Override
    public void save(Invoice invoice) {
        invoiceRepository.save(invoice);
    }

    @Override
    public Optional<Invoice> getInvoiceById(int id) {
        return invoiceRepository.findById(id);
    }

    @Override
    public List<Invoice> getAllInvoice(){
        return invoiceRepository.findAll();
    }

    @Override
    public void detele(int id) {
        Invoice invoice = getInvoiceById(id).get();
        invoiceRepository.delete(invoice);
    }
}
