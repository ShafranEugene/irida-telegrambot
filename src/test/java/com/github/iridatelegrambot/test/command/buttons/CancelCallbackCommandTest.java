package com.github.iridatelegrambot.test.command.buttons;

import com.github.iridatelegrambot.command.CallbackCommand.CallbackCommand;
import com.github.iridatelegrambot.command.CallbackCommand.CancelCallbackCommand;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.telegram.telegrambots.meta.api.objects.CallbackQuery;

public class CancelCallbackCommandTest extends AbstractCallbackCommandTest{
    private CancelCallbackCommand cancelCallbackCommand;
    @BeforeEach
    void init(){
        cancelCallbackCommand = new CancelCallbackCommand(mOrderService,mInvoiceService,commandCallbackSenderService);
    }

    @Override
    protected CallbackCommand getCallbackCommand() {
        return cancelCallbackCommand;
    }
    @Test
    void shouldDeleteDocument(){
        //given
        CallbackQuery callbackQuery1 = createCallbackQuery("delete:order:id:10");
        CallbackQuery callbackQuery2 = createCallbackQuery("delete:invoice:id:10");
        //when
        cancelCallbackCommand.execute(callbackQuery1);
        cancelCallbackCommand.execute(callbackQuery2);
        //then
        Mockito.verify(mOrderService).delete(10);
        Mockito.verify(mInvoiceService).delete(10);
    }
}
