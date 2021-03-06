package com.github.iridatelegrambot.service.buttons;

import com.github.iridatelegrambot.entity.Invoice;
import com.github.iridatelegrambot.entity.Order;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.InlineKeyboardMarkup;

import java.util.Optional;

public interface InlineDocumentButtonService {
    InlineKeyboardMarkup cityButtons(Order order);

    InlineKeyboardMarkup cityButtons(Invoice invoice);

    InlineKeyboardMarkup markupActiveOrdersForInvoice(Invoice invoice);

    InlineKeyboardMarkup closeOrder(Order order);

    Optional<InlineKeyboardMarkup> showActiveOrders();
}
