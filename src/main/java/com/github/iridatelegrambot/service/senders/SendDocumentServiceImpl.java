package com.github.iridatelegrambot.service.senders;

import com.github.iridatelegrambot.entity.Invoice;
import com.github.iridatelegrambot.entity.Order;
import com.github.iridatelegrambot.service.InvoiceService;
import com.github.iridatelegrambot.service.OrderService;
import com.github.iridatelegrambot.service.buttons.InlineDocumentButtonService;
import com.github.iridatelegrambot.service.statuswait.WaitDocument;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.InlineKeyboardMarkup;

import java.util.Optional;
@Service
public class SendDocumentServiceImpl implements SendDocumentService {
    private final SendMessageService sendMessageService;
    private final InlineDocumentButtonService inlineKeyboardService;
    private final OrderService orderService;
    private final InvoiceService invoiceService;

    @Autowired
    public SendDocumentServiceImpl(SendMessageService sendMessageService, InlineDocumentButtonService inlineKeyboardService,
        OrderService orderService, InvoiceService invoiceService){
        this.sendMessageService = sendMessageService;
        this.inlineKeyboardService = inlineKeyboardService;
        this.orderService = orderService;
        this.invoiceService = invoiceService;
    }

    @Override
    public void sendListCityForOrder(Optional<Order> orderOptional, Long chatId){
        if(orderOptional.isEmpty()){
            sendMessageService.sendMessage(chatId.toString(),
                    "Повторите попытку.\nСообщение не содержит номера или такой номер уже есть.");
            return;
        }

        Order order = orderOptional.get();
        InlineKeyboardMarkup inlineKeyboardMarkup = inlineKeyboardService.cityButtons(order);
        sendMessageService.sendMessage(chatId.toString(),"Введите город который сделал заказ:",inlineKeyboardMarkup);
    }
    @Override
    public void sendListCityForInvoice(Optional<Invoice> invoiceOptional, Long chatId){
        if (invoiceOptional.isEmpty()){
            sendMessageService.sendMessage(chatId.toString(),
                    "Повторите попытку.\nСообщение не содержит номера или такой номер уже есть.");
            return;
        }

        Invoice invoice = invoiceOptional.get();
        InlineKeyboardMarkup inlineKeyboardMarkup = inlineKeyboardService.cityButtons(invoice);
        sendMessageService.sendMessage(chatId.toString(),"Введите город из которого была накладная:",inlineKeyboardMarkup);
    }

    @Override
    public void sendActiveOrdersForInvoice(Long chatId, String message,Integer messageId, Invoice invoice){
        if(WaitDocument.INVOICE.invoiceHaveIdOrder(chatId)){
            sendMessageCloseOrderIfInvoiceHaveOrder(chatId,invoice,messageId);
            return;
        }
        sendMessageService.deleteMessage(chatId,messageId);
        sendMessageService.sendMessage(chatId.toString(),message,inlineKeyboardService.markupActiveOrdersForInvoice(invoice));
    }

    private void sendMessageCloseOrderIfInvoiceHaveOrder(Long chatId, Invoice invoice, Integer messageId) {
        int idOrder = WaitDocument.INVOICE.getIdOrderForInvoice(chatId);
        Optional<Order> orderOptional = orderService.getOrderById(idOrder);

        if(orderOptional.isEmpty()){
            sendMessageService.sendMessage(chatId.toString(),"Заказ не найден.");
            return;
        }
        Order order = orderOptional.get();
        invoice.setOrder(order);
        invoiceService.save(invoice);
        sendMessageCloseOrder(chatId, messageId,"Заказ завершен?",order);
    }


    @Override
    public void sendMessageCloseOrder(Long chatId, Integer messageId, String message, Order order){
        sendMessageService.deleteMessage(chatId,messageId);
        sendMessageService.sendMessage(chatId.toString(),message,inlineKeyboardService.closeOrder(order));
    }
}
