package com.github.iridatelegrambot.test.service;

import com.github.iridatelegrambot.entity.Invoice;
import com.github.iridatelegrambot.entity.Order;
import com.github.iridatelegrambot.entity.UserTelegram;
import com.github.iridatelegrambot.service.*;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.telegram.telegrambots.meta.api.objects.Message;
import org.telegram.telegrambots.meta.api.objects.Update;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Optional;

public class AnswerCatcherServiceImplTest {

    private OrderService orderService;
    private InvoiceService invoiceService;
    private UserTelegramServiceImpl userTelegramService;
    private AnswerCatcherServiceImpl answerCatcherService;

    @BeforeEach
    void init(){
        orderService = Mockito.mock(OrderService.class);
        invoiceService = Mockito.mock(InvoiceService.class);
        userTelegramService = Mockito.mock(UserTelegramServiceImpl.class);
        answerCatcherService = new AnswerCatcherServiceImpl(orderService,invoiceService,userTelegramService);
    }

    @Test
    void shouldProperlyAnswerByOrder(){
        //given
        Update update = new Update();

        Message message = Mockito.mock(Message.class);
        Mockito.when(message.getChatId()).thenReturn(12345678L);
        Mockito.when(message.getText()).thenReturn("500");
        update.setMessage(message);

        Order order = new Order();
        order.setNumber("500");
        order.setStatusActive(true);

        Date date = new Date();
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        order.setDate(simpleDateFormat.format(date));

        //when
        Optional<Order> orderOptional = answerCatcherService.answerByOrder(update);

        //then
        Assertions.assertTrue(orderOptional.isPresent());
        Assertions.assertEquals(order,orderOptional.get());
    }

    @Test
    void shouldProperlyAnswerByInvoice(){
        //given
        Update update = new Update();

        Message message = Mockito.mock(Message.class);
        Mockito.when(message.getChatId()).thenReturn(12345678L);
        Mockito.when(message.getText()).thenReturn("1000");
        update.setMessage(message);

        Invoice invoice = new Invoice();
        invoice.setIdUser(12345678L);
        invoice.setNumber("1000");
        Date date = new Date();
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        invoice.setDate(simpleDateFormat.format(date));

        //when
        Optional<Invoice> invoiceOptional = answerCatcherService.answerByInvoice(update);

        //then
        Assertions.assertTrue(invoiceOptional.isPresent());
        Assertions.assertEquals(invoice,invoiceOptional.get());
    }

    @Test
    void shouldGetNullDueToInvalidNumber(){
        //given
        Update update = new Update();

        Message message = Mockito.mock(Message.class);
        Mockito.when(message.getChatId()).thenReturn(12345678L);
        Mockito.when(message.getText()).thenReturn("aboba");
        update.setMessage(message);
        //when
        Optional<Invoice> invoiceOptional = answerCatcherService.answerByInvoice(update);
        Optional<Order> orderOptional = answerCatcherService.answerByOrder(update);
        //then
        Assertions.assertTrue(invoiceOptional.isEmpty());
        Assertions.assertTrue(orderOptional.isEmpty());
    }

}
