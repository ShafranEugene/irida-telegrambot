package com.github.iridatelegrambot.bot;

import org.springframework.stereotype.Component;

@Component
public class CheckUpdateOnPost {
    private boolean lastMessageAddOrder = false;
    private boolean lastMessageAddInvoice = false;

    public CheckUpdateOnPost() {
    }

    public boolean isLastMessageAddOrder() {
        return lastMessageAddOrder;
    }

    public void setLastMessageAddOrder(boolean lastMessageAddOrder) {
        this.lastMessageAddOrder = lastMessageAddOrder;
    }

    public boolean isLastMessageAddInvoice() {
        return lastMessageAddInvoice;
    }

    public void setLastMessageAddInvoice(boolean lastMessageAddInvoice) {
        this.lastMessageAddInvoice = lastMessageAddInvoice;
    }
}
