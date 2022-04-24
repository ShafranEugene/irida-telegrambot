package com.github.iridatelegrambot.service.buttons;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.iridatelegrambot.command.CallbackCommand.CallbackCommandName;
import com.github.iridatelegrambot.entity.BondOrderToInvoice;
import com.github.iridatelegrambot.entity.Invoice;
import com.github.iridatelegrambot.entity.Order;
import com.github.iridatelegrambot.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.InlineKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.InlineKeyboardButton;

import java.util.*;

import static com.github.iridatelegrambot.command.CallbackCommand.CallbackCommandName.*;
import static com.github.iridatelegrambot.service.statuswait.WaitTypeStatus.DELETE;
import static com.github.iridatelegrambot.service.statuswait.WaitTypeStatus.INFO;

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
                String orderInJSON = ADD_ORDER.getNameForService() + objectMapper.writeValueAsString(order);
                listJSONOrdersWithCity.add(orderInJSON);
            } catch (JsonProcessingException e) {
                e.printStackTrace();
            }
        }

        String cancel = CallbackCommandName.CANCEL.getNameForService() + "order:id:" + order.getId();
        listJSONOrdersWithCity.add(cancel);

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
                String orderInJSON = ADD_INVOICE.getNameForService() + objectMapper.writeValueAsString(invoice);
                listJSONOrdersWithCity.add(orderInJSON);
            } catch (JsonProcessingException e) {
                e.printStackTrace();
            }
        }

        String cancel = CANCEL.getNameForService() + "invoice:id:" + invoice.getId();
        listJSONOrdersWithCity.add(cancel);

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
        buttonCancel.setCallbackData(listJSONOWithCity.get(listJSONOWithCity.size() - 1));
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
                String orderJson = ADD_ORDER_TO_INVOICE.getNameForService() + objectMapper.writeValueAsString(bond);
                CallbackJson.add(orderJson);
            } catch (JsonProcessingException e) {
                e.printStackTrace();
            }
        }
        return creatorMarkupWithOrders(orders,CallbackJson);
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
    public InlineKeyboardMarkup closeOrder(Order order){
        List<List<InlineKeyboardButton>> rows = new ArrayList<>();
        List<InlineKeyboardButton> row = new ArrayList<>();

        InlineKeyboardButton buttonYes = new InlineKeyboardButton();
        InlineKeyboardButton buttonNo = new InlineKeyboardButton();

        buttonYes.setText("Да");
        buttonNo.setText("Нет");

        buttonYes.setCallbackData(CLOSE_ORDER.getNameForService() + order.getId() + ":false");
        buttonNo.setCallbackData(CLOSE_ORDER.getNameForService() + order.getId() + ":true");

        row.add(buttonYes);
        row.add(buttonNo);
        rows.add(row);

        InlineKeyboardMarkup inlineKeyboardMarkup = new InlineKeyboardMarkup();
        inlineKeyboardMarkup.setKeyboard(rows);
        return inlineKeyboardMarkup;
    }

    @Override
    public InlineKeyboardMarkup showActiveOrders(){
        List<Order> orders = orderService.getAllOrdersActive();
        List<String> callbackOrders = new ArrayList<>();

        for(Order order : orders){
            String text = SHOW_ORDER.getNameForService() + "id:" + order.getId();
            callbackOrders.add(text);
        }
        return creatorMarkupWithOrders(orders,callbackOrders);
    }

    @Override
    public InlineKeyboardMarkup showMenuOrder(Order order){
        HashMap<String,String> buttonsMap = new HashMap<>();
        int idOrder = order.getId();
        buttonsMap.put("Добавить накладную на перемещение",ORDER_MENU.getNameForService() + "addinvoice:id:" + idOrder);
        buttonsMap.put("Удалить заказ",ORDER_MENU.getNameForService() + "delete:id:" + idOrder);
        return createMenu(buttonsMap);
    }

    @Override
    public InlineKeyboardMarkup showMenuStat(){
        HashMap<String,String> buttonsMap = new HashMap<>();
        buttonsMap.put("Информация о всех заказах", STAT_MENU.getNameForService() + "infoAllOrders");
        buttonsMap.put("Информация о всех накладных на перемещение", STAT_MENU.getNameForService() + "infoAllInvoice");
        return createMenu(buttonsMap);
    }

    private InlineKeyboardMarkup createMenu(HashMap<String,String> TextAndCallbackData){
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
    public InlineKeyboardMarkup showMenuStatDetails(String typeDocument){
        HashMap<String,String> buttonsMap = new HashMap<>();
        buttonsMap.put("Получить более детальную информацию по накладной",STAT_MENU.getNameForService() + typeDocument + ":" + INFO.getName());
        buttonsMap.put("Удалить накладную",STAT_MENU.getNameForService() + typeDocument + ":" + DELETE.getName());
        return createMenu(buttonsMap);
    }
}
