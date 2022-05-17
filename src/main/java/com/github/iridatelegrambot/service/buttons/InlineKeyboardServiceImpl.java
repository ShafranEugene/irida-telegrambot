package com.github.iridatelegrambot.service.buttons;

import org.springframework.stereotype.Service;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.InlineKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.InlineKeyboardButton;

import java.util.*;

@Service
public class InlineKeyboardServiceImpl implements InlineKeyboardService{

    @Override
    public InlineKeyboardMarkup createMenu(Map<String, String> TextAndCallbackData){
        List<List<InlineKeyboardButton>> rows = new ArrayList<>();
        for(Map.Entry<String,String> entry : TextAndCallbackData.entrySet()){
            List<InlineKeyboardButton> row = new ArrayList<>();
            InlineKeyboardButton button = new InlineKeyboardButton();
            button.setText(entry.getKey());
            button.setCallbackData(entry.getValue());
            row.add(button);
            rows.add(row);
        }
        InlineKeyboardMarkup markup = new InlineKeyboardMarkup();
        markup.setKeyboard(rows);
        return markup;
    }

    @Override
    public InlineKeyboardMarkup createMenu(Map<String, String> textAndCallbackData,Integer amountOfButtonsInRow){
        List<List<InlineKeyboardButton>> rows = new ArrayList<>();
        rows.add(new ArrayList<>());
        int counterButtons = 0;

        for(Map.Entry<String,String> data : textAndCallbackData.entrySet()){
            counterButtons++;
            if(counterButtons > amountOfButtonsInRow){
                rows.add(new ArrayList<>());
                counterButtons = 0;
            } else {
                InlineKeyboardButton button = new InlineKeyboardButton();
                button.setText(data.getKey());
                button.setCallbackData(data.getValue());
                rows.get(rows.size() - 1).add(button);
            }
        }

        InlineKeyboardMarkup markup = new InlineKeyboardMarkup();
        markup.setKeyboard(rows);
        return markup;
    }

}
