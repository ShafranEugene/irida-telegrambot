package com.github.iridatelegrambot.command;

public enum CommandName {

    START("/start"),
    STOP("/stop"),
    HELP("/help"),
    STAT("/stat"),
    ADDORDER("/add_order"),
    ADDINVOICE("/add_invoice"),
    SHOWACTIVEORDER("/show_active_orders"),
    NO("nocommand");

    private final String commandName;

    CommandName(String commandName) {
        this.commandName = commandName;
    }

    public String getCommandName() {
        return commandName;
    }
}
