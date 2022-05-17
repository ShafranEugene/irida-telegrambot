package com.github.iridatelegrambot.test.service.buttons;

import com.github.iridatelegrambot.service.buttons.InlineKeyboardService;
import com.github.iridatelegrambot.service.buttons.InlineKeyboardServiceImpl;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.InlineKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.InlineKeyboardButton;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

public class InlineKeyboardServiceImplTest {
    private final InlineKeyboardService inlineKeyboardService = new InlineKeyboardServiceImpl();

    @Test
    void shouldProperlyCreateMenu(){
        //given
        Map<String,String> dataButtons = new HashMap<>();
        dataButtons.put("text","callback");
        //when
        InlineKeyboardMarkup markup = inlineKeyboardService.createMenu(dataButtons);
        List<List<InlineKeyboardButton>> buttons = markup.getKeyboard();
        InlineKeyboardButton button = buttons.get(0).get(0);
        //then
        Assertions.assertEquals("text",button.getText());
        Assertions.assertEquals("callback",button.getCallbackData());
    }

    @Test
    void shouldProperlyCreateMenuWithMultipleButtonsInARow(){
        //given
        TreeMap<String,String> dataMap = new TreeMap<>();
        dataMap.put("Test1","test_1");
        dataMap.put("Test2","test_2");
        dataMap.put("Test3","test_3");
        //when
        InlineKeyboardMarkup markup = inlineKeyboardService.createMenu(dataMap,2);
        List<List<InlineKeyboardButton>> rows = markup.getKeyboard();
        //then
        Assertions.assertEquals(2,rows.get(0).size());
        Assertions.assertEquals("Test1",rows.get(0).get(0).getText());
        Assertions.assertEquals("Test2",rows.get(0).get(1).getText());
    }
}
