package com.github.iridatelegrambot.test.command;

import com.github.iridatelegrambot.command.Command;
import com.github.iridatelegrambot.command.CommandName;
import com.github.iridatelegrambot.command.GuideCommand;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;

public class GuideCommandTest extends AbstractCommandTest {
    @Override
    String getCommandName() {
        return CommandName.GUIDE.getCommandName();
    }

    @Override
    String getCommandMessage() {
        String file = "src/main/resources/guide.txt";
        StringBuilder text = new StringBuilder();
        try {
            Path path = Paths.get(file);
            List<String> lines = Files.readAllLines(path);
            for(String line : lines){
                text.append(line).append("\n");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return text.toString();
    }

    @Override
    Command getCommand() {
        return new GuideCommand(commandSenderService);
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
