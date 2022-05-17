package com.github.iridatelegrambot.service;

import org.telegram.telegrambots.meta.api.objects.Update;

public interface HandleUserTelegramService {
    String toStringInfoForUser(Long chatId);

    void setUserActiveStatus(Long chatId, Boolean status);

    void setUserAdminStatus(Long chatId, Boolean status);

    Boolean getActiveStatusUser(Long chatId);

    Boolean getAdminStatusUser(Long chatId);

    Boolean checkUserOnEmpty(Long chatId);

    Boolean checkUserIsPresent(Long chatId);

    Boolean checkOnFirstUser(Long chatId);

    void createUser(Update update);
}
