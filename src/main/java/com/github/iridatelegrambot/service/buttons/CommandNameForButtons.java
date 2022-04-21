package com.github.iridatelegrambot.service.buttons;

public enum CommandNameForButtons {


    ADDORDER("/add_order",1),
    ADDINVOICE("/add_invoice",1),
    SHOWACTIVEORDER("/show_active_orders",1),
    STAT("/stat",1),
    HELP("/help",1);

    private final String name;
    //number of buttons in one a row, if you want 2 buttons in one row, you must add 2 buttons with numberRow = 2;
    private final Integer numberRow;

    CommandNameForButtons(String name,Integer numberRow) {
        this.numberRow = numberRow;
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public Integer getNumberRow(){
        return numberRow;
    }
}
