package com.github.iridatelegrambot.bot;

import com.github.iridatelegrambot.entity.ConditionBot;
import com.github.iridatelegrambot.entity.UserTelegram;
import com.github.iridatelegrambot.service.UserTelegramService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class CheckUpdateOnPost {

    private final UserTelegramService userTelegramService;
    @Autowired
    public CheckUpdateOnPost(UserTelegramService userTelegramService) {
        this.userTelegramService = userTelegramService;
    }

    public boolean waitingNumberOrder(Long idChat){
        ConditionBot conditionBot = findConditionBot(idChat);
        return conditionBot.isAnswerOrderStatus();
    }

    public boolean waitingNumberInvoice(Long idChat){
        ConditionBot conditionBot = findConditionBot(idChat);
        return conditionBot.isAnswerInvoiceStatus();
    }

    public void setStatusOrder(Long idChat,boolean status){

    }

    private ConditionBot findConditionBot(Long idChat){
        UserTelegram userTelegram = userTelegramService.findByChatId(idChat).get();
        return userTelegram.getConditionBot();
    }
}
