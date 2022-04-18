package com.github.iridatelegrambot.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;

//this POJO should help to create buttons because CallBackData have limit 64 bit
public class BondOrderToInvoice {
    private int idOrder;
    private int idInvoice;
    @JsonIgnore
    private Long chatId;
    @JsonIgnore
    private boolean waitInvoice = false;

    public BondOrderToInvoice(int idOrder, int idInvoice) {
        this.idOrder = idOrder;
        this.idInvoice = idInvoice;
    }

    public BondOrderToInvoice() {
    }

    public int getIdOrder() {
        return idOrder;
    }

    public void setIdOrder(int idOrder) {
        this.idOrder = idOrder;
    }

    public int getIdInvoice() {
        return idInvoice;
    }

    public void setIdInvoice(int idInvoice) {
        this.idInvoice = idInvoice;
    }

    public boolean isWaitInvoice() {
        return waitInvoice;
    }

    public void setWaitInvoice(boolean waitInvoice) {
        this.waitInvoice = waitInvoice;
    }

    public Long getChatId() {
        return chatId;
    }

    public void setChatId(Long chatId) {
        this.chatId = chatId;
    }

    @Override
    public String toString() {
        return "BondOrderToInvoice{" +
                "idOrder=" + idOrder +
                ", idInvoice=" + idInvoice +
                '}';
    }
}
