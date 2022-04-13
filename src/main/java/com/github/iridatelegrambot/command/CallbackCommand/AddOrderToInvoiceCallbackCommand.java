package com.github.iridatelegrambot.command.CallbackCommand;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.iridatelegrambot.entity.BondOrderToInvoice;
import com.github.iridatelegrambot.entity.Invoice;
import com.github.iridatelegrambot.entity.Order;
import com.github.iridatelegrambot.service.InvoiceService;
import com.github.iridatelegrambot.service.OrderService;
import com.github.iridatelegrambot.service.SendMessageService;
import com.github.iridatelegrambot.service.buttons.InlineKeyboardService;
import org.telegram.telegrambots.meta.api.objects.CallbackQuery;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.InlineKeyboardMarkup;

public class AddOrderToInvoiceCallbackCommand implements CallbackCommand {

    private final SendMessageService sendMessageService;
    private final InvoiceService invoiceService;
    private final OrderService orderService;
    private final InlineKeyboardService inlineKeyboardService;

    public AddOrderToInvoiceCallbackCommand(SendMessageService sendMessageService, InvoiceService invoiceService,
                                            OrderService orderService, InlineKeyboardService inlineKeyboardService) {
        this.sendMessageService = sendMessageService;
        this.invoiceService = invoiceService;
        this.orderService = orderService;
        this.inlineKeyboardService = inlineKeyboardService;
    }

    @Override
    public void execute(CallbackQuery callbackQuery) {
        String query = callbackQuery.getData();
        String JsonBond = query.substring(query.indexOf('{'));

        ObjectMapper objectMapper = new ObjectMapper();
        try {
            BondOrderToInvoice bond = objectMapper.readValue(JsonBond,BondOrderToInvoice.class);
            int idOrder = bond.getIdOrder();
            int idInvoice = bond.getIdInvoice();
            Invoice invoice = invoiceService.getInvoiceById(idInvoice).get();
            Order order = orderService.getOrderById(idOrder).get();
            invoice.setOrder(order);
            invoiceService.save(invoice);

            InlineKeyboardMarkup inlineKeyboardMarkup = inlineKeyboardService.closeOrder(order);

            sendMessageService.sendMessage(callbackQuery.getMessage().getChatId().toString(),"Заказ завершен?", inlineKeyboardMarkup);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }

    }
}
