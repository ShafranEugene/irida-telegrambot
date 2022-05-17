package com.github.iridatelegrambot.command.CallbackCommand;

import com.github.iridatelegrambot.service.senders.CommandCallbackSenderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.objects.CallbackQuery;
@Component
public class NotFindCallbackCommand implements CallbackCommand {
    private CommandCallbackSenderService commandCallbackSenderService;
    private final CallbackCommandName commandName = CallbackCommandName.NOT_FIND;
    @Autowired
    public NotFindCallbackCommand(CommandCallbackSenderService commandCallbackSenderService) {
        this.commandCallbackSenderService = commandCallbackSenderService;
    }

    @Override
    public void execute(CallbackQuery callbackQuery) {
        Long chatId = callbackQuery.getMessage().getChatId();
        commandCallbackSenderService.sendMessage(chatId.toString(),"Команду не найдено.");
    }

    @Override
    public String getNameCommand() {
        return commandName.getName();
    }
}
