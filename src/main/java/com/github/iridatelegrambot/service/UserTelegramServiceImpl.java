package com.github.iridatelegrambot.service;

import com.github.iridatelegrambot.entity.ConditionBot;
import com.github.iridatelegrambot.entity.UserTelegram;
import com.github.iridatelegrambot.repository.UserTelegramRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.telegram.telegrambots.meta.api.objects.Update;

import java.util.List;
import java.util.Optional;

@Service
public class UserTelegramServiceImpl implements UserTelegramService {

    private final UserTelegramRepository repository;

    @Autowired
    public UserTelegramServiceImpl(UserTelegramRepository repository) {
        this.repository = repository;
    }

    @Override
    public void save(UserTelegram user) {
        repository.save(user);
    }

    @Override
    public List<UserTelegram> getAllActiveUser() {
        return repository.findAllByActiveTrue();
    }

    @Override
    public List<UserTelegram> getAllUser() {
        return repository.findAll();
    }

    @Override
    public Optional<UserTelegram> findByChatId(Long chatId) {
        return repository.findById(chatId);
    }

    @Override
    public UserTelegram findOrCreateUser(Update update){
        Long chatId = update.getMessage().getChatId();
        Optional<UserTelegram> userTelegramOptional = findByChatId(chatId);

        if(userTelegramOptional.isEmpty()){
            UserTelegram user = new UserTelegram();
            user.setActive(true);
            user.setChatId(chatId);
            user.setFirstName(update.getMessage().getChat().getFirstName());
            user.setUserName(update.getMessage().getChat().getUserName());
            ConditionBot conditionBot = new ConditionBot();
            conditionBot.setUserTelegram(user);
            user.setConditionBot(conditionBot);
            save(user);
            return user;
        } else {
            UserTelegram userTelegram = userTelegramOptional.get();
            userTelegram.setActive(true);
            save(userTelegram);
            return userTelegram;
        }
    }
}
