package com.github.iridatelegrambot.test.service.statuswait;

import com.github.iridatelegrambot.entity.Order;
import com.github.iridatelegrambot.service.AnswerCatcherService;
import com.github.iridatelegrambot.service.AnswerCatcherServiceImpl;
import com.github.iridatelegrambot.service.SendMessageService;
import com.github.iridatelegrambot.service.SendMessageServiceImpl;
import com.github.iridatelegrambot.service.statuswait.HandleWaitNumberImpl;
import com.github.iridatelegrambot.service.statuswait.WaitDocument;
import com.github.iridatelegrambot.service.statuswait.WaitTypeStatus;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.Mockito;
import org.telegram.telegrambots.meta.api.objects.Message;
import org.telegram.telegrambots.meta.api.objects.Update;

import java.util.Optional;

public class HandleWaitNumberImplTest {
    private SendMessageService sendMessageService;
    private AnswerCatcherService answerCatcherService;
    private HandleWaitNumberImpl handleWaitNumber;
    private final Long chatId = 12345678L;

    @BeforeEach
    void init(){
        sendMessageService = Mockito.mock(SendMessageServiceImpl.class);
        answerCatcherService = Mockito.mock(AnswerCatcherServiceImpl.class);
        handleWaitNumber = new HandleWaitNumberImpl(sendMessageService,answerCatcherService);
    }

    @Test
    void shouldProperlySendInfoAndCloseWaitStatus(){
        //given
        WaitDocument.ORDER.setWaitNumber(chatId,true,WaitTypeStatus.INFO);
        Update update = createUpdate();
        Mockito.when(answerCatcherService.infoOrder(update)).thenReturn("The order has been found.");
        ArgumentCaptor<String> chatIdCaptor = ArgumentCaptor.forClass(String.class);
        ArgumentCaptor<String> messageCaptor = ArgumentCaptor.forClass(String.class);
        //when
        handleWaitNumber.handle(update);
        Mockito.verify(sendMessageService).sendMessage(chatIdCaptor.capture(),messageCaptor.capture());
        //then
        Assertions.assertEquals("The order has been found.",messageCaptor.getValue());
        Assertions.assertEquals(String.valueOf(chatId),chatIdCaptor.getValue());
        Assertions.assertFalse(WaitDocument.ORDER.getWaitStatus(chatId));
    }

    @Test
    void shouldProperlySendDeleteAndCloseWaitStatus(){
        //given
        WaitDocument.INVOICE.setWaitNumber(chatId,true,WaitTypeStatus.DELETE);
        Update update = createUpdate();
        Mockito.when(answerCatcherService.deleteInvoice(update)).thenReturn("The invoice has been deleted.");
        ArgumentCaptor<String> chatIdCaptor = ArgumentCaptor.forClass(String.class);
        ArgumentCaptor<String> messageCaptor = ArgumentCaptor.forClass(String.class);
        //when
        handleWaitNumber.handle(update);
        Mockito.verify(sendMessageService).sendMessage(chatIdCaptor.capture(),messageCaptor.capture());
        //then
        Assertions.assertEquals("The invoice has been deleted.",messageCaptor.getValue());
        Assertions.assertEquals(String.valueOf(chatId),chatIdCaptor.getValue());
        Assertions.assertFalse(WaitDocument.INVOICE.getWaitStatus(chatId));
    }

    @Test
    void shouldProperlyAddDocumentAndCloseWaitStatus(){
        //given
        WaitDocument.ORDER.setWaitNumber(chatId,true,WaitTypeStatus.ADD);
        Update update = createUpdate();
        Order order = new Order();
        order.setId(10);
        Optional<Order> orderOptional = Optional.of(order);

        Mockito.when(answerCatcherService.addOrder(update)).thenReturn(orderOptional);
        ArgumentCaptor<Optional<Order>> orderCaptor = ArgumentCaptor.forClass(Optional.class);
        ArgumentCaptor<Long> chatIdCaptor = ArgumentCaptor.forClass(Long.class);
        //when
        handleWaitNumber.handle(update);
        Mockito.verify(sendMessageService).sendListCityForOrder(orderCaptor.capture(),chatIdCaptor.capture());
        //then
        Assertions.assertEquals(orderOptional,orderCaptor.getValue());
        Assertions.assertFalse(WaitDocument.ORDER.getWaitStatus(chatId));
    }

    @Test
    void shouldFailAddDocumentAndReturnWaitStatusToTrue(){
        //given
        WaitDocument.INVOICE.setWaitNumber(chatId,true,WaitTypeStatus.ADD);
        Update update = createUpdate();
        Mockito.when(answerCatcherService.addInvoice(update)).thenReturn(Optional.empty());
        //when
        handleWaitNumber.handle(update);
        //then
        Assertions.assertTrue(WaitDocument.INVOICE.getWaitStatus(chatId));
    }

    @Test
    void shouldReturnWaitStatusToTrueInFail(){
        //given
        WaitDocument.ORDER.setWaitNumber(chatId,true,WaitTypeStatus.DELETE);
        Update update = createUpdate();
        Mockito.when(answerCatcherService.deleteOrder(update)).thenReturn("Повторите попытку. Заказ не найден.");
        //when
        handleWaitNumber.handle(update);
        //then
        Assertions.assertTrue(WaitDocument.ORDER.getWaitStatus(chatId));
    }

    private Update createUpdate(){
        Update update = new Update();
        Message message = Mockito.mock(Message.class);
        Mockito.when(message.getChatId()).thenReturn(chatId);
        update.setMessage(message);
        return update;
    }
}
