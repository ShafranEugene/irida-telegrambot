package com.github.iridatelegrambot.service.statususer;

import com.github.iridatelegrambot.entity.UserTelegram;

public interface MuteInviteService {
    boolean checkStatus(Long chatId);

    void setMute(Long chatId);

    void setMuteOfDay(Long chatId);
}
