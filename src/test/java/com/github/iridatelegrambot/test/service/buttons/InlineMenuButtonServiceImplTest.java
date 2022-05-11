package com.github.iridatelegrambot.test.service.buttons;

import com.github.iridatelegrambot.service.buttons.InlineKeyboardService;
import com.github.iridatelegrambot.service.buttons.InlineKeyboardServiceImpl;
import com.github.iridatelegrambot.service.buttons.InlineMenuButtonService;
import com.github.iridatelegrambot.service.buttons.InlineMenuButtonServiceImpl;
import com.github.iridatelegrambot.service.statuswait.WaitDocument;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.InlineKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.InlineKeyboardButton;

import java.util.*;

public class InlineMenuButtonServiceImplTest {
    private InlineKeyboardService inlineKeyboardService;
    private InlineMenuButtonService inlineMenuButtonService;

    @BeforeEach
    void init(){
        inlineKeyboardService = new InlineKeyboardServiceImpl();
        inlineMenuButtonService = new InlineMenuButtonServiceImpl(inlineKeyboardService);
    }

    @Test
    void shouldCreateMenuOrders(){
        //when
        InlineKeyboardMarkup markup = inlineMenuButtonService.showMenuOrder(5);
        List<List<InlineKeyboardButton>> rows = markup.getKeyboard();
        //
        Assertions.assertEquals("Добавить накладную на перемещение",rows.get(0).get(0).getText());
    }

    @Test
    void shouldCreateMenuStat(){
        //when
        InlineKeyboardMarkup markup = inlineMenuButtonService.showMenuStat();
        List<List<InlineKeyboardButton>> rows = markup.getKeyboard();
        //
        Assertions.assertEquals("Информация для администратора",rows.get(0).get(0).getText());
    }

    @Test
    void shouldCreateMenuStatDetails(){
        //when
        InlineKeyboardMarkup markup = inlineMenuButtonService.showMenuStatDetails(WaitDocument.ORDER);
        List<List<InlineKeyboardButton>> rows = markup.getKeyboard();
        //
        Assertions.assertEquals("Получить более детальную информацию по накладной",rows.get(0).get(0).getText());
    }

    @Test
    void shouldCreateMenuAdmin(){
        //when
        InlineKeyboardMarkup markup = inlineMenuButtonService.showMenuAdmin();
        List<List<InlineKeyboardButton>> rows = markup.getKeyboard();
        //
        Assertions.assertEquals("Выдать права администратора пользователю",rows.get(0).get(0).getText());
    }
}
