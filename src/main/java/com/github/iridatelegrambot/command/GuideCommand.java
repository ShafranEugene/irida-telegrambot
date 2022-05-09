package com.github.iridatelegrambot.command;

import com.github.iridatelegrambot.service.senders.CommandSenderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.objects.Update;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;

@Component
public class GuideCommand implements Command {

    private final CommandSenderService commandSenderService;
    private final CommandName commandName = CommandName.GUIDE;
    @Autowired
    public GuideCommand(CommandSenderService commandSenderService) {
        this.commandSenderService = commandSenderService;
    }

    @Override
    public void execute(Update update) {
        String file = "src/main/resources/guide.txt";
        Long chatId = update.getMessage().getChatId();
        try {
            Path path = Paths.get(file);
            List<String> lines = Files.readAllLines(path);
            StringBuilder text = new StringBuilder();
            for(String line : lines){
                text.append(line).append("\n");
            }
            commandSenderService.sendMessage(chatId.toString(), text.toString());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public CommandName getCommand() {
        return commandName;
    }
}
