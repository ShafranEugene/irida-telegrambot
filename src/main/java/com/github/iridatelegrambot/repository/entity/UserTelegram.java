package com.github.iridatelegrambot.repository.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "user_telegram")
public class UserTelegram {
    @Id
    @Column(name = "chat_id")
    private String chatId;

    @Column
    private boolean active;

    public UserTelegram() {
    }

    public String getChatId() {
        return chatId;
    }

    public void setChatId(String chatId) {
        this.chatId = chatId;
    }

    public boolean isActive() {
        return active;
    }

    public void setActive(boolean active) {
        this.active = active;
    }
}
