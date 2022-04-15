package com.github.iridatelegrambot.command.CallbackCommand;

public enum CallbackCommandName {
    ADD_ORDER("add_order"),
    ADD_INVOICE("add_invoice"),
    ADD_ORDER_TO_INVOICE("addOrdToInv"),
    CLOSE_ORDER("closeOrder"),
    CANCEL("delete");

    private final String name;

    CallbackCommandName(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }
}
