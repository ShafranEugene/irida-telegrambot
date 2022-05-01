package com.github.iridatelegrambot.service.buttons;

import com.github.iridatelegrambot.command.CommandName;

public enum CommandNameForButtons {


    ADDORDER("Добавить заказ", CommandName.ADDORDER,2),
    ADDINVOICE("Добавить накладную", CommandName.ADDINVOICE,2),
    SHOWACTIVEORDER("Показать все активные заказы",CommandName.SHOWACTIVEORDER,1),
    STAT("Статистика",CommandName.STAT,1),
    HELP("Помощь",CommandName.HELP,1);

    private final String name;
    //number of buttons in one a row, if you want 2 buttons in one row, you must add 2 buttons with numberRow = 2;
    private final Integer numberRow;
    private final CommandName command;

    CommandNameForButtons(String name,CommandName command, Integer numberRow) {
        this.numberRow = numberRow;
        this.name = name;
        this.command = command;
    }

    public String getName() {
        return name;
    }

    public Integer getNumberRow(){
        return numberRow;
    }

    public static boolean hasMainCommand(String text){
        for(CommandNameForButtons command : CommandNameForButtons.values()){
            if(text.equals(command.name)){
                return true;
            }
        }
        return false;
    }

    public static CommandName findCommandName(String text){
        for(CommandNameForButtons command : CommandNameForButtons.values()){
            if(text.equals(command.name)){
                return command.command;
            }
        }
        return CommandName.NO;
    }

}
