package com.github.iridatelegrambot.command;

import com.github.iridatelegrambot.entity.UserTelegram;
import com.github.iridatelegrambot.service.SendMessageService;
import com.github.iridatelegrambot.service.UserTelegramService;
import org.telegram.telegrambots.meta.api.objects.Update;

import java.util.ArrayList;
import java.util.List;

public class StatCommand implements Command{

    private final SendMessageService sendMessage;
    private final UserTelegramService telegramService;

    public final static String STAT_MESSAGE ="Количество активных пользователей : %s\nАктивные пользователеи:";

    public StatCommand(SendMessageService sendMessage, UserTelegramService telegramService) {
        this.sendMessage = sendMessage;
        this.telegramService = telegramService;
    }

    @Override
    public void execute(Update update) {
        List<UserTelegram> userTelegramList = telegramService.getAllUser();
        String text = STAT_MESSAGE;
        for(UserTelegram user : userTelegramList){
            text += "\n\t - " + user.getUserName() + ", " + user.getFirstName() + ", Id:" + user.getChatId() + ";";
        }
        int quantityUsers = telegramService.getAllActiveUser().size();
        sendMessage.sendMenuStat(update.getMessage().getChatId(),String.format(text,quantityUsers));
    }
}
