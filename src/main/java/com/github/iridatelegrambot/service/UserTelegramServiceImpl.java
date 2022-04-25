package com.github.iridatelegrambot.service;

import com.github.iridatelegrambot.entity.UserTelegram;
import com.github.iridatelegrambot.repository.UserTelegramRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.telegram.telegrambots.meta.api.objects.Update;

import java.util.List;
import java.util.Optional;

@Service
public class UserTelegramServiceImpl implements UserTelegramService {

    private final UserTelegramRepository repository;
    private final List<String> adminList;

    @Autowired
    public UserTelegramServiceImpl(UserTelegramRepository repository, @Value("#{'${bot.admin}'.split(',')}") List<String> adminList) {
        this.repository = repository;
        this.adminList = adminList;
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
    public List<UserTelegram> findAllAdmin(){
        return repository.findAllByAdminTrue();
    }

    @Override
    public UserTelegram findOrCreateUser(Update update){
        Long chatId;
        if(update.hasCallbackQuery()){
            chatId = update.getCallbackQuery().getMessage().getChatId();
        } else {
            chatId = update.getMessage().getChatId();
        }

        Optional<UserTelegram> userTelegramOptional = findByChatId(chatId);

        if(userTelegramOptional.isEmpty()){
            UserTelegram user = new UserTelegram();
            user.setActive(false);
            user.setChatId(chatId);
            user.setFirstName(update.getMessage().getChat().getFirstName());
            user.setUserName(update.getMessage().getChat().getUserName());
            if(checkForAdmin(update.getMessage().getChat().getUserName())){
                user.setAdmin(true);
            }
            save(user);
            return user;
        } else {
            UserTelegram userTelegram = userTelegramOptional.get();
            if(checkForAdmin(update.getMessage().getChat().getUserName())){
                userTelegram.setAdmin(true);
            }
            save(userTelegram);
            return userTelegram;
        }
    }

    private boolean checkForAdmin(String userName){
        for(String name : adminList){
            if(name.equals(userName)){
                return true;
            }
        }
        return false;
    }
}
