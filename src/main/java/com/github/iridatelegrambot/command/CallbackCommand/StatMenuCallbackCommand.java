package com.github.iridatelegrambot.command.CallbackCommand;

import com.github.iridatelegrambot.entity.Invoice;
import com.github.iridatelegrambot.entity.Order;
import com.github.iridatelegrambot.service.InvoiceService;
import com.github.iridatelegrambot.service.OrderService;
import com.github.iridatelegrambot.service.SendMessageService;
import com.github.iridatelegrambot.service.statuswait.WaitDocument;
import com.github.iridatelegrambot.service.statuswait.WaitTypeStatus;
import org.telegram.telegrambots.meta.api.objects.CallbackQuery;

import java.util.List;

import static com.github.iridatelegrambot.service.statuswait.WaitTypeStatus.DELETE;
import static com.github.iridatelegrambot.service.statuswait.WaitTypeStatus.INFO;

public class StatMenuCallbackCommand implements CallbackCommand {

    private final SendMessageService sendMessageService;
    private final OrderService orderService;
    private final InvoiceService invoiceService;

    public StatMenuCallbackCommand(SendMessageService sendMessageService, OrderService orderService, InvoiceService invoiceService) {
        this.sendMessageService = sendMessageService;
        this.orderService = orderService;
        this.invoiceService = invoiceService;
    }

    @Override
    public void execute(CallbackQuery callbackQuery) {
        Long chatId = callbackQuery.getMessage().getChatId();
        String[] data = callbackQuery.getData().split(":");
        String text = data[1];

        if(data.length>2){
            handleStatDetails(chatId,data);
        }

        if(text.equals("infoAllOrders")){
            sendInfoAllOrders(chatId);
        } else if(text.equals("infoAllInvoice")){
            sendInfoAllInvoice(chatId);
        }
    }

    private void sendInfoAllOrders(Long chatId){
        List<Order> orderList = orderService.getAllOrders();
        StringBuilder info = new StringBuilder("Список всех заказов:");
        for(Order order : orderList){
            info.append("\n\t- Номер - ").append(order.getNumber()).append("; Дата добавления - ").append(order.getDate()).append(";");
        }
        sendMessageService.sendMenuStatDetails(chatId,info.toString(),"order");
    }

    private void sendInfoAllInvoice(Long chatId){
        List<Invoice> invoices = invoiceService.getAllInvoice();
        StringBuilder info = new StringBuilder("Список всех накладных на перемещение:");
        for (Invoice invoice : invoices){
            info.append("\n\t- Номер - ").append(invoice.getNumber()).append("; Дата добавления - ").append(invoice.getDate()).append(";");
        }
        sendMessageService.sendMenuStatDetails(chatId,info.toString(),"invoice");
    }

    private void handleStatDetails(Long chatId, String[] data){
        String typeDocument = data[1];
        String subType = data[2];

        if(subType.equals(INFO.getName())){
            if(typeDocument.equals(WaitDocument.ORDER.getName())){
                WaitDocument.ORDER.setWaitNumber(chatId,true, INFO);
            } else if(typeDocument.equals(WaitDocument.INVOICE.getName())){
                WaitDocument.INVOICE.setWaitNumber(chatId,true, INFO);
            }
        } else if(subType.equals(DELETE.getName())){
            if(typeDocument.equals(WaitDocument.ORDER.getName())){
                WaitDocument.ORDER.setWaitNumber(chatId,true,DELETE);
            } else if(typeDocument.equals(WaitDocument.INVOICE.getName())){
                WaitDocument.INVOICE.setWaitNumber(chatId,true, DELETE);
            }
        }
        String message = "Введите номер:";
        sendMessageService.sendMessage(chatId.toString(),message);
    }
}
