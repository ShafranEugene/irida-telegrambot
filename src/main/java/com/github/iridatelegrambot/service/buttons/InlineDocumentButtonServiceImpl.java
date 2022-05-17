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

import java.util.*;

import static com.github.iridatelegrambot.command.CallbackCommand.CallbackCommandName.*;
@Service
public class InlineDocumentButtonServiceImpl implements  InlineDocumentButtonService{
    private final OrderService orderService;
    private final InlineKeyboardService inlineKeyboardService;

    @Autowired
    public InlineDocumentButtonServiceImpl(OrderService orderService,InlineKeyboardService inlineKeyboardService) {
        this.inlineKeyboardService = inlineKeyboardService;
        this.orderService = orderService;
    }

    @Override
    public InlineKeyboardMarkup cityButtons(Order order){
        List<CityName> cityNamesList = new ArrayList<>(Arrays.asList(CityName.values()));
        Map<String,String> mapData = new HashMap<>();
        ObjectMapper objectMapper = new ObjectMapper();
        for (CityName cityName : cityNamesList) {
            order.setCity(cityName.getNameCity());
            try {
                String orderInJSON = ADD_ORDER.getNameForService() + objectMapper.writeValueAsString(order);
                mapData.put(cityName.getNameCity(),orderInJSON);
            } catch (JsonProcessingException e) {
                e.printStackTrace();
            }
        }

        String cancel = CallbackCommandName.CANCEL.getNameForService() + "order:id:" + order.getId();
        CANCEL.setSubCommand("order");
        mapData.put("Отмена",cancel);
        return inlineKeyboardService.createMenu(mapData,3);
    }


    @Override
    public InlineKeyboardMarkup cityButtons(Invoice invoice){
        List<CityName> cityNamesList = new ArrayList<>(Arrays.asList(CityName.values()));
        Map<String,String> mapData = new HashMap<>();
        ObjectMapper objectMapper = new ObjectMapper();
        for (CityName cityName : cityNamesList) {
            invoice.setCity(cityName.getNameCity());

            try {
                String orderInJSON = ADD_INVOICE.getNameForService() + objectMapper.writeValueAsString(invoice);
                mapData.put(cityName.getNameCity(),orderInJSON);
            } catch (JsonProcessingException e) {
                e.printStackTrace();
            }
        }

        String cancel = CANCEL.getNameForService() + "invoice:id:" + invoice.getId();
        CANCEL.setSubCommand("invoice");
        mapData.put("Отмена",cancel);
        return inlineKeyboardService.createMenu(mapData,3);
    }


    @Override
    public InlineKeyboardMarkup markupActiveOrdersForInvoice(Invoice invoice){
        List<Order> orders = orderService.getAllOrdersActive();
        TreeMap<String,String> mapData = new TreeMap<>();
        ObjectMapper objectMapper = new ObjectMapper();
        for(Order order : orders){
            try {
                BondOrderToInvoice bond = new BondOrderToInvoice(order.getId(),invoice.getId());
                String orderJson = ADD_ORDER_TO_INVOICE.getNameForService() + objectMapper.writeValueAsString(bond);
                mapData.put(order.getNumber(),orderJson);
            } catch (JsonProcessingException e) {
                e.printStackTrace();
            }
        }
        return inlineKeyboardService.createMenu(mapData,3);
    }

    @Override
    public InlineKeyboardMarkup closeOrder(Order order){
        TreeMap<String,String> mapData = new TreeMap<>();
        mapData.put("Да",CLOSE_ORDER.getNameForService() + order.getId() + ":false");
        mapData.put("Нет",CLOSE_ORDER.getNameForService() + order.getId() + ":true");
        return inlineKeyboardService.createMenu(mapData,2);
    }

    @Override
    public InlineKeyboardMarkup showActiveOrders(){
        List<Order> orders = orderService.getAllOrdersActive();
        TreeMap<String,String> mapData = new TreeMap<>();

        for(Order order : orders){
            String data = SHOW_ORDER.getNameForService() + "id:" + order.getId();
            mapData.put(order.getNumber(),data);
        }
        return inlineKeyboardService.createMenu(mapData,3);
    }
}
