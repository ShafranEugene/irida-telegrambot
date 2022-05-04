package com.github.iridatelegrambot.command.CallbackCommand;

import com.github.iridatelegrambot.service.send.SendMessageService;
import com.github.iridatelegrambot.service.statuswait.WaitDocument;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.objects.CallbackQuery;

import static com.github.iridatelegrambot.service.statuswait.WaitTypeStatus.DELETE;
import static com.github.iridatelegrambot.service.statuswait.WaitTypeStatus.INFO;
@Component
public class StatDocumentCallbackCommand implements CallbackCommand {
    private final SendMessageService sendMessageService;
    private final CallbackCommandName commandName = CallbackCommandName.STAT_DOCUMENT;

    @Autowired
    public StatDocumentCallbackCommand(SendMessageService sendMessageService) {
        this.sendMessageService = sendMessageService;
    }

    @Override
    public void execute(CallbackQuery callbackQuery) {
        Long chatId = callbackQuery.getMessage().getChatId();
        String[] data = callbackQuery.getData().split(":");
        String typeDocument = data[1];
        String subType = data[2];

        if(subType.equals(INFO.getName())){
            if(typeDocument.equals(WaitDocument.ORDER.getName())){
                WaitDocument.ORDER.setWaitNumber(chatId,true, INFO);
            } else if(typeDocument.equals(WaitDocument.INVOICE.getName())){
                WaitDocument.INVOICE.setWaitNumber(chatId,true, INFO);
            }
        } else if(subType.equals(DELETE.getName())){
            if(typeDocument.equals(WaitDocument.ORDER.getName())){
                WaitDocument.ORDER.setWaitNumber(chatId,true,DELETE);
            } else if(typeDocument.equals(WaitDocument.INVOICE.getName())){
                WaitDocument.INVOICE.setWaitNumber(chatId,true, DELETE);
            }
        }
        String message = "Введите номер:";
        sendMessageService.sendMessage(chatId.toString(),message);
    }

    @Override
    public String getNameCommand() {
        return commandName.getName();
    }
}
