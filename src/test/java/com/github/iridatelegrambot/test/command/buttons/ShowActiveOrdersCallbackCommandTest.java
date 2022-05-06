package com.github.iridatelegrambot.test.command.buttons;

import com.github.iridatelegrambot.command.CallbackCommand.CallbackCommand;
import com.github.iridatelegrambot.command.CallbackCommand.ShowActiveOrdersCallbackCommand;
import com.github.iridatelegrambot.entity.Order;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.Mockito;
import org.telegram.telegrambots.meta.api.objects.CallbackQuery;

import java.util.Optional;

public class ShowActiveOrdersCallbackCommandTest extends AbstractCallbackCommandTest{

    private ShowActiveOrdersCallbackCommand showActiveOrdersCallbackCommand;
    @BeforeEach
    void init(){
        showActiveOrdersCallbackCommand = new ShowActiveOrdersCallbackCommand(commandCallbackSenderService,mOrderService);
    }

    @Test
    void shouldProperlySendOrderInfo(){
        //given
        CallbackQuery callbackQuery = createCallbackQuery("show_order:id:5");
        Order order = new Order();
        order.setId(5);
        order.setNumber("500");
        Mockito.when(mOrderService.getOrderById(5)).thenReturn(Optional.of(order));
        ArgumentCaptor<Long> longArgumentCaptor = ArgumentCaptor.forClass(Long.class);
        ArgumentCaptor<Order> orderArgumentCaptor = ArgumentCaptor.forClass(Order.class);

        //when
        showActiveOrdersCallbackCommand.execute(callbackQuery);
        Mockito.verify(commandCallbackSenderService).sendMenuOrder(longArgumentCaptor.capture(),orderArgumentCaptor.capture());
        //then
        Assertions.assertEquals(order.toStringForUsers(),orderArgumentCaptor.getValue().toStringForUsers());
        Assertions.assertEquals(callbackQuery.getMessage().getChatId(),longArgumentCaptor.getValue());
    }

    @Test
    void shouldCantFindOrder(){
        //given
        CallbackQuery callbackQuery = createCallbackQuery("show_order:id:5");
        Mockito.when(mOrderService.getOrderById(5)).thenReturn(Optional.empty());

        ArgumentCaptor<String> chatIdCaptor = ArgumentCaptor.forClass(String.class);
        ArgumentCaptor<String> messageCaptor = ArgumentCaptor.forClass(String.class);

        //when
        showActiveOrdersCallbackCommand.execute(callbackQuery);
        Mockito.verify(mSendMessageService).sendMessage(chatIdCaptor.capture(),messageCaptor.capture());
        //then
        Assertions.assertEquals("Заказ не найден.",messageCaptor.getValue());
        Assertions.assertEquals(String.valueOf(12345678L),chatIdCaptor.getValue());
    }

    @Override
    protected CallbackCommand getCallbackCommand() {
        return showActiveOrdersCallbackCommand;
    }
}
