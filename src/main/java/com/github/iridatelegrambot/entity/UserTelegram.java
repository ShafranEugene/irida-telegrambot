package com.github.iridatelegrambot.entity;

import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Entity
@Table(name = "user_telegram")
public class UserTelegram {
    @Id
    @Column(name = "chat_id")
    private Long chatId;
    @Column(name = "first_name")
    private String firstName;
    @Column(name = "user_name")
    private String userName;
    @Column(name = "admin")
    private boolean admin;

    @Column
    private boolean active;
    @OneToOne(cascade = CascadeType.ALL,mappedBy = "userTelegram")
    private ConditionBot conditionBot;
    @OneToMany(mappedBy = "user", fetch = FetchType.EAGER)
    private final List<Order> orderList = new ArrayList<>();
    @OneToMany(mappedBy = "user", fetch = FetchType.EAGER)
    @Fetch(value = FetchMode.SUBSELECT)
    private final List<Invoice> invoiceList = new ArrayList<>();

    public UserTelegram() {
    }

    public Long getChatId() {
        return chatId;
    }

    public void setChatId(Long chatId) {
        this.chatId = chatId;
    }

    public boolean isActive() {
        return active;
    }

    public void setActive(boolean active) {
        this.active = active;
    }

    public ConditionBot getConditionBot() {
        return conditionBot;
    }

    public void setConditionBot(ConditionBot conditionBot) {
        this.conditionBot = conditionBot;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public boolean isAdmin() {
        return admin;
    }

    public void setAdmin(boolean admin) {
        this.admin = admin;
    }

    public List<Order> getOrderList() {
        return orderList;
    }

    public void addOrder(Order order) {
        if(orderList.contains(order)){
            return;
        }
        orderList.add(order);
        order.setUser(this);
    }

    public List<Invoice> getInvoiceList() {
        return invoiceList;
    }

    public void addInvoice(Invoice invoice) {
        if(invoiceList.contains(invoice)){
            return;
        }
        invoiceList.add(invoice);
        invoice.setUser(this);
    }

    @Override
    public boolean equals(Object object) {
        if (this == object) return true;
        if (object == null || getClass() != object.getClass()) return false;
        UserTelegram that = (UserTelegram) object;
        return active == that.active &&
                Objects.equals(chatId, that.chatId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(chatId, active);
    }

    @Override
    public String toString() {
        return "UserTelegram{" +
                "chatId=" + chatId +
                ", active=" + active +
                '}';
    }
}
