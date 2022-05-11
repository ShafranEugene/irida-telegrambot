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
    private final UserTelegramService userTelegramService;

    @Autowired
    public AnswerCatcherServiceImpl(OrderService orderService,InvoiceService invoiceService, UserTelegramService userTelegramService) {
        this.orderService = orderService;
        this.invoiceService = invoiceService;
        this.userTelegramService = userTelegramService;
    }

    @Override
    public Optional<Order> addOrder(Update update) {
        String numberOrder = update.getMessage().getText();

        if(numberOrder.replaceAll("[^0-9]","").isBlank()){
            return Optional.empty();
        } else if(checkIdentityOrder(numberOrder)){
            return Optional.empty();
        }

        Order order = new Order();
        order.setNumber(numberOrder);
        order.setStatusActive(true);
        order.setUser(userTelegramService.findOrCreateUser(update));

        Date date = new Date();
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        order.setDate(simpleDateFormat.format(date));
        orderService.save(order);

        return Optional.of(order);
    }


    private boolean checkIdentityOrder(String numberOrder){
        final boolean[] DBhasThisNumber = {false};
        List<Order> orders = orderService.getAllOrders();
        orders.forEach(order -> {
            if(order.getNumber().equals(numberOrder)){
                DBhasThisNumber[0] = true;
            }
        });
        return DBhasThisNumber[0];
    }

    @Override
    public Optional<Invoice> addInvoice(Update update){
        String[] textUpdate = update.getMessage().getText().split(";");
        String numberInvoice = textUpdate[0];

        if(numberInvoice.replaceAll("[^0-9]","").isBlank()){
            return Optional.empty();
        }else if(checkIdentityInvoice(numberInvoice)){
            return Optional.empty();
        }

        Invoice invoice = new Invoice();
        invoice.setNumber(numberInvoice);
        invoice.setUser(userTelegramService.findOrCreateUser(update));
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
    @Override
    public String deleteOrder(Update update){
        String numberOrder = update.getMessage().getText();

        for(Order orderFor : orderService.getAllOrders()){
            if(numberOrder.equals(orderFor.getNumber())){
                List<Invoice> invoiceList = orderFor.getInvoiceList();
                for(Invoice invoice : invoiceList){
                    invoice.setOrder(null);
                    invoiceService.save(invoice);
                }
                orderService.delete(orderFor.getId());
                return "Заказ с номером \"" + orderFor.getNumber() + "\" был удален.";
            }
        }
        return "Повторите попытку. Заказ с таким номер не найден.";
    }
    @Override
    public String deleteInvoice(Update update){
        String numberInvoice = update.getMessage().getText();

        for(Invoice invoiceFor : invoiceService.getAllInvoice()){
            if(numberInvoice.equals(invoiceFor.getNumber())){
                invoiceService.detele(invoiceFor.getId());
                return "Накладная с номером \"" + invoiceFor.getNumber() + "\" была удалена.";
            }
        }
        return "Повторите попытку. Накладная с таким номер не найден.";
    }
    @Override
    public String infoOrder(Update update){
        String numberOrder = update.getMessage().getText();
        for(Order orderFor : orderService.getAllOrders()){
            if(numberOrder.equals(orderFor.getNumber())){
                return orderFor.toStringForUsers();
            }
        }
        return "Повторите попытку. Заказ с таким номер не найден.";
    }
    @Override
    public String infoInvoice(Update update){
        String numberInvoice = update.getMessage().getText();
        for(Invoice invoiceFor : invoiceService.getAllInvoice()){
            if(numberInvoice.equals(invoiceFor.getNumber())){
                return invoiceFor.toStringForUser();
            }
        }
        return "Повторите попытку. Накладная с таким номер не найден.";
    }
}
