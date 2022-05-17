package com.github.iridatelegrambot.test.service.statuswait;

import com.github.iridatelegrambot.service.statuswait.WaitDocument;
import com.github.iridatelegrambot.service.statuswait.WaitTypeStatus;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class WaitDocumentTest {
    private final Long chatId = 12345678L;

    @Test
    void shouldSetStatus(){
        //given
        WaitDocument.ORDER.setWaitNumber(chatId,true, WaitTypeStatus.ADD);
        //when-then
        Assertions.assertEquals(true,WaitDocument.getWaitAllStatus(chatId));
    }

    @Test
    void shouldGetStatus(){
        //given
        WaitDocument.ORDER.setWaitNumber(chatId,true,WaitTypeStatus.ADD);
        //when-then
        Assertions.assertTrue(WaitDocument.ORDER.getWaitStatus(chatId));
    }

    @Test
    void shouldGetWaitType(){
        //given
        WaitDocument.ORDER.setWaitNumber(chatId,true,WaitTypeStatus.DELETE);
        //when-then
        Assertions.assertEquals(WaitTypeStatus.DELETE, WaitDocument.ORDER.getWaitType(chatId).get());
    }

    @Test
    void shouldSetAndCheckOrderNumber(){
        //given
        WaitDocument.INVOICE.setIdOrderToInvoice(chatId,10);
        //when-then
        Assertions.assertTrue(WaitDocument.INVOICE.invoiceHaveIdOrder(chatId));
        Assertions.assertEquals(10,WaitDocument.INVOICE.getIdOrderForInvoice(chatId));
    }

    @Test
    void shouldGetOnlyOneActiveDocument(){
        //given
        WaitDocument.ORDER.setWaitNumber(chatId,true,WaitTypeStatus.ADD);
        WaitDocument.INVOICE.setWaitNumber(chatId,true,WaitTypeStatus.ADD);
        //when-then
        Assertions.assertFalse(WaitDocument.ORDER.getWaitStatus(chatId));
    }

    @Test
    void shouldGetOnlyOneActiveType(){
        //given
        WaitDocument.ORDER.setWaitNumber(chatId,true,WaitTypeStatus.ADD);
        WaitDocument.ORDER.setWaitNumber(chatId,true,WaitTypeStatus.DELETE);
        //when-then
        Assertions.assertEquals(WaitTypeStatus.DELETE,WaitDocument.ORDER.getWaitType(chatId).get());
        Assertions.assertNotEquals(WaitTypeStatus.ADD,WaitDocument.ORDER.getWaitType(chatId).get());
    }

    @Test
    void shouldGetOnlyOneActiveDocumentWithOtherType(){
        //given
        WaitDocument.ORDER.setWaitNumber(chatId,true,WaitTypeStatus.ADD);
        WaitDocument.INVOICE.setWaitNumber(chatId,true,WaitTypeStatus.INFO);
        //when-then
        Assertions.assertFalse(WaitDocument.ORDER.getWaitStatus(chatId));
    }
}
