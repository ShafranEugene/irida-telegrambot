package com.github.iridatelegrambot.command.CallbackCommand;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.iridatelegrambot.entity.BondOrderToInvoice;
import com.github.iridatelegrambot.entity.Invoice;
import com.github.iridatelegrambot.entity.Order;
import com.github.iridatelegrambot.service.InvoiceService;
import com.github.iridatelegrambot.service.OrderService;
import com.github.iridatelegrambot.service.send.SendMessageWithOrderService;
import org.telegram.telegrambots.meta.api.objects.CallbackQuery;

public class AddOrderToInvoiceCallbackCommand implements CallbackCommand {

    private final SendMessageWithOrderService sendMessageService;
    private final InvoiceService invoiceService;
    private final OrderService orderService;

    public AddOrderToInvoiceCallbackCommand(SendMessageWithOrderService sendMessageService, InvoiceService invoiceService,
                                            OrderService orderService) {
        this.sendMessageService = sendMessageService;
        this.invoiceService = invoiceService;
        this.orderService = orderService;
    }

    @Override
    public void execute(CallbackQuery callbackQuery) {
        Long chatId = callbackQuery.getMessage().getChatId();
        String query = callbackQuery.getData();
        String JsonBond = query.substring(query.indexOf('{'));

        ObjectMapper objectMapper = new ObjectMapper();
        try {
            BondOrderToInvoice bond = objectMapper.readValue(JsonBond,BondOrderToInvoice.class);
            int idOrder = bond.getIdOrder();
            int idInvoice = bond.getIdInvoice();
            if(invoiceService.getInvoiceById(idInvoice).isEmpty() || orderService.getOrderById(idOrder).isEmpty()){
                sendMessageService.sendMessage(chatId.toString(),"Заказ или накладную с таким номер не найдено.");
            }
            Invoice invoice = invoiceService.getInvoiceById(idInvoice).get();
            Order order = orderService.getOrderById(idOrder).get();
            invoice.setOrder(order);
            invoiceService.save(invoice);

            sendMessageService.sendMessageCloseOrder(callbackQuery.getMessage().getChatId(),"Заказ завершен?",order);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }

    }
}
