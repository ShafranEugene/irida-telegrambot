package com.github.iridatelegrambot.test.command.buttons;

import com.github.iridatelegrambot.command.AddInvoiceCommand;
import com.github.iridatelegrambot.command.CallbackCommand.CallbackCommand;
import com.github.iridatelegrambot.command.CallbackCommand.OrderMenuCallbackCommand;
import com.github.iridatelegrambot.entity.Order;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.telegram.telegrambots.meta.api.objects.CallbackQuery;
import org.telegram.telegrambots.meta.api.objects.Message;
import org.telegram.telegrambots.meta.api.objects.Update;

import java.util.Optional;

public class OrderMenuCallbackCommandTest extends AbstractCallbackCommandTest {
    private OrderMenuCallbackCommand orderMenuCallbackCommand;
    private AddInvoiceCommand addInvoiceCommand;
    @BeforeEach
    void init(){
        addInvoiceCommand = Mockito.mock(AddInvoiceCommand.class);
        orderMenuCallbackCommand = new OrderMenuCallbackCommand(mOrderService,commandCallbackSenderService,addInvoiceCommand);
    }

    @Override
    protected CallbackCommand getCallbackCommand() {
        return orderMenuCallbackCommand;
    }

    @Test
    void shouldAddInvoice(){
        //given
        CallbackQuery callbackQuery = createCallbackQuery("order:addinvoice:id:10");
        Update update = new Update();
        Message message = callbackQuery.getMessage();
        message.setText("/add_invoice");
        update.setMessage(message);

        Mockito.when(mOrderService.getOrderById(10)).thenReturn(Optional.of(new Order()));
        //when
        orderMenuCallbackCommand.execute(callbackQuery);
        //then
        Mockito.verify(addInvoiceCommand).execute(update);
    }

    @Test
    void shouldDeleteOrder(){
        //given
        CallbackQuery callbackQuery = createCallbackQuery("order:delete:id:10");
        Order order = new Order();
        Mockito.when(mOrderService.getOrderById(10)).thenReturn(Optional.of(order));
        //when
        orderMenuCallbackCommand.execute(callbackQuery);
        //then
        Mockito.verify(mOrderService).delete(10);

    }
}
