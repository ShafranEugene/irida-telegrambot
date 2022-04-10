package com.github.iridatelegrambot.service.buttons;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.iridatelegrambot.entity.BondOrderToInvoice;
import com.github.iridatelegrambot.entity.Invoice;
import com.github.iridatelegrambot.entity.Order;
import com.github.iridatelegrambot.service.OrderService;
import org.aspectj.weaver.ast.Or;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.InlineKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.InlineKeyboardButton;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Service
public class InlineKeyboardServiceImpl implements InlineKeyboardService{

    private final OrderService orderService;
    @Autowired
    public InlineKeyboardServiceImpl(OrderService orderService) {
        this.orderService = orderService;
    }

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


    @Override
    public InlineKeyboardMarkup cityButtons(Invoice invoice){
        InlineKeyboardMarkup inlineKeyboardMarkup;
        List<CityName> cityNamesList = new ArrayList<>(Arrays.asList(CityName.values()));
        List<String> listJSONOrdersWithCity = new ArrayList<>();

        ObjectMapper objectMapper = new ObjectMapper();

        for (CityName cityName : cityNamesList) {
            invoice.setCity(cityName.getNameCity());

            try {
                String orderInJSON = "add_invoice:" + objectMapper.writeValueAsString(invoice);
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
    public InlineKeyboardMarkup markupActiveOrdersForInvoice(Invoice invoice){
        List<Order> orders = orderService.getAllOrdersActive();
        List<String> CallbackJson = new ArrayList<>();

        ObjectMapper objectMapper = new ObjectMapper();
        for(Order order : orders){
            try {
                BondOrderToInvoice bond = new BondOrderToInvoice(order.getId(),invoice.getId());
                String orderJson = "addOrdToInv:" + objectMapper.writeValueAsString(bond);
                CallbackJson.add(orderJson);
            } catch (JsonProcessingException e) {
                e.printStackTrace();
            }
        }
        InlineKeyboardMarkup inlineKeyboardMarkup = creatorMarkupWithOrders(orders,CallbackJson);
        return inlineKeyboardMarkup;
    }

    private InlineKeyboardMarkup creatorMarkupWithOrders(List<Order> orders,List<String> JSONdata){
        InlineKeyboardMarkup markup = new InlineKeyboardMarkup();

        List<List<InlineKeyboardButton>> rows = new ArrayList<>();

        int countRows = (int) Math.ceil(((double) orders.size())/3);
        for(int i = 0; i < countRows; i++){

            rows.add(new ArrayList<>());

            for (int j = i * 3; j < i * 3 + 3; j++){
                if(j >= orders.size()){
                    continue;
                }
                InlineKeyboardButton button = new InlineKeyboardButton();
                button.setText(orders.get(j).getNumber());
                button.setCallbackData(JSONdata.get(j));

                rows.get(i).add(button);
            }
        }

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
