package com.github.iridatelegrambot.command.CallbackCommand;
import java.util.*;
public enum CallbackCommandName {
    ADD_ORDER("add_order","add_order:JSON(Order)"),
    ADD_INVOICE("add_invoice","add_invoice:JSON(Invoice)"),
    ADD_ORDER_TO_INVOICE("add_ord_to_inv","addOrdToInv:JSON(BondOrderToInvoice)"),
    CLOSE_ORDER("close_order","delete:{id}:boolean"),
    SHOW_ORDER("show_order","show_order:id:{id}"),
    ORDER_MENU("order","order:subcommand:id:{id}"),
    STAT_MENU("stat","stat:subcommand"),
    CANCEL("delete","delete:subcommand:id:{id}"),
    ADD_STATUS_USER("set_status_user","setStatusUser:chatId:{id}:boolean"),
    ADMIN_MENU("admin_menu","adminMenu:subcommand"),
    STAT_DOCUMENT("stat_document","stat_document:WaitDocument:WaitTypeStatus"),
    ADMIN_MENU_SET_STATUS("admin_set_status","admin_set_status:subcommand:{idUsers}:boolean");

    private final String name;
    private final String format;
    private final List<String> subCommandList = new ArrayList<>();

    CallbackCommandName(String name, String format) {
        this.name = name;
        this.format = format;
    }

    public String getName() {
        return name;
    }

    public String getNameForService(){
        return name + ":";
    }

    public String getFormat() {
        return format;
    }

    public List<String> getSubCommandList() {
        return subCommandList;
    }

    public void setSubCommand(String subCommand){
        subCommandList.add(subCommand);
    }

    public void setSubCommands(String[] subCommands){
        subCommandList.addAll(Arrays.asList(subCommands));
    }
}
