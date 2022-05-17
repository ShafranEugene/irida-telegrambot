package com.github.iridatelegrambot.test.command.buttons;

import com.github.iridatelegrambot.command.CallbackCommand.AddStatusUserCallbackCommand;
import com.github.iridatelegrambot.command.CallbackCommand.CallbackCommand;
import com.github.iridatelegrambot.entity.UserTelegram;
import com.github.iridatelegrambot.service.statususer.MuteInviteService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.telegram.telegrambots.meta.api.objects.CallbackQuery;

import java.util.Optional;

public class AddStatusUserCallbackCommandTest extends AbstractCallbackCommandTest{
    private AddStatusUserCallbackCommand addStatusUserCallbackCommand;
    private MuteInviteService inviteService;
    @BeforeEach
    void init(){
        inviteService = Mockito.mock(MuteInviteService.class);
        addStatusUserCallbackCommand = new AddStatusUserCallbackCommand(commandCallbackSenderService,inviteService,mUserTelegramService);
    }
    @Override
    protected CallbackCommand getCallbackCommand() {
        return addStatusUserCallbackCommand;
    }

    @Test
    void shouldProperlySetUserStatusIfTrue(){
        //given
        CallbackQuery callbackQuery = createCallbackQuery("set_status_user:chatId:5:true");
        UserTelegram userTelegram = new UserTelegram();
        userTelegram.setChatId(5L);
        Mockito.when(mUserTelegramService.findByChatId(5L)).thenReturn(Optional.of(userTelegram));
        //when
        addStatusUserCallbackCommand.execute(callbackQuery);
        //then
        Mockito.verify(mUserTelegramService).save(userTelegram);
        Mockito.verify(commandCallbackSenderService).sendMessage(String.valueOf(12345678L),
                "Пользователю \"" + userTelegram.getUserName() + "\" открыт доступ к боту.");
    }

    @Test
    void shouldProperlySetMuteIfFalse(){
        //given
        CallbackQuery callbackQuery = createCallbackQuery("set_status_user:chatId:5:false");
        UserTelegram userTelegram = new UserTelegram();
        userTelegram.setChatId(5L);
        Mockito.when(mUserTelegramService.findByChatId(5L)).thenReturn(Optional.of(userTelegram));
        //when
        addStatusUserCallbackCommand.execute(callbackQuery);
        //then
        Mockito.verify(inviteService).setMuteOfDay(5L);
    }

    @Test
    void shouldNotFindUsers(){
        //given
        CallbackQuery callbackQuery = createCallbackQuery("set_status_user:chatId:5:false");
        Mockito.when(mUserTelegramService.findByChatId(5L)).thenReturn(Optional.empty());
        //when
        addStatusUserCallbackCommand.execute(callbackQuery);
        //then
        Mockito.verify(commandCallbackSenderService).sendMessage(String.valueOf(12345678L),"Пользователь не найден.");
    }
}
