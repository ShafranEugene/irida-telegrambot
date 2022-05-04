package com.github.iridatelegrambot.command;

import com.github.iridatelegrambot.service.send.SendMessageService;
import com.github.iridatelegrambot.service.statuswait.WaitDocument;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.objects.Update;

import static com.github.iridatelegrambot.service.statuswait.WaitTypeStatus.ADD;
@Component
public class AddOrderCommand implements Command{

    private final SendMessageService sendMessageService;
    private final CommandName commandName = CommandName.ADDORDER;

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

    @Override
    public CommandName getCommand() {
        return commandName;
    }
}
