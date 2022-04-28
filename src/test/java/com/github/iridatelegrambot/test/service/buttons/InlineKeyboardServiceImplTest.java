package com.github.iridatelegrambot.test.service.buttons;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.iridatelegrambot.entity.BondOrderToInvoice;
import com.github.iridatelegrambot.entity.Invoice;
import com.github.iridatelegrambot.entity.Order;
import com.github.iridatelegrambot.service.OrderService;
import com.github.iridatelegrambot.service.UserTelegramServiceImpl;
import com.github.iridatelegrambot.service.buttons.InlineKeyboardService;
import com.github.iridatelegrambot.service.buttons.InlineKeyboardServiceImpl;
import org.aspectj.weaver.ast.Or;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.InlineKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.InlineKeyboardButton;

import java.util.ArrayList;
import java.util.List;

public class InlineKeyboardServiceImplTest {
    private OrderService orderService;
    private UserTelegramServiceImpl userTelegramService;
    private InlineKeyboardService inlineKeyboardService;

    @BeforeEach
    void init(){
        orderService = Mockito.mock(OrderService.class);
        userTelegramService = Mockito.mock(UserTelegramServiceImpl.class);
        inlineKeyboardService = new InlineKeyboardServiceImpl(orderService,userTelegramService);
    }

    @Test
    void shouldProperlyCityButtons(){
        //given
        Order order = new Order();
        order.setId(1);
        order.setCity("Днепр");
        ObjectMapper objectMapper = new ObjectMapper();

        String orderToJson = "null";
        try {
            orderToJson = "add_order:" + objectMapper.writeValueAsString(order);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }

        InlineKeyboardButton button = new InlineKeyboardButton();
        button.setText(order.getCity());
        button.setCallbackData(orderToJson);

        //when
        InlineKeyboardMarkup inlineKeyboardMarkup = inlineKeyboardService.cityButtons(order);
        List<List<InlineKeyboardButton>> rows = inlineKeyboardMarkup.getKeyboard();
        InlineKeyboardButton buttonOfMarkup = rows.get(0).get(0);

        //then
        Assertions.assertEquals(button,buttonOfMarkup);
    }

    @Test
    void shouldPropertyMarkupWithActiveOrdersForInvoice(){
        //given
        Order order = new Order();
        order.setId(1);
        order.setNumber("500");
        Invoice invoice = new Invoice();
        invoice.setId(1);


        BondOrderToInvoice bondOrderToInvoice = new BondOrderToInvoice(order.getId(),invoice.getId());
        String bondJson = "null";
        ObjectMapper objectMapper = new ObjectMapper();
        try {
            bondJson = "add_ord_to_inv:" + objectMapper.writeValueAsString(bondOrderToInvoice);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }

        InlineKeyboardButton button = new InlineKeyboardButton();
        button.setText(order.getNumber());
        button.setCallbackData(bondJson);

        List<Order> orders = new ArrayList<>();
        orders.add(order);
        Mockito.when(orderService.getAllOrdersActive()).thenReturn(orders);

        //when
        InlineKeyboardMarkup inlineKeyboardMarkup = inlineKeyboardService.markupActiveOrdersForInvoice(invoice);
        List<List<InlineKeyboardButton>> rows = inlineKeyboardMarkup.getKeyboard();
        InlineKeyboardButton buttonOfMarkup = rows.get(0).get(0);
        //then
        Assertions.assertEquals(button,buttonOfMarkup);
    }
}
