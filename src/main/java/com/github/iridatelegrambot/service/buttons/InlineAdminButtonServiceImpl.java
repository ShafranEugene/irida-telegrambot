package com.github.iridatelegrambot.service.buttons;

import com.github.iridatelegrambot.entity.UserTelegram;
import com.github.iridatelegrambot.service.UserTelegramService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.InlineKeyboardMarkup;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

import static com.github.iridatelegrambot.command.CallbackCommand.CallbackCommandName.ADD_STATUS_USER;
import static com.github.iridatelegrambot.command.CallbackCommand.CallbackCommandName.ADMIN_MENU_SET_STATUS;
@Service
public class InlineAdminButtonServiceImpl implements InlineAdminButtonService {
    private final InlineKeyboardService inlineKeyboardService;
    private final UserTelegramService userTelegramService;

    @Autowired
    public InlineAdminButtonServiceImpl(InlineKeyboardService inlineKeyboardService, UserTelegramService userTelegramService) {
        this.inlineKeyboardService = inlineKeyboardService;
        this.userTelegramService = userTelegramService;
    }

    @Override
    public InlineKeyboardMarkup inviteForAdmin(Long chatIdUser){
        Map<String,String> buttonsMap = new HashMap<>();
        buttonsMap.put("Дать доступ",ADD_STATUS_USER.getNameForService() + "chatId:" + chatIdUser + ":true");
        buttonsMap.put("Отклонить",ADD_STATUS_USER.getNameForService() + "chatId:" + chatIdUser + ":false");
        return inlineKeyboardService.createMenu(buttonsMap);
    }

    @Override
    public Optional<InlineKeyboardMarkup> showAllUsersForSetStatus(boolean status) {
        Map<String, String> buttonsMap = new HashMap<>();
        for (UserTelegram userTelegram : userTelegramService.getAllUser()) {
            if (status) {
                if (!userTelegram.isActive()) {
                    buttonsMap.put(userTelegram.getUserName(), ADMIN_MENU_SET_STATUS.getNameForService() + "setActive:" +
                            userTelegram.getChatId() + ":true");
                }
            } else {
                if (userTelegram.isActive()) {
                    buttonsMap.put(userTelegram.getUserName(), ADMIN_MENU_SET_STATUS.getNameForService() + "setActive:" +
                            userTelegram.getChatId() + ":false");
                }
            }
        }
        if(buttonsMap.size() == 0){
            return Optional.empty();
        }
        return Optional.of(inlineKeyboardService.createMenu(buttonsMap));
    }

    @Override
    public InlineKeyboardMarkup showAllUsersForSetAdmin(){
        Map<String,String> buttonsMap = new HashMap<>();
        for(UserTelegram userTelegram : userTelegramService.getAllUser()){
            buttonsMap.put(userTelegram.getUserName(),ADMIN_MENU_SET_STATUS.getNameForService() + "setAdmin:" +
                    userTelegram.getChatId() + ":true");
        }
        return inlineKeyboardService.createMenu(buttonsMap);
    }
}
