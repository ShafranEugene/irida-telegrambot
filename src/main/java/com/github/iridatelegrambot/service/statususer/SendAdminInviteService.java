package com.github.iridatelegrambot.service.statususer;

import com.github.iridatelegrambot.entity.UserTelegram;

public interface SendAdminInviteService {

    void send(UserTelegram user);
}
