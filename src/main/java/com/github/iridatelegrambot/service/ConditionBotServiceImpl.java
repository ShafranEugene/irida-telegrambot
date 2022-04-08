package com.github.iridatelegrambot.service;

import com.github.iridatelegrambot.entity.ConditionBot;
import com.github.iridatelegrambot.entity.UserTelegram;
import com.github.iridatelegrambot.repository.ConditionBotRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.telegram.telegrambots.meta.api.objects.User;

import java.util.Optional;
@Service
public class ConditionBotServiceImpl implements ConditionBotService {

    private final ConditionBotRepository conditionBotRepository;

    @Autowired
    public ConditionBotServiceImpl(ConditionBotRepository conditionBotRepository){
        this.conditionBotRepository = conditionBotRepository;
    }
    @Override
    public void save(ConditionBot conditionBot) {
        conditionBotRepository.save(conditionBot);
    }

    @Override
    public Optional<ConditionBot> getConditionById(int id) {
        return conditionBotRepository.findById(id);
    }

}
