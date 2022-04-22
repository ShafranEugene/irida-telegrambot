package com.github.iridatelegrambot.test.command.buttons;

import com.github.iridatelegrambot.command.CallbackCommand.StatMenuCallbackCommand;
import com.github.iridatelegrambot.service.statuswait.WaitDocument;
import com.github.iridatelegrambot.service.statuswait.WaitTypeStatus;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.telegram.telegrambots.meta.api.objects.CallbackQuery;

public class StatMenuCallbackCommandTest extends AbstractCallbackCommandTest {
    private final StatMenuCallbackCommand statMenuCallbackCommand =
            new StatMenuCallbackCommand(mSendMessageService,mOrderService,mInvoiceService);

    @Test
    void shouldProperlyAllInfoOrders(){
        //given
        CallbackQuery callbackQuery = createCallbackQuery("stat:infoAllOrders");
        String message = "Список всех заказов:";
        //when
        statMenuCallbackCommand.execute(callbackQuery);
        //then
        Mockito.verify(mSendMessageService).sendMenuStatDetails(12345678L,"Список всех заказов:","order");
    }

    @Test
    void shouldProperlySetWaitStatus(){
        //given
        CallbackQuery callbackQuery = createCallbackQuery("stat:order:info");
        //when
        statMenuCallbackCommand.execute(callbackQuery);
        //then
        Assertions.assertTrue(WaitDocument.ORDER.getWaitStatus(12345678L));
        Assertions.assertTrue(WaitTypeStatus.INFO.getStatus(12345678L));
    }
}
