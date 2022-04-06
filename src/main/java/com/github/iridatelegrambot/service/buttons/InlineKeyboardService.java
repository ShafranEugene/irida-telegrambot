package com.github.iridatelegrambot.service.buttons;

import com.github.iridatelegrambot.entity.Order;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.InlineKeyboardMarkup;

public interface InlineKeyboardService {

    InlineKeyboardMarkup cityButtons(Order order);

    void mainMenu();

    void orderMenu();

    void invoiceMenu();

    void listActualOrder();
}
