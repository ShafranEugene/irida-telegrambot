package com.github.iridatelegrambot.service.buttons;

public enum CommandNameForButtons {


    ADDORDER("/add_order"),
    ADDINVOICE("/add_invoice"),
    SHOWACTIVEORDER("/show_active_orders"),
    HELP("/help");

    private final String name;

    CommandNameForButtons(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }
}
