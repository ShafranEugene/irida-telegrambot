package com.github.iridatelegrambot.test.command.buttons;

import com.github.iridatelegrambot.command.CallbackCommand.CallbackCommand;
import com.github.iridatelegrambot.command.CallbackCommand.StatMenuCallbackCommand;
import com.github.iridatelegrambot.service.statuswait.WaitDocument;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.telegram.telegrambots.meta.api.objects.CallbackQuery;

public class StatMenuCallbackCommandTest extends AbstractCallbackCommandTest {
    private final StatMenuCallbackCommand statMenuCallbackCommand =
            new StatMenuCallbackCommand(commandCallbackSenderService,mOrderService,mInvoiceService,mUserTelegramService);

    @Test
    void shouldProperlyAllInfoOrders(){
        //given
        CallbackQuery callbackQuery = createCallbackQuery("stat:infoAllOrders");
        //when
        statMenuCallbackCommand.execute(callbackQuery);
        //then
        Mockito.verify(commandCallbackSenderService).sendMenuStatDetails(12345678L,"Список всех заказов:",10,WaitDocument.ORDER);
    }

    @Override
    protected CallbackCommand getCallbackCommand() {
        return statMenuCallbackCommand;
    }
}
