package com.github.iridatelegrambot.service;

import com.github.iridatelegrambot.bot.CheckUpdateOnPost;
import com.github.iridatelegrambot.entity.Invoice;
import com.github.iridatelegrambot.entity.Order;
import com.github.iridatelegrambot.service.buttons.InlineKeyboardService;
import com.github.iridatelegrambot.service.buttons.InlineKeyboardServiceImpl;
import org.springframework.stereotype.Service;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.InlineKeyboardMarkup;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

@Service
public class AnswerCatcherServiceImpl implements AnswerCatcherService{

    private final SendMessageService sendMessageService;
    private final OrderService orderService;
    private final InvoiceService invoiceService;
    private final CheckUpdateOnPost checkUpdateOnPost;
    private final InlineKeyboardService inlineKeyboardService;


    public AnswerCatcherServiceImpl(SendMessageService sendMessageService, OrderService orderService,
                                    CheckUpdateOnPost checkUpdateOnPost, InvoiceService invoiceService,
                                    InlineKeyboardService inlineKeyboardService) {
        this.sendMessageService = sendMessageService;
        this.orderService = orderService;
        this.invoiceService = invoiceService;
        this.checkUpdateOnPost = checkUpdateOnPost;
        this.inlineKeyboardService = inlineKeyboardService;
    }

    @Override
    public void answerByOrder(Update update) {

        String numberOrder = update.getMessage().getText();
        Long chatId = update.getMessage().getChatId();

        if(numberOrder.replaceAll("[^0-9]","").isBlank()){
            sendMessageService.sendMessage(chatId.toString(),"Сообщение не содержит номера!\nПовторите попытку.");
            return;
        }

        if(checkIdentityOrder(numberOrder)){
            sendMessageService.sendMessage(chatId.toString(),"Такой номер уже есть в базе!\nПовторите попытку.");
            return;
        }
        Order order = new Order();

        order.setNumber(numberOrder);
        order.setIdUser(chatId);
        order.setStatusActive(true);

        Date date = new Date();
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        order.setDate(simpleDateFormat.format(date));

        orderService.save(order);

        InlineKeyboardMarkup inlineKeyboardMarkup = inlineKeyboardService.cityButtons(order);

        sendMessageService.sendMessage(chatId.toString(),"Введите город который сделал заказ:",inlineKeyboardMarkup);

        checkUpdateOnPost.setStatusOrder(chatId,false);
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
    public void answerByInvoice(Update update){
        String[] textUpdate = update.getMessage().getText().split(";");
        String numberInvoice = textUpdate[0];
        Long chatId = update.getMessage().getChatId();

        if(numberInvoice.replaceAll("[^0-9]","").isBlank()){
            sendMessageService.sendMessage(chatId.toString(),"Сообщение не содержит номера!\nПовторите попытку.");
            return;
        }

        if(checkIdentityInvoice(numberInvoice)){
            sendMessageService.sendMessage(chatId.toString(),"Такой номер уже есть в базе!\nПовторите попытку.");
            return;
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

        InlineKeyboardMarkup inlineKeyboardMarkup = inlineKeyboardService.cityButtons(invoice);

        sendMessageService.sendMessage(chatId.toString(),"Введите город из которого была накладная:",inlineKeyboardMarkup);

        checkUpdateOnPost.setStatusInvoice(chatId,false);



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
