package com.github.iridatelegrambot.command;

import com.github.iridatelegrambot.entity.UserTelegram;
import com.github.iridatelegrambot.service.senders.CommandSenderService;
import com.github.iridatelegrambot.service.UserTelegramService;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.objects.Update;

import java.util.List;
@Component
public class StatCommand implements Command{

    private final CommandSenderService commandSenderService;
    private final UserTelegramService telegramService;
    private final CommandName commandName = CommandName.STAT;

    public final static String STAT_MESSAGE ="Количество активных пользователей : %s\nАктивные пользователеи:";

    public StatCommand(CommandSenderService commandSenderService, UserTelegramService telegramService) {
        this.commandSenderService = commandSenderService;
        this.telegramService = telegramService;
    }

    @Override
    public void execute(Update update) {
        List<UserTelegram> userTelegramList = telegramService.getAllActiveUser();
        StringBuilder text = new StringBuilder(STAT_MESSAGE);
        for(UserTelegram user : userTelegramList){
            text.append("\n\t - ").append(user.getUserName()).append(", ").append(user.getFirstName()).append(", Id:").append(user.getChatId()).append(", Админ: ");
            if(user.isAdmin()){
                text.append("Да;");
            } else {
                text.append("Нет;");
            }
        }
        int quantityUsers = telegramService.getAllActiveUser().size();
        commandSenderService.sendStatMenu(update.getMessage().getChatId(),String.format(text.toString(),quantityUsers));
    }

    @Override
    public CommandName getCommand() {
        return commandName;
    }
}
