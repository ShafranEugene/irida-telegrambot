package com.github.iridatelegrambot.command.CallbackCommand;

import com.github.iridatelegrambot.entity.UserTelegram;
import com.github.iridatelegrambot.service.SendMessageService;
import com.github.iridatelegrambot.service.UserTelegramService;
import org.telegram.telegrambots.meta.api.objects.CallbackQuery;

import java.util.Optional;

public class AddStatusUserCallbackCommand implements CallbackCommand {
    private final SendMessageService sendMessageService;
    private final UserTelegramService userTelegramService;

    public AddStatusUserCallbackCommand(SendMessageService sendMessageService, UserTelegramService userTelegramService) {
        this.sendMessageService = sendMessageService;
        this.userTelegramService = userTelegramService;
    }

    @Override
    public void execute(CallbackQuery callbackQuery) {
        Long chatId = callbackQuery.getMessage().getChatId();
        String[] data = callbackQuery.getData().split(":");
        String chatIdUser = data[2];
        boolean setStatus = Boolean.parseBoolean(data[3]);

        Optional<UserTelegram> optionalUserTelegram = userTelegramService.findByChatId(Long.valueOf(chatIdUser));
        if(optionalUserTelegram.isEmpty()){
            sendMessageService.sendMessage(chatId.toString(),"Пользователь не найден.");
        } else if(setStatus){
            UserTelegram userTelegram = optionalUserTelegram.get();
            userTelegram.setActive(true);
            userTelegramService.save(userTelegram);
            sendMessageService.sendMessage(chatId.toString(),
                    "Пользователю \"" + userTelegram.getUserName() + "\" открыт доступ к боту.");
            sendMessageService.sendMessage(chatIdUser,"Администратор открыл Вам доступ к боту." + "" +
                    "\nДетальнее о возможностях боты можно ознакомится по команде /help");
        } else {
            UserTelegram userTelegram = optionalUserTelegram.get();
            userTelegram.setActive(false);
            userTelegramService.save(userTelegram);
            sendMessageService.sendMessage(chatId.toString(),
                    "Пользователю \"" + userTelegram.getUserName() + "\" закрыт доступ к боту.");
            sendMessageService.sendMessage(chatIdUser,"Администратор закрыл Вам доступ к боту.");
        }

    }
}
