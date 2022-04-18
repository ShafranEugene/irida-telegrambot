package com.github.iridatelegrambot.test.command.buttons;

import com.github.iridatelegrambot.command.CallbackCommand.ShowActiveOrdersCallbackCommand;
import com.github.iridatelegrambot.entity.Order;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.Mockito;
import org.telegram.telegrambots.meta.api.objects.CallbackQuery;

import java.util.Optional;

import static org.mockito.ArgumentMatchers.eq;

public class ShowActiveOrdersCallbackCommandTest extends AbstractCallbackCommandTest{

    private ShowActiveOrdersCallbackCommand showActiveOrdersCallbackCommand;
    @BeforeEach
    void init(){
        showActiveOrdersCallbackCommand = new ShowActiveOrdersCallbackCommand(mSendMessageService,mOrderService);
    }

    @Test
    void shouldProperlySendOrderInfo(){
        //given
        CallbackQuery callbackQuery = createCallbackQuery("show_order:id:5");
        Order order = new Order();
        order.setId(5);
        order.setNumber("500");
        Mockito.when(mOrderService.getOrderById(5)).thenReturn(Optional.of(order));
        ArgumentCaptor<String> messageArgumentCaptor = ArgumentCaptor.forClass(String.class);

        //when
        showActiveOrdersCallbackCommand.execute(callbackQuery);
        Mockito.verify(mSendMessageService).sendMessage(eq(callbackQuery.getMessage().getChatId().toString()),messageArgumentCaptor.capture());
        //then
        Assertions.assertEquals(order.toStringForUsers(),messageArgumentCaptor.getValue());
    }
}