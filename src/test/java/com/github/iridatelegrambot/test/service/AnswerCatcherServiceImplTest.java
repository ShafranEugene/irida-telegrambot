package com.github.iridatelegrambot.test.service;

import com.github.iridatelegrambot.entity.Invoice;
import com.github.iridatelegrambot.entity.Order;
import com.github.iridatelegrambot.service.*;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.telegram.telegrambots.meta.api.objects.Chat;
import org.telegram.telegrambots.meta.api.objects.Message;
import org.telegram.telegrambots.meta.api.objects.Update;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;

public class AnswerCatcherServiceImplTest {

    private OrderService orderService;
    private InvoiceService invoiceService;
    private UserTelegramServiceImpl userTelegramService;
    private AnswerCatcherServiceImpl answerCatcherService;
    private Update updateWithNumber500;
    private Update updateWithNumber600ForSave;
    private final Long chatId = 12345678L;

    @BeforeEach
    void init(){
        orderService = Mockito.mock(OrderService.class);
        invoiceService = Mockito.mock(InvoiceService.class);
        userTelegramService = Mockito.mock(UserTelegramServiceImpl.class);
        answerCatcherService = new AnswerCatcherServiceImpl(orderService,invoiceService,userTelegramService);

        updateWithNumber600ForSave = new Update();
        Message messageForSave = Mockito.mock(Message.class);
        Mockito.when(messageForSave.getText()).thenReturn("600");
        Mockito.when(messageForSave.getChatId()).thenReturn(chatId);
        Chat chat = Mockito.mock(Chat.class);
        Mockito.when(chat.getUserName()).thenReturn("Lupa");
        Mockito.when(messageForSave.getChat()).thenReturn(chat);
        updateWithNumber600ForSave.setMessage(messageForSave);

        updateWithNumber500 = new Update();
        Message message = Mockito.mock(Message.class);
        Mockito.when(message.getText()).thenReturn("500");
        Mockito.when(message.getChatId()).thenReturn(chatId);
        Mockito.when(message.getChat()).thenReturn(chat);
        updateWithNumber500.setMessage(message);

        List<Order> orderList = new ArrayList<>();
        List<Invoice> invoiceList = new ArrayList<>();
        for(int i = 0; i<5; i++){
            Order order = new Order();
            order.setId(1 + i);
            order.setNumber("50" + i);
            orderList.add(order);
            Invoice invoice = new Invoice();
            invoice.setId(1 + i);
            invoice.setNumber("50" + i);
            invoiceList.add(invoice);
        }
        Mockito.when(orderService.getAllOrders()).thenReturn(orderList);
        Mockito.when(invoiceService.getAllInvoice()).thenReturn(invoiceList);
    }

    @Test
    void shouldProperlySaveOrder(){
        //given
        Order order = new Order();
        order.setNumber("600");
        order.setStatusActive(true);

        Date date = new Date();
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        order.setDate(simpleDateFormat.format(date));

        //when
        Optional<Order> orderOptional = answerCatcherService.addOrder(updateWithNumber600ForSave);

        //then
        Assertions.assertTrue(orderOptional.isPresent());
        Assertions.assertEquals(order,orderOptional.get());
    }

    @Test
    void shouldProperlySaveInvoice(){
        //given
        Invoice invoice = new Invoice();
        invoice.setNumber("600");
        Date date = new Date();
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        invoice.setDate(simpleDateFormat.format(date));

        //when
        Optional<Invoice> invoiceOptional = answerCatcherService.addInvoice(updateWithNumber600ForSave);

        //then
        Assertions.assertTrue(invoiceOptional.isPresent());
        Assertions.assertEquals(invoice,invoiceOptional.get());
    }

    @Test
    void shouldGetNullDueToInvalidNumber(){
        //given
        Update updateInvalid = new Update();

        Message message = Mockito.mock(Message.class);
        Mockito.when(message.getChatId()).thenReturn(chatId);
        Mockito.when(message.getText()).thenReturn("aboba");
        updateInvalid.setMessage(message);
        //when
        Optional<Invoice> invoiceOptional = answerCatcherService.addInvoice(updateInvalid);
        Optional<Order> orderOptional = answerCatcherService.addOrder(updateInvalid);
        //then
        Assertions.assertTrue(invoiceOptional.isEmpty());
        Assertions.assertTrue(orderOptional.isEmpty());
    }

    @Test
    void shouldProperlyDeleteInvoice(){
        //when
        answerCatcherService.deleteInvoice(updateWithNumber500);
        //then
        Mockito.verify(invoiceService).delete(1);
    }

    @Test
    void shouldProperlyDeleteOrder(){
        //when
        answerCatcherService.deleteOrder(updateWithNumber500);
        //then
        Mockito.verify(orderService).delete(1);
    }

    @Test
    void shouldProperlyGetInfoInvoice(){
        //given
        Invoice invoice = new Invoice();
        invoice.setId(1);
        invoice.setNumber("500");
        //when
        String invoiceInfo = answerCatcherService.infoInvoice(updateWithNumber500);
        //then
        Assertions.assertEquals(invoice.toStringForUser(),invoiceInfo);
    }

    @Test
    void shouldProperlyGetInfoOrder(){
        //given
        Order order = new Order();
        order.setId(1);
        order.setNumber("500");
        //when
        String orderInfo = answerCatcherService.infoOrder(updateWithNumber500);
        //then
        Assertions.assertEquals(order.toStringForUsers(),orderInfo);
    }

    @Test
    void shouldNotFindOrder(){
        //given
        Mockito.when(orderService.getAllOrders()).thenReturn(new ArrayList<>());
        String unknownAnswer = "Повторите попытку. Заказ с таким номер не найден.";
        //when
        String answer1 = answerCatcherService.deleteOrder(updateWithNumber500);
        String answer2 = answerCatcherService.infoOrder(updateWithNumber500);
        //then
        Assertions.assertEquals(unknownAnswer,answer1);
        Assertions.assertEquals(unknownAnswer,answer2);
    }

    @Test
    void shouldNotFindInvoice(){
        //given
        Mockito.when(invoiceService.getAllInvoice()).thenReturn(new ArrayList<>());
        String unknownAnswer = "Повторите попытку. Накладная с таким номер не найден.";
        //when
        String answer1 = answerCatcherService.deleteInvoice(updateWithNumber500);
        String answer2 = answerCatcherService.deleteInvoice(updateWithNumber500);
        //then
        Assertions.assertEquals(unknownAnswer,answer1);
        Assertions.assertEquals(unknownAnswer,answer2);
    }

}
