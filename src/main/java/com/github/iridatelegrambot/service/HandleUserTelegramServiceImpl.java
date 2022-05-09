package com.github.iridatelegrambot.service;

import com.github.iridatelegrambot.entity.UserTelegram;
import com.github.iridatelegrambot.service.senders.SendMessageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.telegram.telegrambots.meta.api.objects.Update;

import java.util.Optional;

@Service
public class HandleUserTelegramServiceImpl implements HandleUserTelegramService{
    private final UserTelegramService userTelegramService;
    private final SendMessageService sendMessageService;
    @Autowired
    public HandleUserTelegramServiceImpl(UserTelegramService userTelegramService, SendMessageService sendMessageService) {
        this.userTelegramService = userTelegramService;
        this.sendMessageService = sendMessageService;
    }

    @Override
    public String toStringInfoForUser(Long chatId){
        Optional<UserTelegram> userTelegramOptional = userTelegramService.findByChatId(chatId);
        if(userTelegramOptional.isPresent()) {
            UserTelegram userTelegram = userTelegramOptional.get();
            String text = "Код: " + chatId +
                    "\nИмя: " + userTelegram.getFirstName() +
                    "\nЛогин: " + userTelegram.getUserName() +
                    "\nСтатус: ";
            if (userTelegram.isActive()) {
                text += "Активен";
            } else {
                text += "Неактивен";
            }
            if (userTelegram.isAdmin()) {
                text += "\nАдмин: Да";
            } else {
                text += "\nАдмин: Нет";
            }
            return text;
        } else {
            return "Пользователь не найден.";
        }
    }

    @Override
    public void setUserActiveStatus(Long chatId, Boolean status){
        Optional<UserTelegram> userTelegramOptional = userTelegramService.findByChatId(chatId);
        if(userTelegramOptional.isPresent()) {
            UserTelegram userTelegram = userTelegramOptional.get();
            userTelegram.setActive(status);
            userTelegramService.save(userTelegram);
        } else {
            userNotFind(chatId);
        }
    }

    @Override
    public void setUserAdminStatus(Long chatId, Boolean status){
        Optional<UserTelegram> userTelegramOptional = userTelegramService.findByChatId(chatId);
        if(userTelegramOptional.isPresent()) {
            UserTelegram userTelegram = userTelegramOptional.get();
            userTelegram.setAdmin(status);
            userTelegramService.save(userTelegram);
        } else {
            userNotFind(chatId);
        }
    }

    private void userNotFind(Long chatId){
        sendMessageService.sendMessage(chatId.toString(),"Пользователь не найден.");
    }

    @Override
    public Boolean getActiveStatusUser(Long chatId){
        Optional<UserTelegram> userTelegramOptional = userTelegramService.findByChatId(chatId);
        if(userTelegramOptional.isPresent()) {
            return userTelegramOptional.get().isActive();
        } else {
            userNotFind(chatId);
            return false;
        }
    }

    @Override
    public Boolean getAdminStatusUser(Long chatId){
        Optional<UserTelegram> userTelegramOptional = userTelegramService.findByChatId(chatId);
        if(userTelegramOptional.isPresent()) {
            return userTelegramOptional.get().isAdmin();
        } else {
            userNotFind(chatId);
            return false;
        }
    }

    @Override
    public Boolean checkUserOnEmpty(Long chatId){
        Optional<UserTelegram> userTelegramOptional = userTelegramService.findByChatId(chatId);
        return userTelegramOptional.isEmpty();
    }

    @Override
    public Boolean checkUserIsPresent(Long chatId){
        Optional<UserTelegram> userTelegramOptional = userTelegramService.findByChatId(chatId);
        return userTelegramOptional.isPresent();
    }

    @Override
    public Boolean checkOnFirstUser(Long chatId){
        return userTelegramService.getAllActiveUser().size() == 0;
    }

    @Override
    public void createUser(Update update){
        userTelegramService.findOrCreateUser(update);
    }
}
