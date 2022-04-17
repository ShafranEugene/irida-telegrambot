package com.github.iridatelegrambot.service.buttons;

import com.github.iridatelegrambot.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.InlineKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.ReplyKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.KeyboardRow;

import java.util.ArrayList;
import java.util.List;
@Service
public class MenuButtonsServiceImpl implements MenuButtonsService{

    private OrderService orderService;
    @Autowired
    public MenuButtonsServiceImpl(OrderService orderService){
        this.orderService = orderService;
    }



    @Override
    public ReplyKeyboardMarkup mainMenu() {
        ReplyKeyboardMarkup replyKeyboardMarkup = new ReplyKeyboardMarkup();
        List<KeyboardRow> rows = new ArrayList<>();
        CommandNameForButtons[] commands = CommandNameForButtons.values();

        for (CommandNameForButtons command : commands){
            KeyboardRow keyboardButtons = new KeyboardRow();
            keyboardButtons.add(command.getName());
            rows.add(keyboardButtons);
        }

        replyKeyboardMarkup.setKeyboard(rows);
        return replyKeyboardMarkup;
    }

    @Override
    public InlineKeyboardMarkup listActualOrder() {

        return null;
    }

    @Override
    public InlineKeyboardMarkup orderMenu() {
        return null;
    }

    @Override
    public InlineKeyboardMarkup invoiceMenu() {
        return null;
    }

}
