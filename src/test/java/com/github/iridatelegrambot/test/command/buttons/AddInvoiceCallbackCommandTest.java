package com.github.iridatelegrambot.test.command.buttons;

import com.github.iridatelegrambot.command.CallbackCommand.AddInvoiceCallbackCommand;
import com.github.iridatelegrambot.entity.Invoice;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.Mockito;
import org.telegram.telegrambots.meta.api.objects.CallbackQuery;


import java.util.Optional;

public class AddInvoiceCallbackCommandTest extends AbstractCallbackCommandTest{

    private AddInvoiceCallbackCommand addInvoiceCallbackCommand;

    @BeforeEach
    void init(){
        addInvoiceCallbackCommand = new AddInvoiceCallbackCommand(mSendMessageService,mInvoiceService);
    }

    @Test
    void shouldProperlySaveInvoice(){
        //given
        CallbackQuery callbackQuery = createCallbackQuery("add_invoice:{\"id\":1,\"city\":\"Днепр\"}");
        Invoice invoice = new Invoice();

        ArgumentCaptor<Invoice> invoiceArgumentCaptor = ArgumentCaptor.forClass(Invoice.class);
        Mockito.when(mInvoiceService.getInvoiceById(1)).thenReturn(Optional.of(invoice));
        //when
        addInvoiceCallbackCommand.execute(callbackQuery);
        //then
        Mockito.verify(mInvoiceService).save(invoiceArgumentCaptor.capture());
        Mockito.verify(mSendMessageService).sendActiveOrdersForInvoice(12345678L,"Выберете заказ:",invoiceArgumentCaptor.getValue());
        Assertions.assertEquals("Днепр",invoiceArgumentCaptor.getValue().getCity());
    }
}
