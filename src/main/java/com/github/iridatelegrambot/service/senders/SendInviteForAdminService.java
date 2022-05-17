package com.github.iridatelegrambot.service.senders;

public interface SendInviteForAdminService{

    void sendInviteToAdmin(Long chatIdAdmin, Long chatIdUser, String message);
}
