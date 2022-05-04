package com.github.iridatelegrambot.test.command.buttons;

import com.github.iridatelegrambot.command.CallbackCommand.AddOrderCallbackCommand;
import com.github.iridatelegrambot.command.CallbackCommand.CallbackCommand;
import com.github.iridatelegrambot.entity.Order;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.Mockito;
import org.telegram.telegrambots.meta.api.objects.CallbackQuery;

import java.util.Optional;

public class AddOrderCallbackCommandTest extends AbstractCallbackCommandTest{

    private AddOrderCallbackCommand addOrderCallbackCommand;

    @BeforeEach
    void init(){
        addOrderCallbackCommand = new AddOrderCallbackCommand(mSendMessageService,mOrderService);
    }

    @Test
    void shouldSaveOrder(){
        //given
        CallbackQuery callbackQuery = createCallbackQuery("add_order:{\"id\":1,\"city\":\"Днепр\"}");
        ArgumentCaptor<Order> orderArgumentCaptor = ArgumentCaptor.forClass(Order.class);
        Mockito.when(mOrderService.getOrderById(1)).thenReturn(Optional.of(new Order()));
        //when
        addOrderCallbackCommand.execute(callbackQuery);
        //then
        Mockito.verify(mOrderService).save(orderArgumentCaptor.capture());
        Assertions.assertEquals("Днепр",orderArgumentCaptor.getValue().getCity());
    }

    @Override
    protected CallbackCommand getCallbackCommand() {
        return addOrderCallbackCommand;
    }
}
