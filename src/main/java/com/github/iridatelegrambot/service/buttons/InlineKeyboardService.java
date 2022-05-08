package com.github.iridatelegrambot.service.buttons;

import org.telegram.telegrambots.meta.api.objects.replykeyboard.InlineKeyboardMarkup;

import java.util.Map;

public interface InlineKeyboardService {

    InlineKeyboardMarkup createMenu(Map<String, String> TextAndCallbackData);

    InlineKeyboardMarkup createMenu(Map<String, String> textAndCallbackData, Integer amountOfButtonsInRow);
}
