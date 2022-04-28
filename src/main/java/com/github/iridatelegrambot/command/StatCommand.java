package com.github.iridatelegrambot.command;

import com.github.iridatelegrambot.entity.UserTelegram;
import com.github.iridatelegrambot.service.send.SendMessageService;
import com.github.iridatelegrambot.service.UserTelegramService;
import com.github.iridatelegrambot.service.send.SendMessageStatMenuService;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.objects.Update;

import java.util.List;
@Component
public class StatCommand implements Command{

    private final SendMessageStatMenuService sendMessage;
    private final UserTelegramService telegramService;
    private final CommandName commandName = CommandName.STAT;

    public final static String STAT_MESSAGE ="Количество активных пользователей : %s\nАктивные пользователеи:";

    public StatCommand(SendMessageStatMenuService sendMessage, UserTelegramService telegramService) {
        this.sendMessage = sendMessage;
        this.telegramService = telegramService;
    }

    @Override
    public void execute(Update update) {
        List<UserTelegram> userTelegramList = telegramService.getAllActiveUser();
        String text = STAT_MESSAGE;
        for(UserTelegram user : userTelegramList){
            text += "\n\t - " + user.getUserName() + ", " + user.getFirstName() + ", Id:" + user.getChatId() +
                    ", Админ: " + user.isAdmin() + ";";
        }
        int quantityUsers = telegramService.getAllActiveUser().size();
        sendMessage.sendMenuStat(update.getMessage().getChatId(),String.format(text,quantityUsers));
    }

    @Override
    public CommandName getCommand() {
        return commandName;
    }
}
