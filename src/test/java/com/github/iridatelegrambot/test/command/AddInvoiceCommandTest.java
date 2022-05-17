package com.github.iridatelegrambot.test.command;

import com.github.iridatelegrambot.command.AddInvoiceCommand;
import com.github.iridatelegrambot.command.Command;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

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
    @Test
    @Override
    void shouldExecuteCommand() {
        //when
        getCommand().execute(createUpdate());
        //then
        Mockito.verify(commandSenderService).sendMessage(chatId.toString(),getCommandMessage());
    }
}
