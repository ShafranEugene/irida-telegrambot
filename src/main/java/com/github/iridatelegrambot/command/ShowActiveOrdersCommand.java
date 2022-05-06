package com.github.iridatelegrambot.command;

import com.github.iridatelegrambot.service.senders.CommandSenderService;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.objects.Update;
@Component
public class ShowActiveOrdersCommand implements Command {
    private final CommandSenderService commandSenderService;
    public static final String SHOW_ACTIVE_ORDERS_MESSAGE = "Все активные заказы:";
    private final CommandName commandName = CommandName.SHOWACTIVEORDER;

    public ShowActiveOrdersCommand(CommandSenderService commandSenderService) {
        this.commandSenderService = commandSenderService;
    }

    @Override
    public void execute(Update update) {
        Long chatId = update.getMessage().getChatId();
        commandSenderService.sendActiveOrders(chatId,SHOW_ACTIVE_ORDERS_MESSAGE);
    }

    @Override
    public CommandName getCommand() {
        return commandName;
    }
}
