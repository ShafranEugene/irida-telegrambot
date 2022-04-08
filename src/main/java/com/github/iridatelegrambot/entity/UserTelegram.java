package com.github.iridatelegrambot.entity;

import javax.persistence.*;
import java.util.Objects;

@Entity
@Table(name = "user_telegram")
public class UserTelegram {
    @Id
    @Column(name = "chat_id")
    private Long chatId;

    @Column
    private boolean active;
    @OneToOne(cascade = CascadeType.ALL, mappedBy = "userTelegram")
    private ConditionBot conditionBot;

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
