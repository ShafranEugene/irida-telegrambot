package com.github.iridatelegrambot.test.service.sendlers;

import com.github.iridatelegrambot.entity.Invoice;
import com.github.iridatelegrambot.entity.Order;
import com.github.iridatelegrambot.service.InvoiceService;
import com.github.iridatelegrambot.service.OrderService;
import com.github.iridatelegrambot.service.buttons.InlineDocumentButtonService;
import com.github.iridatelegrambot.service.senders.SendDocumentService;
import com.github.iridatelegrambot.service.senders.SendDocumentServiceImpl;
import com.github.iridatelegrambot.service.senders.SendMessageService;
import com.github.iridatelegrambot.service.statuswait.WaitDocument;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.InlineKeyboardMarkup;

import java.util.Optional;

public class SendDocumentServiceImplTest {
    private SendMessageService sendMessageService;
    private InlineDocumentButtonService inlineKeyboardService;
    private OrderService orderService;
    private InvoiceService invoiceService;
    private SendDocumentService sendDocumentService;
    private final Long chatId = 112358L;
    @BeforeEach
    void init(){
        sendMessageService = Mockito.mock(SendMessageService.class);
        inlineKeyboardService = Mockito.mock(InlineDocumentButtonService.class);
        orderService = Mockito.mock(OrderService.class);
        invoiceService = Mockito.mock(InvoiceService.class);
        sendDocumentService = new SendDocumentServiceImpl(sendMessageService,inlineKeyboardService,orderService,invoiceService);

    }

    @Test
    void shouldSendListCitiesForOrder(){
        //given
        Order order = new Order();
        InlineKeyboardMarkup markup = new InlineKeyboardMarkup();
        Mockito.when(inlineKeyboardService.cityButtons(order)).thenReturn(markup);
        //when
        sendDocumentService.sendListCityForOrder(Optional.of(order),chatId);
        //then
        Mockito.verify(sendMessageService).sendMessage(chatId.toString(),"Введите город который сделал заказ:",markup);
    }

    @Test
    void shouldSendListCitiesForInvoice(){
        //given
        Invoice invoice = new Invoice();
        InlineKeyboardMarkup markup = new InlineKeyboardMarkup();
        Mockito.when(inlineKeyboardService.cityButtons(invoice)).thenReturn(markup);
        //when
        sendDocumentService.sendListCityForInvoice(Optional.of(invoice),chatId);
        //then
        Mockito.verify(sendMessageService).sendMessage(chatId.toString(),"Введите город из которого была накладная:",markup);
    }

    @Test
    void shouldNotFindDocument(){
        //when
        sendDocumentService.sendListCityForOrder(Optional.empty(),chatId);
        //then
        Mockito.verify(sendMessageService).sendMessage(chatId.toString(),"Повторите попытку.\nСообщение не содержит номера или такой номер уже есть.");
    }

    @Test
    void shouldSendActiveOrdersForInvoice(){
        //given
        Invoice invoice = new Invoice();
        InlineKeyboardMarkup markup = new InlineKeyboardMarkup();
        Mockito.when(inlineKeyboardService.markupActiveOrdersForInvoice(invoice)).thenReturn(markup);
        String message = "test";
        //when
        sendDocumentService.sendActiveOrdersForInvoice(chatId,message,10,invoice);
        //then
        Mockito.verify(sendMessageService).sendMessage(chatId.toString(),message,markup);
    }

    @Test
    void shouldSendMessageForCloseOrder(){
        //given
        Order order = new Order();
        InlineKeyboardMarkup markup = new InlineKeyboardMarkup();
        Mockito.when(inlineKeyboardService.closeOrder(order)).thenReturn(markup);
        //when
        sendDocumentService.sendMessageCloseOrder(chatId,10,"test",order);
        //then
        Mockito.verify(sendMessageService).sendMessage(chatId.toString(),"test",markup);
    }

    @Test
    void shouldSkipSendOrder(){
        //given
        WaitDocument.INVOICE.setIdOrderToInvoice(chatId,20);
        Invoice invoice = new Invoice();
        Order order = new Order();
        Mockito.when(orderService.getOrderById(20)).thenReturn(Optional.of(order));
        InlineKeyboardMarkup markup = new InlineKeyboardMarkup();
        Mockito.when(inlineKeyboardService.closeOrder(order)).thenReturn(markup);
        //when
        sendDocumentService.sendActiveOrdersForInvoice(chatId,"text",10,invoice);
        //then
        Mockito.verify(sendMessageService).sendMessage(chatId.toString(),"Заказ завершен?",markup);
    }
}
