package com.github.iridatelegrambot.command.CallbackCommand;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.iridatelegrambot.entity.Invoice;
import com.github.iridatelegrambot.service.InvoiceService;
import com.github.iridatelegrambot.service.send.SendMessageWithOrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.objects.CallbackQuery;

@Component
public class AddInvoiceCallbackCommand implements CallbackCommand {

    private final SendMessageWithOrderService sendMessageService;
    private final InvoiceService invoiceService;
    private final CallbackCommandName commandName = CallbackCommandName.ADD_INVOICE;

    @Autowired
    public AddInvoiceCallbackCommand(SendMessageWithOrderService sendMessageService,InvoiceService invoiceService){
        this.sendMessageService = sendMessageService;
        this.invoiceService = invoiceService;
    }



    @Override
    public void execute(CallbackQuery callbackQuery) {
        Long chatId = callbackQuery.getMessage().getChatId();
        String query = callbackQuery.getData();
        String JSONData = query.substring(query.indexOf('{'));

        ObjectMapper objectMapper = new ObjectMapper();
        int id = Integer.parseInt(JSONData.replaceAll("[^0-9]",""));
        if(invoiceService.getInvoiceById(id).isEmpty()){
            sendMessageService.sendMessage(chatId.toString(),"Накладная не найдена.");
            return;
        }
        try {
            Invoice invoice = invoiceService.getInvoiceById(id).get();
            Invoice invoiceJson = objectMapper.readValue(JSONData,Invoice.class);
            invoice.setCity(invoiceJson.getCity());
            invoiceService.save(invoice);
            sendMessageService.sendActiveOrdersForInvoice(chatId,"Выберете заказ:",invoice);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }


    }

    @Override
    public String getNameCommand() {
        return commandName.getName();
    }
}
