package com.github.iridatelegrambot.service.send;

public interface SendMessageMainMenuService extends SendMessageService {

    void sendMainMenu(Long chatId, String messageText);

}
