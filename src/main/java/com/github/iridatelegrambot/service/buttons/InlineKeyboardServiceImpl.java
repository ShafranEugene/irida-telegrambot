package com.github.iridatelegrambot.service.buttons;

import org.telegram.telegrambots.meta.api.objects.replykeyboard.InlineKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.InlineKeyboardButton;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

import static com.github.iridatelegrambot.service.buttons.CityName.*;

public class InlineKeyboardServiceImpl implements InlineKeyboardService{
    @Override
    public InlineKeyboardMarkup cityButtons(Object orderOrIvoice) {
        InlineKeyboardMarkup inlineKeyboardMarkup = new InlineKeyboardMarkup();

        HashMap<String,String> cityNameValue = new HashMap<>();

        List<CityName> cityNamesList = new ArrayList<>(Arrays.asList(CityName.values()));

        cityNamesList.forEach(city -> {
            cityNameValue.put(city.getNameCity(),city.getCallBack());
        });

        inlineKeyboardMarkup = InlineKeyboardMarkup.builder()
                .keyboardRow(List.of(InlineKeyboardButton.builder()
                            .text(DNIPRO.getNameCity())
                            .callbackData(DNIPRO.getCallBack())
                            .build(),
                        InlineKeyboardButton.builder()
                            .text(KHARKIV.getNameCity())
                            .callbackData(KHARKIV.getCallBack())
                            .build()))
                .build();



        return inlineKeyboardMarkup;
    }

    @Override
    public void mainMenu() {
        //todo
    }

    @Override
    public void orderMenu() {
        //todo
    }

    @Override
    public void invoiceMenu() {
        //todo
    }

    @Override
    public void listActualOrder() {
        //todo
    }
}
