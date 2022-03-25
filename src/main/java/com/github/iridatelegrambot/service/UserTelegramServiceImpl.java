package com.github.iridatelegrambot.service;

import com.github.iridatelegrambot.entity.UserTelegram;
import com.github.iridatelegrambot.repository.UserTelegramRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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
}
