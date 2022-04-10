package com.github.iridatelegrambot.command.CallbackCommand;

public enum CallbackCommandName {
    ADD_ORDER("add_order"),
    ADD_INVOICE("add_invoice");

    private final String name;

    CallbackCommandName(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }
}
