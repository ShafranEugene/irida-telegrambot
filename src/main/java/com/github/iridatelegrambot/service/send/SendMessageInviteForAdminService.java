package com.github.iridatelegrambot.service.send;

public interface SendMessageInviteForAdminService extends SendMessageService {

    void sendInviteToAdmin(Long chatIdAdmin, Long chatIdUser, String message);
}
