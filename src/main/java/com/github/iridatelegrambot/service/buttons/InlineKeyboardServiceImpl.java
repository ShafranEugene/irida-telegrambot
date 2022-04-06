package com.github.iridatelegrambot.service.buttons;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.iridatelegrambot.entity.Order;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.InlineKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.InlineKeyboardButton;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;


public class InlineKeyboardServiceImpl implements InlineKeyboardService{
    @Override
    public InlineKeyboardMarkup cityButtons(Order order){
        InlineKeyboardMarkup inlineKeyboardMarkup;

        List<CityName> cityNamesList = new ArrayList<>(Arrays.asList(CityName.values()));

        List<String> listJSONOrdersWithCity = new ArrayList<>();

        ObjectMapper objectMapper = new ObjectMapper();

        for (CityName cityName : cityNamesList) {
            order.setCity(cityName.getNameCity());

            try {
                String orderInJSON = "add_order:" + objectMapper.writeValueAsString(order);
                listJSONOrdersWithCity.add(orderInJSON);
            } catch (JsonProcessingException e) {
                e.printStackTrace();
            }
        }

        inlineKeyboardMarkup = creatorMarkupWithCity(cityNamesList,listJSONOrdersWithCity);

        return inlineKeyboardMarkup;
    }


    private InlineKeyboardMarkup creatorMarkupWithCity(List<CityName> cityNamesList, List<String> listJSONOWithCity){
        InlineKeyboardMarkup markup = new InlineKeyboardMarkup();

        List<List<InlineKeyboardButton>> rows = new ArrayList<>();

        int countRows = (int) Math.ceil(((double) cityNamesList.size())/3);
        for(int i = 0; i < countRows; i++){

            rows.add(new ArrayList<>());

            for (int j = i * 3; j < i * 3 + 3; j++){
                if(j >= cityNamesList.size()){
                    continue;
                }
                InlineKeyboardButton button = new InlineKeyboardButton();
                button.setText(cityNamesList.get(j).getNameCity());
                button.setCallbackData(listJSONOWithCity.get(j));

                rows.get(i).add(button);
            }
        }

        InlineKeyboardButton buttonCancel = new InlineKeyboardButton();
        buttonCancel.setText("Отмена");
        buttonCancel.setCallbackData("Cancel");
        rows.get(countRows - 1).add(buttonCancel);

        markup.setKeyboard(rows);
        return markup;
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
