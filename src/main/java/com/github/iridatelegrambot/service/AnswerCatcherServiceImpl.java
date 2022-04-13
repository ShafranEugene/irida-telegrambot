package com.github.iridatelegrambot.service;

import com.github.iridatelegrambot.entity.Invoice;
import com.github.iridatelegrambot.entity.Order;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.telegram.telegrambots.meta.api.objects.Update;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Optional;

@Service
public class AnswerCatcherServiceImpl implements AnswerCatcherService{

    private final OrderService orderService;
    private final InvoiceService invoiceService;

    @Autowired
    public AnswerCatcherServiceImpl(OrderService orderService,InvoiceService invoiceService) {
        this.orderService = orderService;
        this.invoiceService = invoiceService;
    }

    @Override
    public Optional<Order> answerByOrder(Update update) {
        String numberOrder = update.getMessage().getText();
        Long chatId = update.getMessage().getChatId();

        if(numberOrder.replaceAll("[^0-9]","").isBlank()){
            return Optional.empty();
        }
        if(checkIdentityOrder(numberOrder)){
            return Optional.empty();
        }

        Order order = new Order();
        order.setNumber(numberOrder);
        order.setIdUser(chatId);
        order.setStatusActive(true);

        Date date = new Date();
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        order.setDate(simpleDateFormat.format(date));
        orderService.save(order);

        return Optional.of(order);
    }


    private boolean checkIdentityOrder(String numberOrder){
        final boolean[] DBhasThisNumber = {false};
        List<Order> orders = orderService.getAllOrdersActive();
        orders.forEach(order -> {
            if(order.getNumber().equals(numberOrder)){
                DBhasThisNumber[0] = true;
            }
        });
        return DBhasThisNumber[0];
    }

    @Override
    public Optional<Invoice> answerByInvoice(Update update){
        String[] textUpdate = update.getMessage().getText().split(";");
        String numberInvoice = textUpdate[0];
        Long chatId = update.getMessage().getChatId();

        if(numberInvoice.replaceAll("[^0-9]","").isBlank()){
            return Optional.empty();
        }
        if(checkIdentityInvoice(numberInvoice)){
            return Optional.empty();
        }

        Invoice invoice = new Invoice();
        invoice.setNumber(numberInvoice);
        invoice.setIdUser(chatId);
        if(textUpdate.length>1){
            String comment = textUpdate[1];
            invoice.setComment(comment);
        }

        Date date = new Date();
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        invoice.setDate(simpleDateFormat.format(date));
        invoiceService.save(invoice);

        return Optional.of(invoice);
    }

    private boolean checkIdentityInvoice(String numberInvoice){
        final boolean[] DBhasInvoice = {false};
        List<Invoice> invoices = invoiceService.getAllInvoice();
        invoices.forEach(invoice -> {
            if(invoice.getNumber().equals(numberInvoice)){
                DBhasInvoice[0] = true;
            }
        });
        return DBhasInvoice[0];
    }
}
