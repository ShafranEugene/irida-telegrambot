package com.github.iridatelegrambot.test.service.buttons;

import com.github.iridatelegrambot.command.CommandName;
import com.github.iridatelegrambot.service.buttons.CommandNameForButtons;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.platform.commons.JUnitException;

public class CommandNameForButtonsTest {
    @Test
    void shouldProperlyGetMainCommand(){
        //when
        boolean test1 = CommandNameForButtons.hasMainCommand("Статистика");
        boolean test2 = CommandNameForButtons.hasMainCommand("Стасик");
        //then
        Assertions.assertTrue(test1);
        Assertions.assertFalse(test2);
    }

    @Test
    void shouldProperlyFindCommand(){
        //when
        CommandName commandName = CommandNameForButtons.findCommandName("Помощь");
        //then
        Assertions.assertEquals(CommandName.HELP,commandName);
    }

    @Test
    void matchingTheNumberOfButtonsInRow(){
        //given
        int countTwoButtonsInRow = 0;
        int countThreeButtonsInRow = 0;
        int countFourButtonsInRow = 0;
        for(CommandNameForButtons command : CommandNameForButtons.values()){
            if(command.getNumberRow() == 2){
                countTwoButtonsInRow++;
            } else if (command.getNumberRow() == 3){
                countThreeButtonsInRow++;
            } else if(command.getNumberRow() == 4){
                countFourButtonsInRow++;
            } else if(command.getNumberRow() > 4){
                Assertions.fail("Слишком много кнопок в ряду");
            }
        }
        Assertions.assertEquals(0,countTwoButtonsInRow%2);
        Assertions.assertEquals(0,countThreeButtonsInRow%3);
        Assertions.assertEquals(0,countFourButtonsInRow%4);
    }
}
