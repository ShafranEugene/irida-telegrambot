package com.github.iridatelegrambot.test.repository;

import com.github.iridatelegrambot.entity.Invoice;
import com.github.iridatelegrambot.entity.Order;
import com.github.iridatelegrambot.repository.InvoiceRepository;
import com.github.iridatelegrambot.service.InvoiceService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.jdbc.Sql;

import java.util.List;

@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
public class InvoiceRepositoryIT {
    @Autowired
    private InvoiceRepository invoiceRepository;

    @Sql(scripts = {"/sql/delete_table.sql"})
    @Test
    void shouldProperlySaveOrder(){
        //given
        Invoice invoice = new Invoice();
        invoice.setNumber("500");
        invoiceRepository.save(invoice);
        //when
        List<Invoice> invoices = invoiceRepository.findAll();
        Invoice invoiceOfDatabase = invoices.get(0);
        //then
        Assertions.assertEquals(invoice,invoiceOfDatabase);
    }

    @Sql(scripts = {"/sql/delete_table.sql","/sql/invoice.sql"})
    @Test
    void shouldProperlyFindAllInvoice(){
        //when
        List<Invoice> invoices = invoiceRepository.findAll();

        //then
        Assertions.assertEquals(4,invoices.size());
    }
}
