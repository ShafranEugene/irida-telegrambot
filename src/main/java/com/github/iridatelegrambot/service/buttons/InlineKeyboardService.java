package com.github.iridatelegrambot.service.buttons;

import org.telegram.telegrambots.meta.api.objects.replykeyboard.InlineKeyboardMarkup;

public interface InlineKeyboardService {

    InlineKeyboardMarkup cityButtons(Object orderOrIvoice);

    void mainMenu();

    void orderMenu();

    void invoiceMenu();

    void listActualOrder();
}
