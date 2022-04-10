package com.github.iridatelegrambot.command;

import com.github.iridatelegrambot.bot.CheckUpdateOnPost;
import com.github.iridatelegrambot.service.SendMessageService;
import org.telegram.telegrambots.meta.api.objects.Update;

public class AddInvoiceCommand implements Command{
    private final SendMessageService sendMessageService;
    private final CheckUpdateOnPost checkUpdateOnPost;

    private static final String ADDINVOICE_MESSAGE = "Введите номер накладной на перемещение: ";
    public AddInvoiceCommand(SendMessageService sendMessageService,CheckUpdateOnPost checkUpdateOnPost) {
        this.sendMessageService = sendMessageService;
        this.checkUpdateOnPost = checkUpdateOnPost;
    }

    @Override
    public void execute(Update update) {
        Long chatId = update.getMessage().getChatId();
        checkUpdateOnPost.setStatusInvoice(chatId,true);
        sendMessageService.sendMessage(chatId.toString(),ADDINVOICE_MESSAGE);
    }
}
