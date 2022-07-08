package com.github.iridatelegrambot.service;

import com.github.iridatelegrambot.entity.Invoice;
import com.github.iridatelegrambot.entity.Order;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;

@Component
public class CleanerOldDocument {

    private final OrderService orderService;
    private final InvoiceService invoiceService;
    private final static Logger logger = LoggerFactory.getLogger(CleanerOldDocument.class);
    @Autowired
    public CleanerOldDocument(OrderService orderService, InvoiceService invoiceService) {
        this.orderService = orderService;
        this.invoiceService = invoiceService;
    }

    @Scheduled(cron = "0 0 2 * * *")
    public void cleaning() throws ParseException {
        List<Invoice> allInvoice = invoiceService.getAllInvoice();
        List<Order> allOrders = orderService.getAllOrders();
        Calendar calendar = new GregorianCalendar();
        calendar.add(Calendar.WEEK_OF_MONTH, -1);
        DateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

        for(Invoice invoice : allInvoice){
            Date date = format.parse(invoice.getDate());
            Calendar calendarInv = Calendar.getInstance();
            calendarInv.setTime(date);
            if(calendar.after(calendarInv)){
                invoiceService.delete(invoice.getId());
                logger.info("Delete invoice with number:" + invoice.getNumber());
            }
        }

        for(Order order : allOrders){
            Date date = format.parse(order.getDate());
            Calendar calendarOrd = Calendar.getInstance();
            calendarOrd.setTime(date);
            if(calendar.after(calendarOrd)){
                orderService.delete(order.getId());
                logger.info("Delete invoice with number:" + order.getNumber());
            }
        }


    }
}
