package com.github.iridatelegrambot.command;

import com.github.iridatelegrambot.service.send.SendMessageService;
import org.telegram.telegrambots.meta.api.objects.Update;

public class HelpCommand implements Command{

    private final SendMessageService sendMessage;

    public final static String HELP_MESSAGE = String.format("Доступные команды \n\n"
                                                + "%s - начать работу со мной\n"
                                                + "%s - приостановить работу\n"
                                                + "%s - получить помощь\n"
                                                + "%s - получить информацию по работе бота\n"
                                                + "%s - добавить заказ на перемещение\n"
                                                + "%s - добавить накладную на перемещение\n",
            CommandName.START.getCommandName(), CommandName.STOP.getCommandName(), CommandName.HELP.getCommandName(),
            CommandName.STAT.getCommandName(), CommandName.ADDORDER.getCommandName(),CommandName.ADDINVOICE.getCommandName());

    public HelpCommand(SendMessageService sendMessage) {
        this.sendMessage = sendMessage;
    }

    @Override
    public void execute(Update update) {
        sendMessage.sendMessage(update.getMessage().getChatId().toString(),HELP_MESSAGE);
    }
}
