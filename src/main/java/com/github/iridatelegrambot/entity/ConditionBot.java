package com.github.iridatelegrambot.entity;

import javax.persistence.*;
import java.util.Objects;

@Entity
@Table(name = "condition_bot")
public class ConditionBot {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    @Column(name = "answer_order_status")
    private boolean answerOrderStatus;
    @Column(name = "answer_invoice_status")
    private boolean answerInvoiceStatus;
    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "id_user")
    private UserTelegram userTelegram;

    public ConditionBot() {
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public boolean isAnswerOrderStatus() {
        return answerOrderStatus;
    }

    public void setAnswerOrderStatus(boolean answerOrderStatus) {
        this.answerOrderStatus = answerOrderStatus;
    }

    public boolean isAnswerInvoiceStatus() {
        return answerInvoiceStatus;
    }

    public void setAnswerInvoiceStatus(boolean answerInvoiceStatus) {
        this.answerInvoiceStatus = answerInvoiceStatus;
    }

    public UserTelegram getUserTelegram() {
        return userTelegram;
    }

    public void setUserTelegram(UserTelegram userTelegram) {
        this.userTelegram = userTelegram;
    }

    @Override
    public String toString() {
        return "ConditionBot{" +
                "id=" + id +
                ", answerOrderStatus=" + answerOrderStatus +
                ", answerInvoiceStatus=" + answerInvoiceStatus +
                ", userTelegram=" + userTelegram +
                '}';
    }

    @Override
    public boolean equals(Object object) {
        if (this == object) return true;
        if (object == null || getClass() != object.getClass()) return false;
        ConditionBot that = (ConditionBot) object;
        return id == that.id &&
                answerOrderStatus == that.answerOrderStatus &&
                answerInvoiceStatus == that.answerInvoiceStatus &&
                Objects.equals(userTelegram, that.userTelegram);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, answerOrderStatus, answerInvoiceStatus, userTelegram);
    }
}
