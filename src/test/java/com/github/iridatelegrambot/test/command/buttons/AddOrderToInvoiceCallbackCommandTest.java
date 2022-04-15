package com.github.iridatelegrambot.test.command.buttons;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.iridatelegrambot.command.CallbackCommand.AddOrderToInvoiceCallbackCommand;
import com.github.iridatelegrambot.entity.Invoice;
import com.github.iridatelegrambot.entity.Order;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.Mockito;
import org.telegram.telegrambots.meta.api.objects.CallbackQuery;

import java.util.Optional;

public class AddOrderToInvoiceCallbackCommandTest extends AbstractCallbackCommandTest {

    private AddOrderToInvoiceCallbackCommand addOrderToInvoiceCallbackCommand;

    @BeforeEach
    void init(){
        addOrderToInvoiceCallbackCommand = new AddOrderToInvoiceCallbackCommand(mSendMessageService,mInvoiceService,mOrderService);
    }

    @Test
    void shouldProperlyBondOrderAndInvoice(){
        //given
        CallbackQuery callbackQuery = createCallbackQuery("addOrdToInv:{\"idOrder\":1,\"idInvoice\":1}");

        Order order = new Order();
        order.setId(1);
        Invoice invoice = new Invoice();
        invoice.setId(1);
        Mockito.when(mOrderService.getOrderById(1)).thenReturn(Optional.of(order));
        Mockito.when(mInvoiceService.getInvoiceById(1)).thenReturn(Optional.of(invoice));
        ArgumentCaptor<Invoice> invoiceArgumentCaptor = ArgumentCaptor.forClass(Invoice.class);
        invoice.setOrder(order);
        //when
        addOrderToInvoiceCallbackCommand.execute(callbackQuery);

        //then
        Mockito.verify(mInvoiceService).save(invoiceArgumentCaptor.capture());
        Assertions.assertEquals(invoice,invoiceArgumentCaptor.getValue());
    }


}
