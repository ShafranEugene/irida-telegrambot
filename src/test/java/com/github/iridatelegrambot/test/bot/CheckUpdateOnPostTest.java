package com.github.iridatelegrambot.test.bot;

import com.github.iridatelegrambot.bot.CheckUpdateOnPost;
import com.github.iridatelegrambot.entity.ConditionBot;
import com.github.iridatelegrambot.entity.UserTelegram;
import com.github.iridatelegrambot.service.UserTelegramService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import java.util.Optional;


public class CheckUpdateOnPostTest {

    private UserTelegramService userTelegramService;
    private CheckUpdateOnPost checkUpdateOnPost;

    @BeforeEach
    void init(){
        userTelegramService = Mockito.mock(UserTelegramService.class);
        checkUpdateOnPost = new CheckUpdateOnPost(userTelegramService);
    }

    @Test
    void shouldProperlyGetCorrectStatus(){
        //given
        Long chatId = 12345678L;

        ConditionBot conditionBot = new ConditionBot();
        conditionBot.setAnswerOrderStatus(true);

        UserTelegram userTelegram = new UserTelegram();
        userTelegram.setChatId(chatId);
        userTelegram.setConditionBot(conditionBot);
        Optional<UserTelegram> userTelegramOptional = Optional.of(userTelegram);

        Mockito.when(userTelegramService.findByChatId(12345678L)).thenReturn(userTelegramOptional);
        //when-then
        Assertions.assertTrue(checkUpdateOnPost.waitingNumberOrder(chatId));
    }

    @Test
    void shouldProperlySetStatus(){
        //given
        Long chatId = 100000001L;
        //when
        checkUpdateOnPost.setStatusOrder(chatId,true);
        //then
        Mockito.verify(userTelegramService).findByChatId(chatId);
    }


}
