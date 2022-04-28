package com.github.iridatelegrambot.command;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.objects.Update;
@Component
public interface Command {
    void execute(Update update);
    CommandName getCommand();
    @Autowired
    default void containerFiller(CommandContainer commandContainer){
        commandContainer.setCommand(this);
    }
}
