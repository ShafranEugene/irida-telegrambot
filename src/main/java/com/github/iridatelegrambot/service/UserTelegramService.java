package com.github.iridatelegrambot.service;

import com.github.iridatelegrambot.entity.UserTelegram;

import java.util.List;
import java.util.Optional;

public interface UserTelegramService {

    void save (UserTelegram user);

    List<UserTelegram> getAllActiveUser();

    List<UserTelegram> getAllUser();

    Optional<UserTelegram> findByChatId(Long ChatId);
}
