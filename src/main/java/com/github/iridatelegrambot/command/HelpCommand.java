package com.github.iridatelegrambot.command;

import com.github.iridatelegrambot.service.send.CommandSenderService;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.objects.Update;
@Component
public class HelpCommand implements Command{

    private final CommandSenderService commandSenderService;
    private final CommandName commandName = CommandName.HELP;

    public final static String HELP_MESSAGE = String.format("Доступные команды \n\n"
                                                + "%s - начать работу со мной\n"
                                                + "%s - приостановить работу с ботом\n"
                                                + "%s - получить помощь\n"
                                                + "%s - получить информацию по работе бота\n"
                                                + "%s - добавить заказ на перемещение\n"
                                                + "%s - добавить накладную на перемещение\n"
                                                + "%s - получить список всех активных заказов\n"
                                                + "%s - руководство\n",
            CommandName.START.getCommandName(), CommandName.STOP.getCommandName(), CommandName.HELP.getCommandName(),
            CommandName.STAT.getCommandName(), CommandName.ADDORDER.getCommandName(),CommandName.ADDINVOICE.getCommandName(),
            CommandName.SHOWACTIVEORDER.getCommandName(),CommandName.GUIDE.getCommandName());

    public HelpCommand(CommandSenderService commandSenderService) {
        this.commandSenderService = commandSenderService;
    }

    @Override
    public void execute(Update update) {
        commandSenderService.sendMessage(update.getMessage().getChatId().toString(),HELP_MESSAGE);
    }

    @Override
    public CommandName getCommand() {
        return commandName;
    }
}
