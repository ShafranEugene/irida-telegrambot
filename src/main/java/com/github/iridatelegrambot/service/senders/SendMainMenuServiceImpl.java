package com.github.iridatelegrambot.service.senders;

import com.github.iridatelegrambot.service.buttons.MenuButtonsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class SendMainMenuServiceImpl implements SendMainMenuService {

    private final SendMessageService sendMessageService;
    private final MenuButtonsService menuButtonsService;

    @Autowired
    public SendMainMenuServiceImpl(SendMessageService sendMessageService, MenuButtonsService menuButtonsService) {
        this.sendMessageService = sendMessageService;
        this.menuButtonsService = menuButtonsService;
    }

    @Override
    public void sendMainMenu(Long chatId, String message){
        sendMessageService.sendMessage(chatId.toString(),message,menuButtonsService.mainMenu());
    }
}
