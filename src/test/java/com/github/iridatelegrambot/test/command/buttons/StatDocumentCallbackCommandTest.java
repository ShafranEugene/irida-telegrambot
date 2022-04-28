package com.github.iridatelegrambot.test.command.buttons;

import com.github.iridatelegrambot.command.CallbackCommand.StatDocumentCallbackCommand;
import com.github.iridatelegrambot.service.statuswait.WaitDocument;
import com.github.iridatelegrambot.service.statuswait.WaitTypeStatus;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.telegram.telegrambots.meta.api.objects.CallbackQuery;

public class StatDocumentCallbackCommandTest extends AbstractCallbackCommandTest {
    private final StatDocumentCallbackCommand statDocument = new StatDocumentCallbackCommand(mSendMessageService);

    @Test
    void shouldProperlySetWaitStatus(){
        //given
        CallbackQuery callbackQuery = createCallbackQuery("stat_document:order:info");
        //when
        statDocument.execute(callbackQuery);
        //then
        Assertions.assertTrue(WaitDocument.ORDER.getWaitStatus(12345678L));
        Assertions.assertTrue(WaitTypeStatus.INFO.getStatus(12345678L));
    }
}
