package com.github.iridatelegrambot.command.CallbackCommand;

import com.github.iridatelegrambot.entity.UserTelegram;
import com.github.iridatelegrambot.service.senders.CommandCallbackSenderService;
import com.github.iridatelegrambot.service.UserTelegramService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.objects.CallbackQuery;

import java.util.Optional;
@Component
public class AdminSetStatusCallbackCommand implements CallbackCommand {
    private final CommandCallbackSenderService sendMessageService;
    private final UserTelegramService userTelegramService;
    private Long chatId;
    private Integer messageId;
    private final CallbackCommandName commandName = CallbackCommandName.ADMIN_MENU_SET_STATUS;
    @Autowired
    public AdminSetStatusCallbackCommand(CommandCallbackSenderService sendMessageService, UserTelegramService userTelegramService) {
        this.sendMessageService = sendMessageService;
        this.userTelegramService = userTelegramService;
    }

    @Override
    public void execute(CallbackQuery callbackQuery) {
        messageId = callbackQuery.getMessage().getMessageId();
        chatId = callbackQuery.getMessage().getChatId();
        String[] data = callbackQuery.getData().split(":");

        if(data[1].equals("setActive")){
            setStatusUser(Long.valueOf(data[2]),Boolean.parseBoolean(data[3]));
        } else if(data[1].equals("setAdmin")){
            setAdmin(Long.valueOf(data[2]));
        }
    }

    @Override
    public String getNameCommand() {
        return commandName.getName();
    }

    private void setStatusUser(Long chatIdUser, boolean status){
        Optional<UserTelegram> userOptional = userTelegramService.findByChatId(chatIdUser);
        if(userOptional.isPresent()){
            UserTelegram userTelegram = userOptional.get();
            userTelegram.setActive(status);
            userTelegramService.save(userTelegram);
            sendMessageService.deleteMessage(chatId,messageId);
            sendMessageService.sendMessage(chatId.toString(),"Готово, статус пользователя был изменен.");
        } else {
            sendMessageService.sendMessage(chatId.toString(),"Не смог найти такого пользователя.");
        }
    }

    private void setAdmin(Long chatIdUser){
        Optional<UserTelegram> userOptional = userTelegramService.findByChatId(chatIdUser);
        if(userOptional.isPresent()){
            UserTelegram userTelegram = userOptional.get();
            userTelegram.setAdmin(true);
            userTelegramService.save(userTelegram);
            sendMessageService.deleteMessage(chatId,messageId);
            sendMessageService.sendMessage(chatId.toString(),"Готово. Пользователю были выданы права администратора.");
            sendMessageService.sendMessage(chatIdUser.toString(),"Вам было выдано права администратора");
        } else {
            sendMessageService.sendMessage(chatId.toString(),"Не смог найти такого пользователя.");
        }
    }
}
