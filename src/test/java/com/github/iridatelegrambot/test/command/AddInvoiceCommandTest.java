package com.github.iridatelegrambot.test.command;

import com.github.iridatelegrambot.command.AddInvoiceCommand;
import com.github.iridatelegrambot.command.Command;

import static com.github.iridatelegrambot.command.AddInvoiceCommand.ADDINVOICE_MESSAGE;
import static com.github.iridatelegrambot.command.CommandName.ADDINVOICE;

public class AddInvoiceCommandTest extends AbstractCommandTest {
    @Override
    String getCommandName() {
        return ADDINVOICE.getCommandName();
    }

    @Override
    String getCommandMessage() {
        return ADDINVOICE_MESSAGE;
    }

    @Override
    Command getCommand() {
        return new AddInvoiceCommand(commandSenderService);
    }
}
