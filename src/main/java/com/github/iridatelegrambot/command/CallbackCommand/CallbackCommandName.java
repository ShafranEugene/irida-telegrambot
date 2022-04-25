package com.github.iridatelegrambot.command.CallbackCommand;

public enum CallbackCommandName {
    ADD_ORDER("add_order"),
    ADD_INVOICE("add_invoice"),
    ADD_ORDER_TO_INVOICE("addOrdToInv"),
    CLOSE_ORDER("closeOrder"),
    SHOW_ORDER("show_order"),
    ORDER_MENU("order"),
    STAT_MENU("stat"),
    CANCEL("delete"),
    ADD_STATUS_USER("setStatusUser"),
    ADMIN_MENU("adminMenu");

    private final String name;

    CallbackCommandName(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public String getNameForService(){
        return name + ":";
    }
}
