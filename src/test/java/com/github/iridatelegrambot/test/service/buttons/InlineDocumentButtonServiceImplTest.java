package com.github.iridatelegrambot.test.service.buttons;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.iridatelegrambot.entity.BondOrderToInvoice;
import com.github.iridatelegrambot.entity.Invoice;
import com.github.iridatelegrambot.entity.Order;
import com.github.iridatelegrambot.service.OrderService;
import com.github.iridatelegrambot.service.OrderServiceImpl;
import com.github.iridatelegrambot.service.buttons.InlineDocumentButtonService;
import com.github.iridatelegrambot.service.buttons.InlineDocumentButtonServiceImpl;
import com.github.iridatelegrambot.service.buttons.InlineKeyboardService;
import com.github.iridatelegrambot.service.buttons.InlineKeyboardServiceImpl;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.Mockito;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import static com.github.iridatelegrambot.command.CallbackCommand.CallbackCommandName.ADD_ORDER;
import static com.github.iridatelegrambot.command.CallbackCommand.CallbackCommandName.ADD_ORDER_TO_INVOICE;
import static org.mockito.ArgumentMatchers.eq;

public class InlineDocumentButtonServiceImplTest {
    private InlineKeyboardService inlineKeyboardService;
    private OrderService orderService;
    private InlineDocumentButtonService inlineDocumentButtonService;

    @BeforeEach
    void init(){
        inlineKeyboardService = Mockito.mock(InlineKeyboardServiceImpl.class);
        orderService = Mockito.mock(OrderServiceImpl.class);
        inlineDocumentButtonService = new InlineDocumentButtonServiceImpl(orderService,inlineKeyboardService);
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
            bondJson = ADD_ORDER_TO_INVOICE.getNameForService() + objectMapper.writeValueAsString(bondOrderToInvoice);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
        ArgumentCaptor<Map<String,String>> mapArgumentCaptor = ArgumentCaptor.forClass(Map.class);

        List<Order> orders = new ArrayList<>();
        orders.add(order);
        Mockito.when(orderService.getAllOrdersActive()).thenReturn(orders);
        //when
        inlineDocumentButtonService.markupActiveOrdersForInvoice(invoice);
        Mockito.verify(inlineKeyboardService).createMenu(mapArgumentCaptor.capture(),eq(3));
        //then
        Assertions.assertEquals(bondJson,mapArgumentCaptor.getValue().get("500"));
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
            orderToJson = ADD_ORDER.getNameForService() + objectMapper.writeValueAsString(order);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
        ArgumentCaptor<Map<String,String>> mapArgumentCaptor = ArgumentCaptor.forClass(Map.class);
        //when
        inlineDocumentButtonService.cityButtons(order);
        Mockito.verify(inlineKeyboardService).createMenu(mapArgumentCaptor.capture(),eq(3));
        //then
        Assertions.assertEquals(orderToJson,mapArgumentCaptor.getValue().get("Днепр"));
    }

}
