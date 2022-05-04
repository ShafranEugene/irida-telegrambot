package com.github.iridatelegrambot.command;

import com.github.iridatelegrambot.service.send.SendMessageService;
import com.github.iridatelegrambot.service.statuswait.WaitDocument;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.objects.Update;

import static com.github.iridatelegrambot.service.statuswait.WaitTypeStatus.ADD;
@Component
public class AddInvoiceCommand implements Command{
    private final SendMessageService sendMessageService;
    private final CommandName commandName = CommandName.ADDINVOICE;

    public static final String ADDINVOICE_MESSAGE = "Введите номер накладной на перемещение: ";
    public AddInvoiceCommand(SendMessageService sendMessageService ) {
        this.sendMessageService = sendMessageService;
    }

    @Override
    public void execute(Update update) {
        Long chatId = update.getMessage().getChatId();
        WaitDocument.INVOICE.setWaitNumber(chatId,true,ADD);
        sendMessageService.sendMessage(chatId.toString(),ADDINVOICE_MESSAGE);
    }

    @Override
    public CommandName getCommand() {
        return commandName;
    }

}
