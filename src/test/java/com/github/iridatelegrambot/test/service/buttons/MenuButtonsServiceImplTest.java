package com.github.iridatelegrambot.test.service.buttons;

import com.github.iridatelegrambot.service.OrderServiceImpl;
import com.github.iridatelegrambot.service.buttons.CommandNameForButtons;
import com.github.iridatelegrambot.service.buttons.MenuButtonsService;
import com.github.iridatelegrambot.service.buttons.MenuButtonsServiceImpl;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.ReplyKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.KeyboardButton;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.KeyboardRow;

import java.util.List;

public class MenuButtonsServiceImplTest {

    private MenuButtonsService menuButtonsService;

    @BeforeEach
    void init(){
        menuButtonsService = new MenuButtonsServiceImpl();
    }

    @Test
    void shouldCreateMenu(){
        //given
        CommandNameForButtons[] buttonsList = CommandNameForButtons.values();
        KeyboardButton button1 = new KeyboardButton(buttonsList[0].getName());
        //when
        ReplyKeyboardMarkup markup = menuButtonsService.mainMenu();
        List<KeyboardRow> rows = markup.getKeyboard();
        KeyboardButton button2 = rows.get(0).get(0);

        //then
        Assertions.assertEquals(button1,button2);
    }

    @Test
    void shouldProperlyCountButtons(){
        //given
        CommandNameForButtons[] buttonsList = CommandNameForButtons.values();
        //when
        ReplyKeyboardMarkup markup = menuButtonsService.mainMenu();
        List<KeyboardRow> rows = markup.getKeyboard();
        //then
        Assertions.assertEquals(buttonsList.length,rows.size());
    }


}
