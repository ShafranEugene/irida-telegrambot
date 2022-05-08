package com.github.iridatelegrambot.service.buttons;

import org.telegram.telegrambots.meta.api.objects.replykeyboard.InlineKeyboardMarkup;

import java.util.Optional;

public interface InlineAdminButtonService {

    InlineKeyboardMarkup inviteForAdmin(Long chatIdUser);

    Optional<InlineKeyboardMarkup> showAllUsersForSetStatus(boolean status);

    InlineKeyboardMarkup showAllUsersForSetAdmin();

}
