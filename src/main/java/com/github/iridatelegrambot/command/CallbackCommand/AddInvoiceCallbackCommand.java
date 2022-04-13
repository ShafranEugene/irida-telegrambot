package com.github.iridatelegrambot.command.CallbackCommand;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.iridatelegrambot.entity.Invoice;
import com.github.iridatelegrambot.service.InvoiceService;
import com.github.iridatelegrambot.service.SendMessageService;
import com.github.iridatelegrambot.service.buttons.InlineKeyboardService;
import org.telegram.telegrambots.meta.api.objects.CallbackQuery;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.InlineKeyboardMarkup;

public class AddInvoiceCallbackCommand implements CallbackCommand {

    private final SendMessageService sendMessageService;
    private final InvoiceService invoiceService;
    private final InlineKeyboardService inlineKeyboardService;

    public AddInvoiceCallbackCommand(SendMessageService sendMessageService,InvoiceService invoiceService, InlineKeyboardService inlineKeyboardService){
        this.sendMessageService = sendMessageService;
        this.invoiceService = invoiceService;
        this.inlineKeyboardService = inlineKeyboardService;
    }



    @Override
    public void execute(CallbackQuery callbackQuery) {
        String query = callbackQuery.getData();
        String JSONData = query.substring(query.indexOf('{'));

        ObjectMapper objectMapper = new ObjectMapper();
        int id = Integer.parseInt(JSONData.replaceAll("[^0-9]",""));

        try {
            Invoice invoice = invoiceService.getInvoiceById(id).get();
            Invoice invoiceJson = objectMapper.readValue(JSONData,Invoice.class);
            invoice.setCity(invoiceJson.getCity());
            invoiceService.save(invoice);
            InlineKeyboardMarkup inlineKeyboardMarkup = inlineKeyboardService.markupActiveOrdersForInvoice(invoice);
            sendMessageService.sendMessage(callbackQuery.getMessage().getChatId().toString(),
                    "Выберете какой это заказ:",
                    inlineKeyboardMarkup);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }


    }
}
