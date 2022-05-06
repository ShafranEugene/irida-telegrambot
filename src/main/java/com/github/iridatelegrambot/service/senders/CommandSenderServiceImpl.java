package com.github.iridatelegrambot.service.senders;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CommandSenderServiceImpl implements CommandSenderService {
    private final SendMessageService sendMessage;
    private final SendMainMenuService sendMainMenuService;
    private final SendStatMenuService sendStatMenu;
    private final SendOrderMenuService sendMessageService;

    @Autowired
    public CommandSenderServiceImpl(SendMessageService sendMessage, SendMainMenuService sendMainMenuService, SendStatMenuService sendStatMenu, SendOrderMenuService sendMessageService) {
        this.sendMessage = sendMessage;
        this.sendMainMenuService = sendMainMenuService;
        this.sendStatMenu = sendStatMenu;
        this.sendMessageService = sendMessageService;
    }

    @Override
    public void sendMainMenu(Long chatId, String message){
        sendMainMenuService.sendMainMenu(chatId,message);
    }

    @Override
    public void sendMessage(String chatId, String text){
        sendMessage.sendMessage(chatId,text);
    }

    @Override
    public void sendStatMenu(Long chatId, String text){
        sendStatMenu.sendMenuStat(chatId,text);
    }

    @Override
    public void sendActiveOrders(Long chatId, String message){
        sendMessageService.sendActiveOrders(chatId,message);
    }



}
