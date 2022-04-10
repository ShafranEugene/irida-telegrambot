package com.github.iridatelegrambot.command;

public enum CommandName {

    START("/start"),
    STOP("/stop"),
    HELP("/help"),
    STAT("/stat"),
    ADDORDER("/addorder"),
    ADDINVOICE("/addinvoice"),
    NO("nocommand");

    private final String commandName;

    CommandName(String commandName) {
        this.commandName = commandName;
    }

    public String getCommandName() {
        return commandName;
    }
}
