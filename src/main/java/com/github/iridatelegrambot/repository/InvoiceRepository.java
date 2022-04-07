package com.github.iridatelegrambot.repository;

import com.github.iridatelegrambot.entity.Invoice;
import org.springframework.data.jpa.repository.JpaRepository;


public interface InvoiceRepository extends JpaRepository<Invoice,Integer> {

}
