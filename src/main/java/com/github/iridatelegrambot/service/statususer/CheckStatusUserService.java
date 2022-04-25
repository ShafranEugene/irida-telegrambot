package com.github.iridatelegrambot.service.statususer;

import org.telegram.telegrambots.meta.api.objects.Update;

public interface CheckStatusUserService {

    boolean check(Update update);
}
