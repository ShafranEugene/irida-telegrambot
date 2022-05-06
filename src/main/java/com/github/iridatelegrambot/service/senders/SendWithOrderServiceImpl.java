package com.github.iridatelegrambot.service.senders;

import com.github.iridatelegrambot.entity.Invoice;
import com.github.iridatelegrambot.entity.Order;
import com.github.iridatelegrambot.service.InvoiceService;
import com.github.iridatelegrambot.service.OrderService;
import com.github.iridatelegrambot.service.buttons.InlineKeyboardService;
import com.github.iridatelegrambot.service.statuswait.WaitDocument;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class SendWithOrderServiceImpl implements SendWithOrderService {
    private final SendMessageService sendMessageService;
    private final InlineKeyboardService inlineKeyboardService;
    private final OrderService orderService;
    private final InvoiceService invoiceService;

    @Autowired
    public SendWithOrderServiceImpl(SendMessageService sendMessageService, InlineKeyboardService inlineKeyboardService,
                                    OrderService orderService, InvoiceService invoiceService) {
        this.sendMessageService = sendMessageService;
        this.inlineKeyboardService = inlineKeyboardService;
        this.orderService = orderService;
        this.invoiceService = invoiceService;
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
