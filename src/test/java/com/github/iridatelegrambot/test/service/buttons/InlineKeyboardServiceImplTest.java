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
}
