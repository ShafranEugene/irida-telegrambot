package com.github.iridatelegrambot.service.buttons;

import org.springframework.stereotype.Service;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.ReplyKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.KeyboardRow;

import java.util.ArrayList;
import java.util.List;
@Service
public class MenuButtonsServiceImpl implements MenuButtonsService{

    public MenuButtonsServiceImpl() {
    }


    @Override
    public ReplyKeyboardMarkup mainMenu() {
        ReplyKeyboardMarkup replyKeyboardMarkup = new ReplyKeyboardMarkup();
        List<KeyboardRow> rows = new ArrayList<>();
        CommandNameForButtons[] commands = CommandNameForButtons.values();

        boolean twoButtonsInOneRow = false;
        for(int i = 0; i<commands.length; i++){
            if(commands[i].getNumberRow() == 2){
                if(!twoButtonsInOneRow){
                    twoButtonsInOneRow = true;
                    KeyboardRow keyboardButtons = new KeyboardRow();
                    keyboardButtons.add(commands[i].getName());
                    rows.add(keyboardButtons);
                } else {
                    twoButtonsInOneRow = false;
                    int indexRow = rows.size()-1;
                    KeyboardRow keyboardButtons = rows.get(indexRow);
                    keyboardButtons.add(commands[i].getName());
                    rows.remove(indexRow);
                    rows.add(keyboardButtons);
                }
            } else if(commands[i].getNumberRow() == 1){
                KeyboardRow keyboardButtons = new KeyboardRow();
                keyboardButtons.add(commands[i].getName());
                rows.add(keyboardButtons);
            }
        }

        replyKeyboardMarkup.setKeyboard(rows);
        return replyKeyboardMarkup;
    }

}
