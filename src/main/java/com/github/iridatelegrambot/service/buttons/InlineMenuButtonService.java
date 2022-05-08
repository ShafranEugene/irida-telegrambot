package com.github.iridatelegrambot.service.buttons;

import com.github.iridatelegrambot.entity.Order;
import com.github.iridatelegrambot.service.statuswait.WaitDocument;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.InlineKeyboardMarkup;

public interface InlineMenuButtonService {
    InlineKeyboardMarkup showMenuStat();

    InlineKeyboardMarkup showMenuStatDetails(WaitDocument waitDocument);

    InlineKeyboardMarkup showMenuAdmin();

    InlineKeyboardMarkup showMenuOrder(Order order);
}
