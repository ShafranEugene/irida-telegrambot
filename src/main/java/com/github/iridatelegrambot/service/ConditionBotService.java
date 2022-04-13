package com.github.iridatelegrambot.service;

import com.github.iridatelegrambot.entity.ConditionBot;

import java.util.Optional;

public interface ConditionBotService {
    void save(ConditionBot conditionBot);

    Optional<ConditionBot> getConditionById(int id);
}
