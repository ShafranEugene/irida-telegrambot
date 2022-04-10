package com.github.iridatelegrambot.entity;
//this POJO should help to create buttons because CallBackData have limit 64 bit
public class BondOrderToInvoice {
    private int idOrder;
    private int idInvoice;

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

    @Override
    public String toString() {
        return "BondOrderToInvoice{" +
                "idOrder=" + idOrder +
                ", idInvoice=" + idInvoice +
                '}';
    }
}
