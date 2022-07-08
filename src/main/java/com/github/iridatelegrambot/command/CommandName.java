package com.github.iridatelegrambot.command;

public enum CommandName {

    START("/start"),
    STOP("/stop"),
    HELP("/help"),
    STAT("/stat"),
    ADDORDER("/add_order"),
    ADDINVOICE("/add_invoice"),
    SHOWACTIVEORDER("/show_active_orders"),
    GUIDE("/guide"),
    SETALARM("/set_alarm"),
    NO("nocommand");

    private final String firstName;


    CommandName(String firstName) {
        this.firstName = firstName;
    }

    public String getCommandName() {
        return firstName;
    }

}
