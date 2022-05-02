package com.github.iridatelegrambot.service.buttons;

import com.github.iridatelegrambot.entity.Invoice;
import com.github.iridatelegrambot.entity.Order;
import com.github.iridatelegrambot.service.statuswait.WaitDocument;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.InlineKeyboardMarkup;

import java.util.Map;
import java.util.Optional;

public interface InlineKeyboardService {

    InlineKeyboardMarkup cityButtons(Order order);

    InlineKeyboardMarkup cityButtons(Invoice invoice);

    InlineKeyboardMarkup markupActiveOrdersForInvoice(Invoice invoice);

    InlineKeyboardMarkup closeOrder(Order order);

    InlineKeyboardMarkup showActiveOrders();

    InlineKeyboardMarkup showMenuOrder(Order order);

    InlineKeyboardMarkup showMenuStat();

    InlineKeyboardMarkup createMenu(Map<String, String> TextAndCallbackData);

    InlineKeyboardMarkup showMenuStatDetails(WaitDocument waitDocument);

    InlineKeyboardMarkup inviteForAdmin(Long chatIdUser);

    InlineKeyboardMarkup showMenuAdmin();

    Optional<InlineKeyboardMarkup> showAllUsersForSetStatus(boolean status);

    InlineKeyboardMarkup showAllUsersForSetAdmin();
}
