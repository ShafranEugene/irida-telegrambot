package com.github.iridatelegrambot.command;

import com.github.iridatelegrambot.service.send.CommandSenderService;
import com.github.iridatelegrambot.service.send.SendMessageService;
import com.github.iridatelegrambot.service.statuswait.WaitDocument;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.objects.Update;

import static com.github.iridatelegrambot.service.statuswait.WaitTypeStatus.ADD;
@Component
public class AddInvoiceCommand implements Command{
    private final CommandSenderService commandSenderService;
    private final CommandName commandName = CommandName.ADDINVOICE;

    public static final String ADDINVOICE_MESSAGE = "Введите номер накладной на перемещение: ";
    public AddInvoiceCommand(CommandSenderService commandSenderService ) {
        this.commandSenderService = commandSenderService;
    }

    @Override
    public void execute(Update update) {
        Long chatId = update.getMessage().getChatId();
        WaitDocument.INVOICE.setWaitNumber(chatId,true,ADD);
        commandSenderService.sendMessage(chatId.toString(),ADDINVOICE_MESSAGE);
    }

    @Override
    public CommandName getCommand() {
        return commandName;
    }

}
