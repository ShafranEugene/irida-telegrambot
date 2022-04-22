package com.github.iridatelegrambot.command;

import com.github.iridatelegrambot.service.SendMessageService;
import com.github.iridatelegrambot.service.statuswait.WaitDocument;
import org.telegram.telegrambots.meta.api.objects.Update;

import static com.github.iridatelegrambot.service.statuswait.WaitTypeStatus.ADD;

public class AddOrderCommand implements Command{

    private final SendMessageService sendMessageService;

    public static final String ADDORDER_MESSAGE = "Введите номер заказа на перемещение: ";
    public AddOrderCommand(SendMessageService sendMessageService) {
        this.sendMessageService = sendMessageService;
    }

    @Override
    public void execute(Update update) {
        Long chatId = update.getMessage().getChatId();
        WaitDocument.ORDER.setWaitNumber(chatId,true,ADD);
        sendMessageService.sendMessage(chatId.toString(),ADDORDER_MESSAGE);
    }
}
