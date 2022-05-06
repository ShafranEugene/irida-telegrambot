package com.github.iridatelegrambot.command;

import com.github.iridatelegrambot.service.senders.CommandSenderService;
import com.github.iridatelegrambot.service.UserTelegramService;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.objects.Update;
@Component
public class StopCommand implements Command{

    private final CommandSenderService commandSenderService;
    private final UserTelegramService userTelegramService;
    private final CommandName commandName = CommandName.STOP;

    public final static String STOP_MESSAGE = "Бот остановлен.";

    public StopCommand(CommandSenderService commandSenderService, UserTelegramService userTelegramService) {
        this.commandSenderService = commandSenderService;
        this.userTelegramService = userTelegramService;
    }

    @Override
    public void execute(Update update) {
        commandSenderService.sendMessage(update.getMessage().getChatId().toString(),STOP_MESSAGE);

        Long chatId = update.getMessage().getChatId();

        userTelegramService.findByChatId(chatId).ifPresent(
                userTelegram -> {
                    userTelegram.setActive(false);
                    userTelegramService.save(userTelegram);
                });
    }

    @Override
    public CommandName getCommand() {
        return commandName;
    }
}
